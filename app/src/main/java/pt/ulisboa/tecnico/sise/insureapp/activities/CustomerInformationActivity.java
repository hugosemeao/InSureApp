package pt.ulisboa.tecnico.sise.insureapp.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import pt.ulisboa.tecnico.sise.insureapp.R;



public class CustomerInformationActivity extends AppCompatActivity {
    private static final String TAG = "ViewCustomerInformation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
    }

            public void onClick(View view) {
            Log.d(TAG, "Back Button Clicked");
            Intent intent = new Intent(CustomerInformationActivity.this, MainMenuActivity.class);
            startActivity(intent);
        }
    }




