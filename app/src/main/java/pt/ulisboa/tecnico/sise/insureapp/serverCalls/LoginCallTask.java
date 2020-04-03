package pt.ulisboa.tecnico.sise.insureapp.serverCalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.activities.LoginActivity;
import pt.ulisboa.tecnico.sise.insureapp.activities.MainMenuActivity;

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
            return sessionId;

        } catch (Exception e) {
            Log.d(TAG, e.toString());

        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer sessionID){
        //store session id in the application context (shared "memory")
        this.globalState.setSessionId(sessionID);
        //sessioID < 1 is invalid credetials
        if(sessionID >= 1){
            Intent intent = new Intent((Context) context, MainMenuActivity.class);
            //TODO extra with the client name and stuff ?? intent.putExtra(InternalProtocol.READ_NOTE_INDEX, position);
            //switches to MainMenuActivity
            context.startActivity(intent);
        }else{
            Toast.makeText(context,"Invalid Credentials",Toast.LENGTH_LONG).show();
        }
    }
}
