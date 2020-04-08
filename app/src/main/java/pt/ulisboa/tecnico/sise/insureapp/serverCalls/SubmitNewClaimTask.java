package pt.ulisboa.tecnico.sise.insureapp.serverCalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.JsonCodec;
import pt.ulisboa.tecnico.sise.insureapp.JsonFileManager;
import pt.ulisboa.tecnico.sise.insureapp.NewClaimInfo;
import pt.ulisboa.tecnico.sise.insureapp.activities.MainMenuActivity;
import pt.ulisboa.tecnico.sise.insureapp.datamodel.ClaimItem;

public class SubmitNewClaimTask extends AsyncTask<String, Void, Boolean> {
    public final static String TAG = "SubmitNewClaimTask";
    private Context _context;
    private GlobalState globalState;
    private Integer _sessionId;
    private Boolean exception = false;

    public SubmitNewClaimTask(Context context, GlobalState globalState) {
        this._context = context;
        this._sessionId = globalState.getSessionId();
        this.globalState = globalState;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            Log.d(TAG,"Claim Details: " + params[0] + ", " + params[1] + ", " + params[2]);
            //WS helper will receive the parameters to include them on a specific session ID
            boolean r = WSHelper.submitNewClaim(_sessionId, params[0], params[1], params[2], params[3]);
            Log.d(TAG, "Submit new claim result => " + r);
            return r;
            //If the exception is true, meaning the parameters are not complete
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            exception = true;

            //store the claim in case connection to server fails
            NewClaimInfo claimToBePutInFile = new NewClaimInfo(params[0], params[1], params[2], params[3]);
            Log.d(TAG,"Claim Details: " + claimToBePutInFile.getClaimTitle() + ", " +
                    claimToBePutInFile.getClaimDate() + ", " + claimToBePutInFile.getClaimPlateInformation()
                    + ", " + claimToBePutInFile.getClaimDescription());
            globalState.getListOfflineClaims().add(claimToBePutInFile);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean res) {

        if (res && !exception) {
            Toast.makeText(_context,"Your claim was successfully submited!",Toast.LENGTH_SHORT).show();
            changeActivity();
        } else if (exception) {
            //to put information in file
            //Setting file name. Each new claim will have different name thanks to counter
            String offlineClaimsFileName ="offlineClaim" +globalState.getCustomer().getPolicyNumber()+ ".json";
            //Converting information to json type string
            String claimJson;
            try {
                //mandatory try-catch
                claimJson = JsonCodec.encodeOfflineClaimList(globalState.getListOfflineClaims());
                Log.d(TAG,"information into String format is DONE");
                //Writing information into a JSON file
                JsonFileManager.jsonWriteToFile(_context,offlineClaimsFileName,claimJson);
                Log.d(TAG,"Claim information saved in file!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            changeActivity();
            Toast.makeText(_context,"Connection is down. Submission will be AUTOMATICALLY retried again later",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(_context,
                    "Error, invalid parameters!",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }
    }

    private void changeActivity(){
        //Intent that alearts with a toast that the claim was created and goes to MainMenuActivity
        Intent intent = new Intent(_context, MainMenuActivity.class);
        _context.startActivity(intent);
        ((Activity)_context).finish();
    }

}