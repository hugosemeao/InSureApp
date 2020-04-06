package pt.ulisboa.tecnico.sise.insureapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insureapp.datamodel.Customer;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.WSHelper;

public class WSGetCustomerInfoTask extends AsyncTask<Integer, Void, Customer> {
    public final static String TAG = "GetCustomerInfoTask";

    private Activity _activityContext;
    private GlobalState _gs;

    private TextView customer_name;
    private TextView policy_id;
    private TextView birth_date;
    private TextView address;
    private TextView nif;

    public WSGetCustomerInfoTask(Context globalState, Activity activity) {
        _gs = (GlobalState)globalState;
        _activityContext = activity;
    }

    @Override
    protected Customer doInBackground(Integer... params) {
        Customer customer = null;
        Integer sessionId = params[0];
        try {

            customer = WSHelper.getCustomerInfo(sessionId);
            //customer.setPlateList(WSHelper.listPlates(sessionId));

        } catch (Exception e) {

            customer = _gs.getCustomer();

            Log.d(TAG, "Customer obtained from cache - globalState");
        }
        return customer;
    }

    @Override
    protected void onPostExecute(Customer customer) {

        if (customer == null || customer.getName() == null || customer.getName().isEmpty()) {
            Log.d(TAG, "Get customer info result => null");
            String toastMsg = "Server Error and the Customer is not stored in cache\nPlease try again later.";
            Toast.makeText(_gs.getApplicationContext(),
                    toastMsg,
                    Toast.LENGTH_LONG)
                    .show();

        } else {
            Log.d(TAG, "Get customer info result => " + customer.toString());

            if (_activityContext != null) {
                customer_name = (TextView) _activityContext.findViewById(R.id.name);
                policy_id = (TextView) _activityContext.findViewById(R.id.policyNuber);
                birth_date = (TextView) _activityContext.findViewById(R.id.dateBirth);
                address = (TextView) _activityContext.findViewById(R.id.address);
                nif = (TextView) _activityContext.findViewById(R.id.fiscalNum);

                String nifString = (customer.getFiscalNumber() <= 0 ? "" : String.valueOf(customer.getFiscalNumber()));
                String policyString = (customer.getPolicyNumber() <= 0 ? "" : String.valueOf(customer.getPolicyNumber()));

                customer_name.setText(customer.getName());
                policy_id.setText(policyString);
                address.setText(customer.getAddress());
                birth_date.setText(customer.getDateOfBirth());
                nif.setText(nifString);
            }

            _gs.setCustomer(customer);
            _gs.writeCustomerInCache(customer);
        }

    }


}
