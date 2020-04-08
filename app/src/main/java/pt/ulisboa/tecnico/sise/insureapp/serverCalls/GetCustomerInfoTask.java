package pt.ulisboa.tecnico.sise.insureapp.serverCalls;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.JsonCodec;
import pt.ulisboa.tecnico.sise.insureapp.JsonFileManager;
import pt.ulisboa.tecnico.sise.insureapp.R;
import pt.ulisboa.tecnico.sise.insureapp.datamodel.Customer;
import pt.ulisboa.tecnico.sise.insureapp.serverCalls.WSHelper;

public class GetCustomerInfoTask extends AsyncTask<Integer, Void, Customer> {
    public final static String TAG = "GetCustomerInfoTask";
    private Activity _activityContext;
    private TextView customer_name;
    private TextView policy_id;
    private TextView birth_date;
    private TextView address;
    private TextView nif;
    private GlobalState globalState;

    public GetCustomerInfoTask(Activity activity, Context context) {
        _activityContext = activity;
        this.globalState=(GlobalState) context.getApplicationContext();
    }

    @Override
    protected Customer doInBackground(Integer... params) {
        Customer customer = globalState.getCustomer();
        Integer sessionId = params[0];
        if (customer==null){
            //checking customer json
            String customerInfoJSON = JsonFileManager.jsonReadFromFile(globalState,"customer.json");
            Log.d(TAG,"File read");
            //Decoding JSON and assign to claimItemList
            customer= JsonCodec.decodeCustomerInfo(customerInfoJSON);
            Log.d(TAG,"File decoded!");
        }
        return customer;
    }

    @Override
    protected void onPostExecute(Customer customer) {
            Log.d(TAG, "Get customer info result: " + customer.toString());
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
                try {
                    //Setting file name
                    String customerFileName ="customer.json";
                    //Converting information to json type string
                    String customerJson = JsonCodec.encodeCustomerInfo(customer);
                    Log.d(TAG,"File written!");
                    //Writing information into a JSON file
                    JsonFileManager.jsonWriteToFile(globalState,customerFileName,customerJson);
                    Log.d(TAG,"File saved!");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


