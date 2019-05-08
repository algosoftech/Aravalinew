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
import com.ais.eduworld.model.ResultModel;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private Context context;
    private ArrayList<ResultModel> resultList;

    public ResultAdapter(Context context,ArrayList<ResultModel> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultitem,parent,false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int i) {
        final ResultModel model = resultList.get(i);
        holder.exam.setText(model.getExam());
        holder.subject.setText(model.getSubject());

        holder.marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AttachActivity.class);
                intent.putExtra("mpdf",model.getMArksObtain());
                context.startActivity(intent);
            }
        });

        holder.grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AttachActivity.class);
                intent.putExtra("mpdf",model.getGrdae());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{
        private TextView exam,subject,marks,grade;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            exam = itemView.findViewById(R.id.tvExam);
            subject = itemView.findViewById(R.id.tvSubject);
            marks   = itemView.findViewById(R.id.tvMObtain);
            grade   = itemView.findViewById(R.id.tvGrade);
        }
    }
}
