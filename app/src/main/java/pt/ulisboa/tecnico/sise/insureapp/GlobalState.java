package pt.ulisboa.tecnico.sise.insureapp;

import android.app.Application;

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

    }