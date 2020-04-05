package pt.ulisboa.tecnico.sise.insureapp.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pt.ulisboa.tecnico.sise.insureapp.R;


public class CustomerInformationActivity extends AppCompatActivity {

    public final static String TAG = "ClientInformation";
    private TextView textView;
    private Bundle bundle;
    private Intent intent;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bundle=savedInstanceState;
        setContentView(R.layout.activity_customer_information);

        //elements in the UI
       textView= (TextView) findViewById(R.id.editText2);
       textView= (TextView) findViewById(R.id.editText3);
       textView=(TextView) findViewById(R.id.editText5);
       textView=(TextView) findViewById(R.id.editText6);
       textView=(TextView) findViewById(R.id.claim_title_value);
        backButton=(Button) findViewById(R.id.Back_button);

        //Setup back button
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "Client Information Button clicked");
                Intent intent = new Intent(CustomerInformationActivity.this,MainMenuActivity.class);
                //switches to ClaimHistoryActivity
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //get the intent from previous activity and print his info in the textView
        intent=getIntent();

        bundle=this.intent.getExtras();
        textView.setText(bundle.getString("key"));
    }

}



