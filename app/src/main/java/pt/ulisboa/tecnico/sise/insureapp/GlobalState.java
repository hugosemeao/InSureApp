package pt.ulisboa.tecnico.sise.insureapp;

import android.app.Application;


    public class GlobalState extends Application {
        private int _sessionId = -1;

        public void setSessionId(int sessionId) {
            this._sessionId = sessionId;
        }
        public int getSessionId() {
            return _sessionId;
        }

    }