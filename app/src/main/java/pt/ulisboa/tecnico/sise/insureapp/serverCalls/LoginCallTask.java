package pt.ulisboa.tecnico.sise.insureapp.serverCalls;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.JsonCodec;
import pt.ulisboa.tecnico.sise.insureapp.JsonFileManager;
import pt.ulisboa.tecnico.sise.insureapp.datamodel.NewClaimInfo;
import pt.ulisboa.tecnico.sise.insureapp.activities.MainMenuActivity;
import pt.ulisboa.tecnico.sise.insureapp.datamodel.Customer;

public class LoginCallTask extends AsyncTask<String, Integer, Integer> {
    public final static String TAG = "LoginCallTask";
    private Context context;
    private GlobalState globalState;

    public LoginCallTask(Context applicationContext, GlobalState globalState) {
        //stores is this class the app context (which saves sessionID) to allow me to edit sessionID
        //after login and make it available to remaining activities
        this.context = applicationContext;
        this.globalState = globalState;
    }

    @Override
    protected Integer doInBackground(String[] params) {
        int sessionId = -1;

        try {
            String username = params[0];
            Log.d(TAG, params[0]);
            String password = params[1];
            Log.d(TAG, params[1]);
            //returns session that is always >= 1
            sessionId = WSHelper.login(username, password);// exists and password
            Log.d(TAG, "Login result => " + sessionId + " "+ params[0] + "," + params[1]);

            if(sessionId >= 1){
                //store customer info/object in global state if there is a valid sessionID
                storeCustomerInfo(sessionId);
                //upload previous sessions offline claim
                uploadPreviousSessionOfflineClaims(sessionId);
            }

            return sessionId;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer sessionID){
        if(sessionID == null){
            //something went wrong when invoking the server
            Toast.makeText(context,"Something went Wrong. Please try again later.",Toast.LENGTH_LONG).show();
            return;
        }
        //store session id in the application context (shared "memory")
        this.globalState.setSessionId(sessionID);
        //sessioID < 1 is invalid credetials
        if(sessionID >= 1){
            //change activity procedure
            Intent intent = new Intent((Context) context, MainMenuActivity.class);
            //switches to MainMenuActivity
            context.startActivity(intent);
        }else{
            Toast.makeText(context,"Invalid Credentials",Toast.LENGTH_LONG).show();
        }
    }

    private void storeCustomerInfo(Integer sessionId){
            try {
                Customer customer = WSHelper.getCustomerInfo(sessionId);
                Log.d(TAG, "Customer Info Acquired");
                globalState.setCustomer(customer);
                List<String> platesList= WSHelper.listPlates(sessionId);
                Log.d(TAG, "Plate List Acquired");
                globalState.setListPlates(platesList);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
    }

    private void uploadPreviousSessionOfflineClaims(Integer sessionID){

        try{
            //this will throw exception if user did not upload any offline claims previously
            String fileName = "offlineClaim" +globalState.getCustomer().getPolicyNumber()+ ".json";
            String jsonFile = JsonFileManager.jsonReadFromFile(context,fileName);
            List<NewClaimInfo> listOfCLaims = JsonCodec.decodeOfflineClaimList(jsonFile);
            for(NewClaimInfo claim : listOfCLaims){
                new WSHelper().submitNewClaim(sessionID,claim.getClaimTitle(),claim.getClaimDate(),claim.getClaimDate(),claim.getClaimDescription());
            }
            //delete the file after submitting info
            File[] filesInMemory = new File("/data/data/pt.ulisboa.tecnico.sise.insureapp/files").listFiles();
            for(File file : filesInMemory){
                if(file.getName().equals(fileName)){
                    file.delete();
                }
            }

        }catch(Exception e){
            Log.d(TAG, e.getMessage());
        }

    }
}
