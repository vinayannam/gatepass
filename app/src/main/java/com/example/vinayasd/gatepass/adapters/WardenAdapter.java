package com.example.vinayasd.gatepass.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vinayasd.gatepass.R;
import com.example.vinayasd.gatepass.modal.Form;
import com.example.vinayasd.gatepass.sql.DatabaseAccess;

import java.util.List;

/**
 * Created by vinayasd on 09/04/17.
 */

public class WardenAdapter extends RecyclerView.Adapter<WardenAdapter.WardenViewHolder> {
    private List<Form> passes;

    public class WardenViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewReason;
        public TextView textViewFrom;
        public TextView textViewTo;
        public Button acceptButton;
        public Button declineButton;


        public WardenViewHolder(View view) {
            super(view);
            textViewReason = (TextView) view.findViewById(R.id.recycler_reason_warden);
            textViewFrom = (TextView) view.findViewById(R.id.recycler_from_2_warden);
            textViewTo = (TextView) view.findViewById(R.id.recycler_to_2_warden);
            acceptButton = (Button) view.findViewById(R.id.recycler_warden_button);
            declineButton = (Button) view.findViewById(R.id.recycler_warden_un_button);
        }

    }

    public WardenAdapter(List<Form> passes) {
        this.passes = passes;
    }

    @Override
    public WardenAdapter.WardenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pass_reclcler_warden, parent, false);

        return new WardenAdapter.WardenViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WardenAdapter.WardenViewHolder holder, final int position) {
        holder.textViewReason.setText(passes.get(position).getName());
        holder.textViewFrom.setText(passes.get(position).getFromDate()+" "+passes.get(position).getFromTime());
        holder.textViewTo.setText(passes.get(position).getTodate()+" "+passes.get(position).getToTime());
        holder.acceptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(null);
                databaseAccess.wardenAccept(position,passes);
            }
        });
        holder.declineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(null);
                databaseAccess.wardenDecline(position,passes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return passes.size();
    }
}
