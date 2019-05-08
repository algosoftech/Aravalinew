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
import com.ais.eduworld.model.AttendanceModel;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {
   private Context context;
   private ArrayList<AttendanceModel> attendanceList;

    public AttendanceAdapter(Context context,ArrayList<AttendanceModel> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendanceitem,parent,false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        AttendanceModel model = attendanceList.get(position);
        holder.mdate.setText(model.getMdate());
        holder.status.setText(model.getStatus());
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }


    public class AttendanceViewHolder extends RecyclerView.ViewHolder{
        private TextView mdate,status;
        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            mdate = itemView.findViewById(R.id.tvAttendanceDate);
            status = itemView.findViewById(R.id.tvStatus);
        }
    }
}
