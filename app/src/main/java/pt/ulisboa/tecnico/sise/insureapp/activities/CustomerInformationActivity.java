package pt.ulisboa.tecnico.sise.insureapp.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.R;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.WSGetCustomerInfoTask;


public class CustomerInformationActivity extends AppCompatActivity {

    private final String TAG = "customerInformationAct";
    private Activity _activity = this;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);

        backButton = findViewById(R.id.Back_button);

        //listeners for the back button
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "backButton clicked");
                Intent intent = new Intent(CustomerInformationActivity.this, MainMenuActivity.class);
                //switches to MainActivityClass
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        GlobalState gs = (GlobalState) getApplicationContext();
        new WSGetCustomerInfoTask(gs, _activity).execute(gs.getSessionId());
    }
}



