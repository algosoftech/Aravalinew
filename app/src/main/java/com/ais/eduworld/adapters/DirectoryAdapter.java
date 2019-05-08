package com.ais.eduworld.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.ais.eduworld.R;
import com.ais.eduworld.model.DirectoryModel;

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.DirectoryViewHolder> {
    private Context context;
    private List<DirectoryModel> dirList = new ArrayList<>();

    public DirectoryAdapter(Context context, List<DirectoryModel> dirList) {
        this.context = context;
        this.dirList = dirList;
    }

    @NonNull
    @Override
    public DirectoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.directoryitem,parent,false);
        return new DirectoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectoryViewHolder holder, int position) {
        DirectoryModel data = dirList.get(position);
        holder.department.setText(data.getDepartment());
        holder.name.setText(data.getName());
        holder.mob.setText(data.getMob());
        if (position == 0){

        }else if (position%2 != 0){
           holder.change.setBackgroundResource(R.drawable.dirbg1);
        }
    }

    @Override
    public int getItemCount() {
        return dirList.size();
    }

    public class DirectoryViewHolder extends RecyclerView.ViewHolder{
        private TextView department,name,mob;
        private CardView cardView;
        private LinearLayout change;
        public DirectoryViewHolder(@NonNull View itemView) {
            super(itemView);
            department = itemView.findViewById(R.id.tvDepartment);
            name    = itemView.findViewById(R.id.tvName);
            mob     = itemView.findViewById(R.id.tvphone);
            cardView = itemView.findViewById(R.id.cardItem);
            change   = itemView.findViewById(R.id.llchange);
        }
    }
}
