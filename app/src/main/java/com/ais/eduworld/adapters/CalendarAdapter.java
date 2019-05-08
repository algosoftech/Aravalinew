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
import com.ais.eduworld.model.CalendarModel;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
   private Context context;
   private ArrayList<CalendarModel> totalEventList;

    public CalendarAdapter(Context context,ArrayList<CalendarModel> totalEventList) {
        this.context = context;
        this.totalEventList = totalEventList;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendaritem,parent,false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
       final CalendarModel model = totalEventList.get(position);
       holder.event.setText(model.getNNAME());
       holder.eventDate.setText(model.getSTARTDATE());
       holder.eventAttach.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, AttachActivity.class);
               intent.putExtra("mpdf",model.getASSIGNMENT());
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return totalEventList.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder{
        private TextView event,eventDate,eventCal,eventAttach;
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            event = itemView.findViewById(R.id.tvEvent);
            eventDate = itemView.findViewById(R.id.tvStartDate);
            eventCal  = itemView.findViewById(R.id.tvEventCal);
            eventAttach = itemView.findViewById(R.id.tvCalendarAttache);
        }
    }
}
