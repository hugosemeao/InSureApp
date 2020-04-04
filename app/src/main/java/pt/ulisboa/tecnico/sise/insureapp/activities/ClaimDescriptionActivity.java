package pt.ulisboa.tecnico.sise.insureapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import pt.ulisboa.tecnico.sise.insureapp.R;

public class ClaimDescriptionActivity extends AppCompatActivity {
    public final static String TAG = "ClaimDescription";
    private TextView textView;
    private Bundle bundle;
    private Intent intent;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bundle=savedInstanceState;
        setContentView(R.layout.activity_claim_description);
        textView= (TextView) findViewById(R.id.textView7);
        backButton=(Button) findViewById(R.id.Back_button);

        //Setup back button
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "claimHistoryButton clicked");
                Intent intent = new Intent(ClaimDescriptionActivity.this, ClaimHistoryActivity.class);
                //switches to ClaimHistoryActivity
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        intent=getIntent();
        bundle=this.intent.getExtras();
        textView.setText(bundle.getString("key"));
    }

}
