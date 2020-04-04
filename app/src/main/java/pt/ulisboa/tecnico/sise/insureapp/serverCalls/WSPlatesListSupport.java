package pt.ulisboa.tecnico.sise.insureapp.serverCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;

public class WSPlatesListSupport extends AsyncTask<Integer, Void, List<String>> {
    public final static String TAG = "PlatesList";
    private Spinner spinner;
    private GlobalState globalState;

    public WSPlatesListSupport(Spinner spinner, Context context) {
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
            try {
                result = globalState.getCustomer().getPlateList();
            } catch (NullPointerException el) {
                Log.d(TAG, el.toString());
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        Boolean LoadSuccessful = result != null && result.size() != 0;

        if (LoadSuccessful) {
            if (spinner != null) {
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(globalState, android.R.layout.simple_spinner_dropdown_item, result);
                spinner.setAdapter(spinnerAdapter);
            }
//
  //          globalState.setCustomerLicensePlates(result);
    //        globalState.writeCustomerInCache(globalState.getCustomer());

        } else {
            Toast.makeText(globalState.getApplicationContext(),
                    "Sorry, there was an error in the server, try again later!",
                    Toast.LENGTH_SHORT)
                    .show();
        }


    }

}

