package pt.ulisboa.tecnico.sise.insureapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import pt.ulisboa.tecnico.sise.insureapp.datamodel.ClaimRecord;
import pt.ulisboa.tecnico.sise.insureapp.datamodel.Customer;


public class GlobalState extends Application {
         private Customer customer;
         private int _sessionId = -1;


    public void setCustomer(Customer newCustomer) {
        Customer oldCustomer = customer;
        this.customer = newCustomer;

        if (oldCustomer != null) {
            this.customer.setListofClaims(oldCustomer.getClaimItemList());
            this.customer.setClaimRecords(oldCustomer.getClaimRecordList());
        }
    }
  //  public void writeCustomerInCache(Object customer)
    // }

    public void setCustomerLicensePlates(List<String> result) {
    }

    public void setSessionId(int sessionId) {
            this._sessionId = sessionId;
        }
        public int getSessionId() {
            return _sessionId;
        }

    public Customer getCustomer() {
        return customer;
    }

    public void resetSessionId() {
        this._sessionId = -1;
    }

    public ClaimRecord getCustomerClaimRecord(int claimID){
        List<ClaimRecord> claimRecords= this.customer.getClaimRecordList();
        ClaimRecord returnedClaim=null;
        if (claimRecords!=null){
            for (ClaimRecord claim:claimRecords){
                if (claim.getId()==claimID){
                    returnedClaim=claim;
                    break;
                }
            }
        }
        return returnedClaim;
    }
    public void addClaimRecordToCustomer(ClaimRecord claimRecord) {
        ClaimRecord cr = this.getCustomerClaimRecord(claimRecord.getId());
        if (cr == null) {
            this.customer.addClaim(claimRecord);
        }
    }

    public void writeCustomerInCache(Customer c) {
        writeObjectInFile(c, InternalProtocol.CACHE_CUSTOMER);
    }

    public void writeObjectInFile(Object o, String filename) {

        try {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(o);

            out.close();
            fos.close();

            Log.d(filename, filename + " - Object successfully written");
        } catch (IOException e) {
            Log.d(filename, filename + " - WRITE ERROR");
        }

    }

    public Object readObjectInFile(String filename) {

        Object o = null;
        try {
            FileInputStream fis = openFileInput(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            o = (Object) in.readObject();

            in.close();
            fis.close();

            Log.d(filename, filename + " - Object successfully read");

        } catch (IOException | ClassNotFoundException e) {
            Log.d(filename, filename + " - read failed: not in cache");
        }

        return o;
    }

    public void destroyFileInCache(Context context, String filename) {
        try {
            context.deleteFile(filename);
            Log.d("DESTROY_CACHE_FILE", filename + " was deleted");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("DESTROY_CACHE_FILE", filename + " does not exist");
        }
    }

}