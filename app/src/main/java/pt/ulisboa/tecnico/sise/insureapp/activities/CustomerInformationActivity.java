package pt.ulisboa.tecnico.sise.insureapp.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.R;
import pt.ulisboa.tecnico.sise.insureapp.WSGetCustomerInfoTask;


public class CustomerInformationActivity extends AppCompatActivity {

    private Activity _activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GlobalState gs = (GlobalState) getApplicationContext();
        new WSGetCustomerInfoTask(gs, _activity).execute(gs.getSessionId());
    }
}



