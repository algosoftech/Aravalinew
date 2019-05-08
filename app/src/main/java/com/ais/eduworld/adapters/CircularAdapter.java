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
import java.util.zip.CheckedOutputStream;

import com.ais.eduworld.R;
import com.ais.eduworld.activities.AttachActivity;
import com.ais.eduworld.model.CircularModel;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class CircularAdapter extends RecyclerView.Adapter<CircularAdapter.CircularViewHolder> {
    private Context context;
    private ArrayList<CircularModel> circularList;

    public CircularAdapter(Context context,ArrayList<CircularModel> circularList) {
        this.context = context;
        this.circularList = circularList;
    }

    @NonNull
    @Override
    public CircularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circularitem,parent,false);
        return new CircularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CircularViewHolder holder, int position) {
        final CircularModel model = circularList.get(position);
       holder.notice.setText(model.getNtitle());
       holder.desc.setText(model.getNNAME());
       holder.attached.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              // String mcombineUrl = viewModel.PDF_BASE_URL+model.getASSIGNMENT();
               Intent intent = new Intent(context, AttachActivity.class);
               intent.putExtra("mpdf",model.getASSIGNMENT());
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return circularList.size();
    }

    public class CircularViewHolder extends RecyclerView.ViewHolder{
        private TextView attached,notice,desc;
        public CircularViewHolder(@NonNull View itemView) {
            super(itemView);
            attached = itemView.findViewById(R.id.tvAttached);
            notice  = itemView.findViewById(R.id.tvNotice);
            desc    = itemView.findViewById(R.id.tvDescCircular);
        }
    }
}
