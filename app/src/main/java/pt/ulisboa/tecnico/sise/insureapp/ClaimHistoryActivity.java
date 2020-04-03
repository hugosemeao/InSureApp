package pt.ulisboa.tecnico.sise.insureapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.sise.insureapp.datamodel.ClaimItem;

public class ClaimHistoryActivity extends AppCompatActivity {
    private ArrayList<ClaimItem> _claimList;
    private ListView _listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_history);
    }
}
