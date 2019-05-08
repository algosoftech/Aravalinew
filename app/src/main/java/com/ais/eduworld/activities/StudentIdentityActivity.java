package com.ais.eduworld.activities;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.bumptech.glide.Glide;

import com.ais.eduworld.R;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentIdentityActivity extends AppCompatActivity {
    private TextView admin,idNo,name,fatherName,motherName,classSection,contact,roll,address,tvSchoolName,tvSchoolAddress,tvSchoolPhone;
    private CircleImageView idImage;
    private PrefConfig prefConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_identity);
        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Identity Card");
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

    public void initView(){
        tvSchoolName= findViewById(R.id.school_name);
        tvSchoolAddress= findViewById(R.id.school_address);
        tvSchoolPhone= findViewById(R.id.school_phone_no);
        admin = findViewById(R.id.tvadmin);
        idNo  = findViewById(R.id.tvIdNo);
        name = findViewById(R.id.tvName1);
        fatherName = findViewById(R.id.tvFName);
        motherName = findViewById(R.id.tvMotherName);
        classSection = findViewById(R.id.tvSection);
        contact = findViewById(R.id.tvContact);
        roll = findViewById(R.id.tvRoll);
        address = findViewById(R.id.tvAddress);
        idImage  = findViewById(R.id.ivIdcard);
        admin.setText(prefConfig.getAdminNo());
        idNo.setText(prefConfig.getParentId());
        name.setText(prefConfig.getStudentName());
        fatherName.setText(prefConfig.getFatherName());
        motherName.setText(prefConfig.getMotherName());
        classSection.setText(prefConfig.getStudentClass()+" "+prefConfig.getStudentSection());
        contact.setText(prefConfig.getFatherMobile());
        roll.setText(prefConfig.getRollNo());
        address.setText(prefConfig.getAddress());
        if (prefConfig.getStudentPic() != ""){
            Glide.with(this)
                    .load(viewModel.PROFILE_IMAGE_URL+prefConfig.getStudentPic())
                    .into(idImage);
        }else {
            Glide.with(this)
                    .load(R.drawable.withoutshadow)
                    .into(idImage);
        }
        if(NetworkClass.isNetworkStatusAvailable(this)) {
            new GetSchoolDetails().execute();
        }else{
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private class GetSchoolDetails extends AsyncTask<String,String,JSONArray> {
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            result = obj.getMethodURL(viewModel.SCHOOL_DETAILS_HEADER+prefConfig.getSchoolId());
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            String jsonStr = result.toString();
            Log.e("Response",jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    JSONObject object = jsonArray.optJSONObject(0);
                    if (object.getBoolean("SUCCESS")) {
                        JSONArray schoolDetailsArray = object.optJSONArray("DATA");
                            JSONObject inObj = schoolDetailsArray.getJSONObject(0);
                            String schooName= inObj.getString("name");
                            String schoolAddress = inObj.getString("Address");
                            String phoneNo1 = inObj.getString("Mob1");
                            String phoneNo2 =inObj.getString("mob2");
                            Log.e("ResultToSet",""+schooName+"\n "+schoolAddress+"\n "+phoneNo1+"\n "+phoneNo2);
                            tvSchoolName.setText(schooName);
                            tvSchoolAddress.setText(schoolAddress);
                            tvSchoolPhone.setText(phoneNo1+" , "+phoneNo2);
                    }
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
