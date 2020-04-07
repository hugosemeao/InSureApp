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
         private Context loginContext;

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



    public void setLoginContext(Context context){
        this.loginContext=context;
    }

    public Context getLoginContext() {return this.loginContext;}

}