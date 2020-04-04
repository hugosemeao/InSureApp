package pt.ulisboa.tecnico.sise.insureapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pt.ulisboa.tecnico.sise.insureapp.datamodel.ClaimItem;

public class ClaimHistoryAdapter extends ArrayAdapter<ClaimItem> {
    public ClaimHistoryAdapter(Context context, int resource, List<ClaimItem> claimItemList) {
        super(context, resource,claimItemList);
    }

    //Override of the getView method to adapt to the Claim Item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ClaimItem claimItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_claim_history, parent, false);
        }
        // Lookup view for data population
        TextView ClaimTitle = convertView.findViewById(R.id.claim_title_text);
        // Populate the data into the template view
        ClaimTitle.setText("Claim Title: "+ claimItem.getTitle()+"\n ID: "+String.valueOf(claimItem.getId()));
        return convertView;
    }
}

