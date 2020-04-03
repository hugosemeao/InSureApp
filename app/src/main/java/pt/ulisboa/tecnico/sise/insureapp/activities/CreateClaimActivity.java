package pt.ulisboa.tecnico.sise.insureapp.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;
import pt.ulisboa.tecnico.sise.insureapp.R;
import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.WSSubmitNewClaimTask;

public class CreateClaimActivity extends AppCompatActivity {
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
        Claimtitle = (EditText) findViewById(R.id.claim_title_value);
        licensePlate = (Spinner) findViewById(R.id.license_plate);
        description = (EditText) findViewById(R.id.claim_description);
        Claimdate = (EditText) findViewById(R.id.date_claim);
        submitClaimButton = (Button) findViewById(R.id.submitButton);
        backMenuButton = (Button) findViewById(R.id.Back_button);
        globalstate = (GlobalState) getApplicationContext();

        // initiate the date picker and a button
        // perform click event on edit text

        Claimdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
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
                    new WSSubmitNewClaimTask(context, globalstate.getSessionId()).execute(ClaimDate, ClaimDescription, ClaimPlateInformation, ClaimTitle);

                } else if (ClaimDate.isEmpty() && !ClaimDescription.isEmpty() && !ClaimPlateInformation.isEmpty() && !ClaimTitle.isEmpty()) {
                    Toast.makeText(context,
                            "Error: Please fill the date",
                            Toast.LENGTH_SHORT)
                            .show();

                } else if (!ClaimDate.isEmpty() && ClaimDescription.isEmpty() && !ClaimPlateInformation.isEmpty() && !ClaimTitle.isEmpty()) {
                    Toast.makeText(context,
                            "Error: Please enter a claim description",
                            Toast.LENGTH_SHORT)
                            .show();

                } else if (!ClaimDate.isEmpty() && !ClaimDescription.isEmpty() && ClaimPlateInformation.isEmpty() && !ClaimTitle.isEmpty()) {
                    Toast.makeText(context,
                            "Error: Enter the plate informarion",
                            Toast.LENGTH_SHORT)
                            .show();

                } else if (!ClaimDate.isEmpty() && !ClaimDescription.isEmpty() && !ClaimPlateInformation.isEmpty() && ClaimTitle.isEmpty()) {
                    Toast.makeText(context,
                            "Error: Enter a claim title ",
                            Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        Button backMenuButton = (Button) findViewById(R.id.Back_button);
        backMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, CreateClaimActivity.class);
                startActivity(intent);
            }
        });

    }
}