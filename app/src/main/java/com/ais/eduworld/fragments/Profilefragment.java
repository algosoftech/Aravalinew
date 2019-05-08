package com.ais.eduworld.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.ais.eduworld.R;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profilefragment extends Fragment {
    private CircleImageView image;
    private TextView ImageName,studentClass,studentSection,teacher;
    private TextView StudentName,MobNo,Email,Location,ZipCode,world;
    private PrefConfig prefConfig;
    public Profilefragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profilefragment, container, false);
         image  = view.findViewById(R.id.civuser);
         ImageName = view.findViewById(R.id.tvuname);
        studentClass = view.findViewById(R.id.tvClass);
        studentSection = view.findViewById(R.id.tvSection);
        teacher = view.findViewById(R.id.tvTeacher);
        StudentName = view.findViewById(R.id.tvStudentName);
        MobNo = view.findViewById(R.id.tvMobNo);
        Email = view.findViewById(R.id.tvEmail);
        Location = view.findViewById(R.id.tvLocation);
        ZipCode = view.findViewById(R.id.tvZipCode);
        world = view.findViewById(R.id.tvWorld);

        prefConfig = new PrefConfig(getContext());
        ImageName.setText(prefConfig.getStudentName());
        studentClass.setText(prefConfig.getStudentClass());
        studentSection.setText(prefConfig.getStudentSection());
        teacher.setText(prefConfig.getAdminNo());
        StudentName.setText(prefConfig.getStudentName());
        MobNo.setText(prefConfig.getFatherMobile());
        Email.setText(prefConfig.getFatherEmail());
        Location.setText(prefConfig.getAddress());

        if (prefConfig.getStudentPic() != ""){
            Glide.with(getContext())
                    .load(viewModel.PROFILE_IMAGE_URL+prefConfig.getStudentPic())
                    .into(image);
        }else {
            Glide.with(getContext())
                    .load(R.drawable.withoutshadow)
                    .into(image);
        }

        return view;
    }

}
