package pt.ulisboa.tecnico.sise.insureapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.sise.insureapp.datamodel.Customer;

public class GlobalState extends Application {
    private static int _sessionId = -1;
    private static List<NewClaimInfo> listOfflineClaims = new ArrayList<NewClaimInfo>();
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setSessionId(int sessionId) {
        this._sessionId = sessionId;
    }

    public int getSessionId() {
        return _sessionId;
    }

    public void resetSessionId() {
        this._sessionId = -1;
    }

    public List<NewClaimInfo> getListOfflineClaims() {
        return listOfflineClaims;
    }

}
