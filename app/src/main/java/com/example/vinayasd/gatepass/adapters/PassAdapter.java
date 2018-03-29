package com.example.vinayasd.gatepass.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vinayasd.gatepass.GifActivity;
import com.example.vinayasd.gatepass.Main2Activity;
import com.example.vinayasd.gatepass.MainActivity;
import com.example.vinayasd.gatepass.R;
import com.example.vinayasd.gatepass.WardenActivity;
import com.example.vinayasd.gatepass.modal.Form;
import com.example.vinayasd.gatepass.sql.DatabaseAccess;

import java.util.List;

/**
 * Created by vinayasd on 07/04/17.
 */

public class PassAdapter extends RecyclerView.Adapter<PassAdapter.PassViewHolder> {

    private List<Form> passes;



    public class PassViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewReason;
        public TextView textViewFrom;
        public TextView textViewTo;
        public TextView colorView;



        public PassViewHolder(View view) {
            super(view);
            textViewReason = (TextView) view.findViewById(R.id.recycler_reason);
            textViewFrom = (TextView) view.findViewById(R.id.recycler_from_2);
            textViewTo = (TextView) view.findViewById(R.id.recycler_to_2);
            colorView = (TextView) view.findViewById(R.id.recycler_approval_2);


        }
    }

    public PassAdapter(List<Form> passes) {
        this.passes = passes;
    }

    @Override
    public PassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pass_recycler, parent, false);

        return new PassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PassViewHolder holder, int position) {
        holder.textViewReason.setText(passes.get(position).getReason());
        holder.textViewFrom.setText(passes.get(position).getFromDate()+" "+passes.get(position).getFromTime());
        holder.textViewTo.setText(passes.get(position).getTodate()+" "+passes.get(position).getToTime());
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(null);
        int cs = databaseAccess.colorState(position,passes);

        if(cs == 1){
            holder.colorView.setBackgroundColor(Color.CYAN);
        }
        else {
            if (cs == 2) {
                holder.colorView.setBackgroundColor(Color.GREEN);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(), GifActivity.class);
                        intent.putExtra("USERNAME","Accepted");
                        holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), GifActivity.class));
                    }
                });
            } else {
                holder.colorView.setBackgroundColor(Color.RED);
            }
        }
    }

    @Override
    public int getItemCount() {
        return passes.size();
    }


}
