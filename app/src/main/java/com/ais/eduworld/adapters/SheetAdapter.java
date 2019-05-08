package com.ais.eduworld.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.ais.eduworld.R;
import com.ais.eduworld.activities.AttachActivity;
import com.ais.eduworld.model.DirectoryModel;
import com.ais.eduworld.util.PrefConfig;

public class SheetAdapter extends RecyclerView.Adapter<SheetAdapter.SheetViewHolder> {
   private Context context;
   private ArrayList<DirectoryModel> sheetList;

    public SheetAdapter(Context context,ArrayList<DirectoryModel> sheetList) {
        this.context = context;
        this.sheetList = sheetList;
    }

    @NonNull
    @Override
    public SheetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sheetitem,parent,false);
        return new SheetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SheetViewHolder holder, int position) {
        if (position%2 == 0){
            holder.sheet.setBackgroundResource(R.drawable.dirbg2);
        }else if (position%2 != 0){
            holder.sheet.setBackgroundResource(R.drawable.dirbg1);
        }
        final DirectoryModel model = sheetList.get(position);
        holder.EndDate.setText(model.getEndate());
        holder.title.setText(model.getName());
        holder.calendar.setText(model.getNtitle());
        holder.attachment.setOnClickListener(new View.OnClickListener() {
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
        return sheetList.size();
    }

    public class SheetViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout sheet;
        private TextView EndDate,title,calendar,attachment;
        public SheetViewHolder(@NonNull View itemView) {
            super(itemView);
            sheet = itemView.findViewById(R.id.llsheet);
            EndDate = itemView.findViewById(R.id.tvEndDate);
            title   = itemView.findViewById(R.id.tvTitle);
            calendar = itemView.findViewById(R.id.tvCalendar);
            attachment = itemView.findViewById(R.id.tvAttachement);
        }
    }
}
