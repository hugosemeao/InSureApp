package pt.ulisboa.tecnico.sise.insureapp.serverCalls;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.activities.LoginActivity;
import pt.ulisboa.tecnico.sise.insureapp.activities.MainMenuActivity;

public class LogOutCallTask extends AsyncTask<Void, Void,Boolean> {
    private static final String TAG = "LogOutCallTask";
    private GlobalState globalState;
    private Context context;

    public LogOutCallTask(Context context, GlobalState globalState) {
        this.globalState = globalState;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        int sessionID = globalState.getSessionId();
        try {
            return WSHelper.logout(sessionID);
        } catch (Exception e) {
           Log.d(TAG, e.toString());
           return null;
        }

    }

    protected void onPostExecute(Boolean logOutSuccessful){
        if(logOutSuccessful == null){
            //means wifi failed or server is offline
            Toast.makeText(context,"Please Check Your Connection and try again.", Toast.LENGTH_LONG).show();
        }else {
            /*if result is either true or false, means the server no longer has client sessionID
            *so we can proceed with logout*/
            //resets globalState's sessionID to an ivalid one, i.e -1
            globalState.resetSessionId();
            //delete all files except offlineClaim ones
            File[] filesInMemory = new File("/data/data/pt.ulisboa.tecnico.sise.insureapp/files").listFiles();
            for (File file : filesInMemory) {
                Log.d("test", file.getName());
                if (!file.getName().startsWith("offlineClaim")) {
                    file.delete();
                }
            }
            //switches to Login Activity
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);

        }
    }
}
