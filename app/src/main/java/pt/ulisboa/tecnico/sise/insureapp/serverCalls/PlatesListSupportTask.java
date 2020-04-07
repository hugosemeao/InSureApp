package pt.ulisboa.tecnico.sise.insureapp.serverCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.List;
import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.JsonCodec;
import pt.ulisboa.tecnico.sise.insureapp.JsonFileManager;

public class PlatesListSupportTask extends AsyncTask<Integer, Void, List<String>> {
    public final static String TAG = "PlatesList";
    private Spinner spinner;
    private GlobalState globalState;

    public PlatesListSupportTask(Spinner spinner, Context context) {
    this.spinner = spinner;
    this.globalState = (GlobalState)context.getApplicationContext();
    }

    @Override
    protected List<String> doInBackground(Integer... params) {
        Integer sessionId = params[0];
        List<String> result = null;
        try {
            result = WSHelper.listPlates(sessionId);
        } catch (Exception e) {
            Log.d(TAG,e.getMessage());
        }
        if (result==null){
            //checking customer json
            String customerClaimPlatesJson = JsonFileManager.jsonReadFromFile(globalState,"claimplates.json");
            Log.d(TAG,"File read");
            //Decoding JSON and assign to claimItemList
            result= JsonCodec.decodePlateList(customerClaimPlatesJson);
            Log.d(TAG,"File decoded!");
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        if (result != null && result.size() != 0) {
            try {
                //Setting file name
                String claimPlatesFileName ="claimplates.json";
                //Converting information to json type string
                String claimPlatesJson = JsonCodec.encodePlateList(result);
                Log.d(TAG,"File written!");
                //Writing information into a JSON file
                JsonFileManager.jsonWriteToFile(globalState,claimPlatesFileName,claimPlatesJson);
                Log.d(TAG,"File saved!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (spinner != null) {
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(globalState, android.R.layout.simple_spinner_dropdown_item, result);
                spinner.setAdapter(spinnerAdapter);
            }
        } else {
            Toast.makeText(globalState.getApplicationContext(),
                    "Sorry, there was an error in the server, try again later!",
                    Toast.LENGTH_SHORT)
                    .show();
        }


    }

}

