package pt.ulisboa.tecnico.sise.insureapp.serverCalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insureapp.activities.MainMenuActivity;

public class SubmitNewClaimTask extends AsyncTask<String, Void, Boolean> {
    public final static String TAG = "ListPlatesTask";
    private Context _context;
    private Integer _sessionId;
    private Boolean exception = false;

    public SubmitNewClaimTask(Context context, Integer sessionId) {
        this._context = context;
        this._sessionId = sessionId;
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
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean res) {

        if (res && !exception) {
            //Intent that alearts with a toast that the claim was created and goes to MainMenuActivity
            Intent intent = new Intent(_context, MainMenuActivity.class);
            _context.startActivity(intent);
            ((Activity)_context).finish();
            Toast.makeText(_context,
                    "Your claim was successfully submited!",
                    Toast.LENGTH_SHORT)
                    .show();

        } else if (exception) {
            Toast.makeText(_context,
                    "Sorry, there was an error in the server, try again later!",
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(_context,
                    "Error, invalid parameters!",
                    Toast.LENGTH_SHORT)
                    .show();

        }

        Toast.makeText(_context.getApplicationContext(), "Your claim was successfully submited!",
                        Toast.LENGTH_SHORT)
                        .show();
    }



}