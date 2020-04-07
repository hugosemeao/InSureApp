package pt.ulisboa.tecnico.sise.insureapp.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.sise.insureapp.ClaimHistoryAdapter;
import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.R;
import pt.ulisboa.tecnico.sise.insureapp.datamodel.ClaimItem;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.GetClaimInfoTask;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.ListClientClaimsTask;

public class ClaimHistoryActivity extends AppCompatActivity {
    public final static String TAG = "ClaimHistory";
    private List<ClaimItem> _claimList;
    private ListView _listView;
    private GlobalState _globalState;
    public Integer sessionID=1;
    private Button backButton;
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_history);

        //element on the UI
        _listView = (ListView) findViewById(R.id.claim_history_list);
        backButton=(Button) findViewById(R.id.OkButton);

        //Initialize claim adapter
        ClaimHistoryAdapter claimHistoryAdapter = new ClaimHistoryAdapter(this, R.layout.item_claim_history, _claimList);

        //Call list claims task
        _globalState = (GlobalState) getApplicationContext();
        new ListClientClaimsTask(this,_listView).execute(_globalState.getSessionId());

        //Setting on click response
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Get the item cliked value
                ClaimItem item = (ClaimItem) _listView.getItemAtPosition(position);
                Log.d(TAG,"Clicked on item: " + item.getId());
                //Run GetClaimInfoTask with the info of the clicked item
                new GetClaimInfoTask(context).execute(_globalState.getSessionId(),item.getId());
            }
        });

        //Setup back button
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "claimHistoryButton clicked");
                Intent intent = new Intent(ClaimHistoryActivity.this, MainMenuActivity.class);
                //switches to MainMenuActivity
                startActivity(intent);
            }
        });
    }
}
