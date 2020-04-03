package pt.ulisboa.tecnico.sise.insureapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import pt.ulisboa.tecnico.sise.insureapp.datamodel.ClaimItem;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.WSHelper;

public class ListClientClaimsTask extends AsyncTask<Integer,String,List<ClaimItem>> {
    private static final String TAG = "ListClientClaims";
    private Integer params;
    private ListView listView;

    @Override
    protected List<ClaimItem> doInBackground(Integer... sessionId) {
        try {
            List<ClaimItem> claimItemList = WSHelper.listClaims(sessionId[0]);
            return claimItemList;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<ClaimItem> result) {
        ArrayAdapter<ClaimItem> adapter = new ArrayAdapter<>(ClaimHistoryActivity,
                android.R.layout.simple_list_item_1, android.R.id.text1, result);
        listView.setAdapter(adapter);


    }
}
