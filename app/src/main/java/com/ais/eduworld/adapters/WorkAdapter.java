package com.ais.eduworld.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.ais.eduworld.R;
import com.ais.eduworld.model.WorkModel;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.WorkViewHolder> {
    private Context context;
    private ArrayList<WorkModel> titleList;

    public WorkAdapter(Context context,ArrayList<WorkModel> titleList) {
        this.context = context;
        this.titleList = titleList;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workitem,parent,false);
        return new WorkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, int position) {
        WorkModel model = titleList.get(position);
        holder.title.setText(model.getTitle());
       // Log.e("mImg1--->",model.getImage());
        Log.e("mImg2--->",model.getClassWork());
        Log.e("mImg3--->",model.getHomeWork());
        Log.e("mImg4--->",model.getTitle());
        if (model.getImage() != null){
            holder.llcw.setVisibility(View.GONE);
            holder.llhw.setVisibility(View.GONE);
            holder.dummytext.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(model.getImage())
                    .into(holder.image);
        }else {
            holder.llcw.setVisibility(View.VISIBLE);
            holder.llhw.setVisibility(View.VISIBLE);
            holder.dummytext.setVisibility(View.GONE);
            holder.tvhw.setText(model.getHomeWork());
            holder.tvcw.setText(model.getClassWork());
            Glide.with(context)
                    .load(R.drawable.icon_cw)
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class WorkViewHolder extends RecyclerView.ViewHolder {
        private TextView title,tvhw,tvcw,dummytext;
        private ImageView image;
        private LinearLayout llhw,llcw;
        public WorkViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvtitle);
            llhw  = itemView.findViewById(R.id.llhw);
            llcw  = itemView.findViewById(R.id.llcw);
            tvcw  = itemView.findViewById(R.id.tvcw);
            tvhw  = itemView.findViewById(R.id.tvhw);
            image = itemView.findViewById(R.id.ivhw);
            dummytext = itemView.findViewById(R.id.tvdummytext);
        }
    }
}
