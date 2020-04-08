package pt.ulisboa.tecnico.sise.insureapp.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;
import pt.ulisboa.tecnico.sise.insureapp.R;
import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.PlatesListSupportTask;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.SubmitNewClaimTask;

public class CreateClaimActivity extends AppCompatActivity {
    private static final String TAG = "createClaimActivity";
    private DatePickerDialog datePickerDialog;
    private EditText Claimtitle;
    private EditText Claimdate;
    private Spinner licensePlate;
    private EditText description;
    private Button submitClaimButton;
    private Button backMenuButton;
    private Context context = this;
    private GlobalState globalstate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_claim);
        Claimtitle =  findViewById(R.id.name);
        licensePlate = findViewById(R.id.license_plate);
        description = findViewById(R.id.claim_description);
        Claimdate = findViewById(R.id.date_claim);
        submitClaimButton = findViewById(R.id.submitButton);
        backMenuButton = findViewById(R.id.Back_button);
        globalstate = (GlobalState) getApplicationContext();
        new PlatesListSupportTask(licensePlate, this.context).execute(globalstate.getSessionId());


        Claimdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from the calendaqr
                final Calendar AppCalendar = Calendar.getInstance();
                int mYear = AppCalendar.get(Calendar.YEAR); // current year
                int mMonth = AppCalendar.get(Calendar.MONTH); // current month
                int mDay = AppCalendar.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(CreateClaimActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                Claimdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        submitClaimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ClaimTitle = Claimtitle.getText().toString();
                String ClaimDate = Claimdate.getText().toString();
                String ClaimPlateInformation = licensePlate.getSelectedItem().toString();
                String ClaimDescription = description.getText().toString();

                if (!ClaimDate.isEmpty() && !ClaimDescription.isEmpty() && !ClaimPlateInformation.isEmpty() && !ClaimTitle.isEmpty()) {
                    new SubmitNewClaimTask(context, globalstate).execute(ClaimTitle, ClaimDate, ClaimPlateInformation, ClaimDescription);

                } else {
                    Toast.makeText(context,
                            "Ups! Some parameters are missing",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        Button backMenuButton = findViewById(R.id.Back_button);
        backMenuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "Back Button Clicked");
                Intent intent = new Intent(CreateClaimActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

}
