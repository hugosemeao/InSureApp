package pt.ulisboa.tecnico.sise.insureapp.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.R;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.LoginCallTask;

public class LoginActivity extends AppCompatActivity {
    String username, password;
    final String TAG = "loginActivity";
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("login","login activity is set");

        final Button button = findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "Button clicked");
                /*gets the inserted values by the user*/
                EditText name  = findViewById(R.id.username);
                EditText pass = findViewById(R.id.loginPassword);
                username = name.getText().toString();
                password = pass.getText().toString();
                Log.d(TAG, "user: "+ username + "  password: " + password);
                GlobalState globalState = (GlobalState) getApplicationContext();
                
                new LoginCallTask(context, globalState).execute(username, password);

            }
        });
    }
}
