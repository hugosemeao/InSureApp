package pt.ulisboa.tecnico.sise.insureapp.serverCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.ulisboa.tecnico.sise.insureapp.ClaimHistoryAdapter;
import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.datamodel.ClaimItem;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.WSHelper;

public class ListClientClaimsTask extends AsyncTask<Integer,Void,List<ClaimItem>> {
    private static final String TAG = "ListClientClaims";
    private Integer params;
    private Context context;
    private ListView listView;
    private GlobalState globalState;

    public ListClientClaimsTask(Context context, ListView listView) {
        this.listView = listView;
        this.context = context;
        this.globalState = (GlobalState) context.getApplicationContext();
    }

    @Override
    protected List<ClaimItem> doInBackground(Integer... sessionId) {
        List<ClaimItem> claimItemList = new ArrayList<>();
        try {
            claimItemList = WSHelper.listClaims(sessionId[0]);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return claimItemList;
    }

    @Override
    protected void onPostExecute(List<ClaimItem> claimItemList) {
        Log.d(TAG, "listClaimItems  " + claimItemList);
        if (claimItemList.isEmpty()) {
            //Toast send when there are no claims registred to that user
            Toast.makeText(context, "You havn't registred claims yet", Toast.LENGTH_LONG).show();
        } else if (claimItemList == null) {
            //Toast send where there iss a connection error with the serever
            Toast.makeText(context, "Sorry, a error occured.", Toast.LENGTH_LONG).show();
        } else {
            if (listView != null) {
                //Updating the listView
                ArrayAdapter<ClaimItem> claimHistoryAdapter = new ClaimHistoryAdapter(context,
                        android.R.layout.simple_list_item_1, claimItemList);
                listView.setAdapter(claimHistoryAdapter);
            }
        }
    }
}
