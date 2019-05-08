package com.ais.eduworld.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.ais.eduworld.R;
import com.ais.eduworld.activities.AttachActivity;
import com.ais.eduworld.model.AssignModel;

public class AssignAdapter extends RecyclerView.Adapter<AssignAdapter.AssignViewHolder> {
   private Context context;
   private ArrayList<AssignModel> assignList;

    public AssignAdapter(Context context,ArrayList<AssignModel> assignList) {
        this.context = context;
        this.assignList = assignList;
    }

    @NonNull
    @Override
    public AssignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignitem,parent,false);
        return new AssignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignViewHolder holder, int position) {
        final AssignModel model = assignList.get(position);
        holder.desc.setText((position+1)+"."+model.getDesc());
        holder.endDate.setText(model.getEndDate());
        holder.attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AttachActivity.class);
                intent.putExtra("mpdf",model.getAssign());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assignList.size();
    }

    public class AssignViewHolder extends RecyclerView.ViewHolder{
        private TextView desc,attach,endDate;
        public AssignViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.tvdesc);
            attach = itemView.findViewById(R.id.tvAttach);
            endDate = itemView.findViewById(R.id.tvendDate);
        }
    }
}
