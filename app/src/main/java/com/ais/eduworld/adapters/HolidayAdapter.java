package com.ais.eduworld.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.ais.eduworld.R;
import com.ais.eduworld.model.HolidayModel;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.HoliViewHolder> {
    private Context context;
    private ArrayList<HolidayModel> holidayList;

    public HolidayAdapter(Context context,ArrayList<HolidayModel> holidayList) {
        this.context = context;
        this.holidayList = holidayList;
    }

    @NonNull
    @Override
    public HoliViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holidayitem,parent,false);
        return new HoliViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoliViewHolder holder, int position) {
        if (position%2 == 0){
            holder.holiday.setBackgroundResource(R.drawable.dirbg2);
        }else if (position%2 != 0){
            holder.holiday.setBackgroundResource(R.drawable.dirbg1);
        }
        HolidayModel model = holidayList.get(position);
        holder.festival.setText(model.getFestival());
        holder.day.setText(model.getDay());
        holder.date1.setText(model.getDate1());
    }

    @Override
    public int getItemCount() {
        return holidayList.size();
    }

    public class HoliViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout holiday;
        private TextView festival,date1,day;
        public HoliViewHolder(@NonNull View itemView) {
            super(itemView);
            holiday = itemView.findViewById(R.id.llholiday);
            festival = itemView.findViewById(R.id.tvFestival);
            date1   = itemView.findViewById(R.id.tvDate);
            day    = itemView.findViewById(R.id.tvDay);
        }
    }
}
