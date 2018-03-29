package com.example.vinayasd.gatepass.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.vinayasd.gatepass.ParentActivity;
import com.example.vinayasd.gatepass.R;
import com.example.vinayasd.gatepass.modal.Form;
import com.example.vinayasd.gatepass.sql.DatabaseAccess;

import java.util.List;

/**
 * Created by vinayasd on 07/04/17.
 */

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ParentViewHolder>{
    private List<Form> passes;

    public class ParentViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewReason;
        public TextView textViewFrom;
        public TextView textViewTo;
        public Button acceptButton;
        public Button declineButton;


        public ParentViewHolder(View view) {
            super(view);
            textViewReason = (TextView) view.findViewById(R.id.recycler_reason_parent);
            textViewFrom = (TextView) view.findViewById(R.id.recycler_from_2_parent);
            textViewTo = (TextView) view.findViewById(R.id.recycler_to_2_parent);
            acceptButton = (Button) view.findViewById(R.id.recycler_parent_button);
            declineButton = (Button) view.findViewById(R.id.recycler_parent_un_button);
        }

    }

    public ParentAdapter(List<Form> passes) {
        this.passes = passes;
    }

    @Override
    public ParentAdapter.ParentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pass_reclcler_parent, parent, false);

        return new ParentAdapter.ParentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParentAdapter.ParentViewHolder holder, final int position) {
        holder.textViewReason.setText(passes.get(position).getReason());
        holder.textViewFrom.setText(passes.get(position).getFromDate()+" "+passes.get(position).getFromTime());
        holder.textViewTo.setText(passes.get(position).getTodate()+" "+passes.get(position).getToTime());
        holder.acceptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(null);
                 databaseAccess.parentAccept(position,passes);
            }
        });
        holder.declineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(null);
                databaseAccess.parentDecline(position,passes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return passes.size();
    }
}
