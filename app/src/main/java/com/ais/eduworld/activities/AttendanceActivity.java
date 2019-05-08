package com.ais.eduworld.activities;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.ais.eduworld.R;
import com.ais.eduworld.adapters.AttendanceAdapter;
import com.ais.eduworld.adapters.WorkAdapter;
import com.ais.eduworld.fragments.DateDialog;
import com.ais.eduworld.model.AttendanceModel;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class AttendanceActivity extends AppCompatActivity implements DateDialog.MyDateListner {
    private RecyclerView attendanceRv;
    private AttendanceAdapter adapter;
    private ImageView mCal;
    private PrefConfig prefConfig;
    public  TextView mDate1,present,absent,total,holidays;
    private String currentDate;
    private String test = null;
    private Button all;
    private ArrayList<AttendanceModel> attendanceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Attendance");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
//        textviewTitle.setTypeface(typeface);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.mipmap.ic_launcher);
        abar.setHomeButtonEnabled(true);
        prefConfig = new PrefConfig(this);
        attendanceList = new ArrayList<>();
        currentDate = viewModel.getapidate();

        initView();
    }

    public void initView(){
        attendanceRv = findViewById(R.id.rvAttendance);
        mDate1 = findViewById(R.id.tvdate2);
        mCal  = findViewById(R.id.ivCal1);
        present = findViewById(R.id.tvpresents);
        absent  = findViewById(R.id.tvabsents);
        total   = findViewById(R.id.tvtotal);
        holidays = findViewById(R.id.tvholidays);
        attendanceRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        attendanceRv.setLayoutManager(manager);
        mDate1.setText(currentDate);
        if(NetworkClass.isNetworkStatusAvailable(this)){

                new AttendanceClass().execute(String.valueOf(viewModel.getMonth()));
                new TotalAttendanceClass().execute(String.valueOf(viewModel.getMonth()));

        }else {
            Toast.makeText(this,"Check your Network Connection",Toast.LENGTH_LONG).show();
        }


        mCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = new DateDialog();
                dateDialog.show(getSupportFragmentManager(),"myDialog");
            }
        });

    }

    @Override
    public void onFinishUserDialog(String mDate, String amonth) {
         mDate1.setText(mDate);

         if(NetworkClass.isNetworkStatusAvailable(this)){

                 new AttendanceClass().execute(amonth);
                 new TotalAttendanceClass().execute(amonth);

         }else {
             Toast.makeText(this,"Check your Network Connection",Toast.LENGTH_LONG).show();

         }


    }

    private class TotalAttendanceClass extends AsyncTask<String,String,JSONArray>{

        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            String month = strings[0];
            String murl = viewModel.TOTAL_ATTANDANCE+prefConfig.getAdminNo()+"&dateA="+month;
            result = obj.getMethodURL(murl);
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            String jsonStr = result.toString();
            Log.e("TotalAttendanceResponse",jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.optJSONObject(i);
                        JSONArray galleryArray = object.optJSONArray("DATA");
                        for (int gallery = 0; gallery<galleryArray.length(); gallery++){
                            JSONObject inObj = galleryArray.optJSONObject(gallery);
                            String absent1 = inObj.optString("absent");
                            String presentss   = inObj.optString("presentss");
                            String total1       = inObj.optString("Total");
                            String holiday1     = inObj.optString("holidays");

                            present.setText(presentss);
                            absent.setText(absent1);
                            total.setText(total1);
                            holidays.setText(holiday1);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class AttendanceClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
         @Override
         protected JSONArray doInBackground(String... strings) {
             String month = strings[0];
//             if (month.equalsIgnoreCase("mtest")){
//                 String murl = viewModel.ATTENDANCE_URL+prefConfig.getAdminNo()+"&mnt=";
//                 result = obj.getMethodURL(murl);
//             }else {
//
//             }
             String murl = viewModel.ATTENDANCE_URL+prefConfig.getAdminNo()+"&monhtno="+month;
             result = obj.getMethodURL(murl);

             return result;
         }

         @Override
         protected void onPostExecute(JSONArray result) {
             super.onPostExecute(result);
             String jsonStr = result.toString();
             Log.e("AttendanceResponse",jsonStr);
             attendanceList.clear();
             if (jsonStr != null) {
                 try {
                     JSONArray jsonArray = new JSONArray(jsonStr);
                     for (int i = 0; i<jsonArray.length(); i++){
                         JSONObject object = jsonArray.optJSONObject(i);
                         JSONArray galleryArray = object.optJSONArray("DATA");
                         for (int gallery = 0; gallery<galleryArray.length(); gallery++){
                             AttendanceModel model = new AttendanceModel();
                             JSONObject inObj = galleryArray.optJSONObject(gallery);
                             String mdate = inObj.optString("dateA");
                             String status   = inObj.optString("mark");
                             model.setMdate(mdate);
                             model.setStatus(status);
                             attendanceList.add(model);
                         }
                     }
                     adapter = new AttendanceAdapter(AttendanceActivity.this,attendanceList);
                     attendanceRv.setAdapter(adapter);
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             } else {
                 Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
             }
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
