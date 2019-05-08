package com.ais.eduworld.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.ais.eduworld.R;
import com.ais.eduworld.model.StudentsModel;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class StudentListActivity extends AppCompatActivity {
    private RecyclerView studentRv;
    private StudentListAdapter adapter;
    private String pid;
    private PrefConfig prefConfig;
    private  boolean doubleBackToExitPressedOnce = false;
    private ArrayList<StudentsModel> studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Select Child");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
//        textviewTitle.setTypeface(typeface);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(false);
        abar.setIcon(R.mipmap.ic_launcher);
        abar.setHomeButtonEnabled(false);

        prefConfig = new PrefConfig(this);
        pid = prefConfig.getParentId();
        studentList = new ArrayList<>();

        initView();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            StudentListActivity.this.finish();
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(findViewById(android.R.id.content),"Please click back again to exit", Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void initView(){
        studentRv = findViewById(R.id.rvStudentList);
        studentRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        studentRv.setLayoutManager(manager);

        if (NetworkClass.isNetworkStatusAvailable(this)){
            new StudentListClass().execute();
        }else {
            Toast.makeText(StudentListActivity.this,"Please check your Network Connection",Toast.LENGTH_LONG).show();
        }





    }

    private class StudentListClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            result = obj.getMethodURL(viewModel.STUDENT_LIST_URL+"/"+pid);
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            String jsonStr = result.toString();
            Log.e("Response",jsonStr);
            if (jsonStr != null) {
                try {

                    //  JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i<jsonArray.length(); i++){
                        StudentsModel model = new StudentsModel();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String adminNo = jsonObject.getString("Admno");
                        String school_name = jsonObject.getString("school_name");
                        String school_id = jsonObject.getString("school_id");
                        String student_full_name = jsonObject.getString("student_full_name");
                        String stuclass = jsonObject.getString("stuclass");
                        String section = jsonObject.getString("section");
                        String roll_no = jsonObject.getString("roll_no");
                        String DOB = jsonObject.getString("DOB");
                        String father_name = jsonObject.getString("father_name");
                        String father_mobno = jsonObject.getString("father_mobno");
                        String father_emailid = jsonObject.getString("father_emailid");
                        String mother_name = jsonObject.getString("mother_name");
                        String student_pic = jsonObject.getString("student_pic");
                        String busRout   = jsonObject.getString("bus_route");
                        String address   = jsonObject.optString("address");

                        Log.e("mimsg-->",student_pic);


                        model.setDOB(DOB);
                        model.setFather_emailid(father_emailid);
                        model.setFather_mobno(father_mobno);
                        model.setFather_name(father_name);
                        model.setMother_name(mother_name);
                        model.setRoll_no(roll_no);
                        model.setSchool_name(school_name);
                        model.setSchool_id(school_id);
                        model.setStudent_full_name(student_full_name);
                        model.setStuclass(stuclass);
                        model.setSection(section);
                        model.setAdminNo(adminNo);
                        model.setStudent_pic(student_pic);
                        model.setBusRout(busRout);
                        model.setAddress(address);
                        studentList.add(model);
                    }
                    adapter = new StudentListAdapter(StudentListActivity.this,studentList);
                    studentRv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
