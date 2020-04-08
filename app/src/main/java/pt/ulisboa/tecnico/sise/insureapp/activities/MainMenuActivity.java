package pt.ulisboa.tecnico.sise.insureapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import java.io.File;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.R;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.LogOutCallTask;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.LoginCallTask;

public class MainMenuActivity extends AppCompatActivity {
    private static final String TAG = "mainMenu";
    private Button claimHistoryButton;
    private Button newClaimButton;
    private Button customerInformationButton;
    private Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        /*//say that we want to use our toolbar as our action bar replacement
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("InsureApp");*/

        //associate buttons to the respective ids
        claimHistoryButton = findViewById(R.id.claimHistoryButton);
        newClaimButton = findViewById(R.id.newClaimButton);
        customerInformationButton = findViewById(R.id.customerInformationButton);
        logOutButton = findViewById(R.id.logOutButton);

        //listeners for each of the buttons
        claimHistoryButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "claimHistoryButton clicked");
                Intent intent = new Intent(MainMenuActivity.this, ClaimHistoryActivity.class);
                //switches to ClaimHistoryActivity
                startActivity(intent);
            }
        });

        newClaimButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "newClaimButton clicked");
                Intent intent = new Intent(MainMenuActivity.this, CreateClaimActivity.class);
                //switches to CreateClaimActivity
                startActivity(intent);
            }
        });

        customerInformationButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "customerInformationButton clicked");
                Intent intent = new Intent(MainMenuActivity.this, CustomerInformationActivity.class);
                //switches to CustomerInformationActivity
                startActivity(intent);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "logOutButton clicked");
                GlobalState globalState = (GlobalState) getApplicationContext();
                //LogsOut from the server
                new LogOutCallTask(getApplicationContext(), globalState).execute();
            }
        });

    }

}
