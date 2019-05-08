package com.ais.eduworld.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.ais.eduworld.R;
import com.ais.eduworld.model.StudentsModel;
import com.ais.eduworld.util.PrefConfig;

import static com.ais.eduworld.R.color.green;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {
    private Context context;
    private PrefConfig prefConfig;
    private ArrayList<StudentsModel> studentList;

    public StudentListAdapter(Context context,ArrayList<StudentsModel> studentList) {
        this.context = context;
        this.studentList = studentList;
        prefConfig = new PrefConfig(context);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlistitem,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        final StudentsModel model = studentList.get(position);
        if(position==0) {
            holder.cardView.setBackgroundColor(Color.parseColor("#007ad0"));
        }else if(position==1){
            holder.cardView.setBackgroundColor(Color.parseColor("#61a327"));
        }
        holder.name.setText(model.getStudent_full_name());
        holder.school.setText(model.getSchool_name());
        holder.stuClass.setText(model.getStuclass());
        holder.roll.setText(model.getRoll_no());
        holder.section.setText(model.getSection());
        holder.father.setText(model.getFather_name());
        holder.admin.setText(model.getAdminNo());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefConfig.setAdminNo(model.getAdminNo());
                prefConfig.setSchoolName(model.getSchool_name());
                prefConfig.setSchoolId(model.getSchool_id());
                prefConfig.setStudentName(model.getStudent_full_name());
                prefConfig.setStudentClass(model.getStuclass());
                prefConfig.setStudentSection(model.getSection());
                prefConfig.setStudentRollNo(model.getRoll_no());
                prefConfig.setStudentDob(model.getDOB());
                prefConfig.setFatherName(model.getFather_name());
                prefConfig.setFatherMobile(model.getFather_mobno());
                prefConfig.setFatherEmail(model.getFather_emailid());
                prefConfig.setMotherName(model.getMother_name());
                prefConfig.setBusRout(model.getBusRout());
                prefConfig.setAddress(model.getAddress());
                prefConfig.setStudentPic(model.getStudent_pic());
                prefConfig.setAddress(model.getAddress());
                Intent intent = new Intent(context,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        private TextView name,school,stuClass,roll,section,father,admin;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardItem);
            cardView.setCardBackgroundColor(Color.BLUE);
            name = itemView.findViewById(R.id.tvName);
            stuClass = itemView.findViewById(R.id.tvClass);
            roll  = itemView.findViewById(R.id.tvRollNo);
            section = itemView.findViewById(R.id.tvSection);
            school  = itemView.findViewById(R.id.tvSchool);
            father  = itemView.findViewById(R.id.tvFatherName);
            admin  = itemView.findViewById(R.id.tvAdmin);
        }
    }
}
