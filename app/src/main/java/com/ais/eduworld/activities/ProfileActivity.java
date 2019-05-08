package com.ais.eduworld.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.ais.eduworld.R;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView image;
    private TextView ImageName,studentClass,studentSection,teacher;
    private TextView StudentName,MobNo,Email,Location,ZipCode,world;
    private PrefConfig prefConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("My Profile");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
//        textviewTitle.setTypeface(typeface);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.mipmap.ic_launcher);
        abar.setHomeButtonEnabled(true);

        prefConfig = new PrefConfig(this);
        initView();
    }

    public void initView(){
        ImageName = findViewById(R.id.tvImgName);
        studentClass = findViewById(R.id.tvClass1);
        studentSection = findViewById(R.id.tvSection1);
        teacher = findViewById(R.id.tvTeacher1);
        StudentName = findViewById(R.id.tvStudentName1);
        MobNo = findViewById(R.id.tvMobNo1);
        Email = findViewById(R.id.tvEmail1);
        image = findViewById(R.id.civStudent);
        Location = findViewById(R.id.tvLocation1);


        ImageName.setText(prefConfig.getStudentName());
        studentClass.setText(prefConfig.getStudentClass());
        studentSection.setText(prefConfig.getStudentSection());
        teacher.setText(prefConfig.getAdminNo());
        StudentName.setText(prefConfig.getStudentName());
        MobNo.setText(prefConfig.getFatherMobile());
        Email.setText(prefConfig.getFatherEmail());
        Location.setText(prefConfig.getAddress());

        Log.e("img--->",prefConfig.getStudentPic());

        if (prefConfig.getStudentPic() != ""){
            Glide.with(this)
                    .load(viewModel.PROFILE_IMAGE_URL+prefConfig.getStudentPic())
                    .into(image);
        }else {
            Glide.with(this)
                    .load(R.drawable.withoutshadow)
                    .into(image);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
