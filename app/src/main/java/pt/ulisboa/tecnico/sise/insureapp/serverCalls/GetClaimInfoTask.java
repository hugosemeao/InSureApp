package pt.ulisboa.tecnico.sise.insureapp.serverCalls;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insureapp.GlobalState;
import pt.ulisboa.tecnico.sise.insureapp.JsonCodec;
import pt.ulisboa.tecnico.sise.insureapp.JsonFileManager;
import pt.ulisboa.tecnico.sise.insureapp.activities.ClaimDescriptionActivity;
import pt.ulisboa.tecnico.sise.insureapp.datamodel.ClaimRecord;

public class GetClaimInfoTask extends AsyncTask<Integer, Void, ClaimRecord> {
    private static final String TAG = "GetClaimInfoTask";
    private Context context;
    private GlobalState globalState;
    private Integer[] integers;
    private Integer claimID;

    public GetClaimInfoTask(Context context){
    this.context=context;
    globalState=(GlobalState) context.getApplicationContext();

    }
    @Override
    protected ClaimRecord doInBackground(Integer...params) {
        ClaimRecord claimRecord = null;
        Integer sessionID=params[0];
        claimID=params[1];
        try {
            //try get claim record from server
            claimRecord=WSHelper.getClaimInfo(sessionID,claimID);
            Log.d(TAG,"Getting info of the claim " +claimRecord.getId());
            return claimRecord;
        } catch (Exception e) {
            Log.d(TAG,e.getMessage());
        }
        if(claimRecord==null){
            //checking customer json
            String customerClaimItemListJson = JsonFileManager.jsonReadFromFile(globalState,claimID+"claimrecord.json");
            Log.d(TAG,"File read");
            //Decoding JSON and assign to claimItemList
            claimRecord=JsonCodec.decodeClaimRecord(customerClaimItemListJson);
            Log.d(TAG,"File decoded!");
        }
    return claimRecord;
    }

    @Override
    protected void onPostExecute(ClaimRecord claimRecord) {
        if (claimRecord == null) {
            Toast.makeText(globalState, "The claim record is not avilable!", Toast.LENGTH_LONG).show();
        } else {
            try {
                //Setting file name
                String claimRecordFileName =claimID+"claimrecord.json";
                //Converting information to json type string
                String claimRecordJson = JsonCodec.encodeClaimRecord(claimRecord);
                Log.d(TAG,"File written!");
                //Writing information into a JSON file
                JsonFileManager.jsonWriteToFile(globalState,claimRecordFileName,claimRecordJson);
                Log.d(TAG,"File saved!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Create Intent to send to the Claim description activity
            Intent intent = new Intent(context, ClaimDescriptionActivity.class);
            intent.putExtra("key", "Title: " +claimRecord.getTitle()+"\nStatus: "+ claimRecord.getStatus()+"\nSubmission date:"+claimRecord.getSubmissionDate()+"\nOccurence Date: "+claimRecord.getOccurrenceDate()+"\nPlate: "+claimRecord.getPlate()+"\nDescription: "+claimRecord.getDescription()+"\nID: "+claimRecord.getId());
            context.startActivity(intent);
        }
    }

}
