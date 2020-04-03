package pt.ulisboa.tecnico.sise.insureapp.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pt.ulisboa.tecnico.sise.insureapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("login","login activity is set");

        final Button button = findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("login", "Button clicked");
                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                //TODO extra with the client name and stuff ?? intent.putExtra(InternalProtocol.READ_NOTE_INDEX, position);
                //switches to MainMenuActivity
                startActivity(intent);
            }
        });
    }
}
