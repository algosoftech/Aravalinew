package com.ais.eduworld.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.ais.eduworld.R;
import com.ais.eduworld.model.NotificationModel;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotiViewHolder> {
    private Context context;
    private ArrayList<NotificationModel> notiList;

    public NotificationAdapter(Context context,ArrayList<NotificationModel> notiList) {
        this.context = context;
        this.notiList = notiList;
    }

    @NonNull
    @Override
    public NotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifitem,parent,false);
        return new NotiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiViewHolder holder, int position) {
       NotificationModel model = notiList.get(position);
       holder.notiDate.setText(model.getNdate());
       holder.notiDesc.setText(model.getDesc());
       holder.notiFrom.setText(model.getNfrom());
       holder.notiName.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }

    public class NotiViewHolder extends RecyclerView.ViewHolder{
        private TextView notiName,notiFrom,notiDesc,notiDate;
        public NotiViewHolder(@NonNull View itemView) {
            super(itemView);
            notiName = itemView.findViewById(R.id.tvnotiName);
            notiFrom = itemView.findViewById(R.id.tvnotiFrom);
            notiDesc = itemView.findViewById(R.id.tvnotiDesc);
            notiDate = itemView.findViewById(R.id.tvnotiDate);
        }
    }
}
