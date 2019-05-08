package com.ais.eduworld.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import com.ais.eduworld.R;
import com.ais.eduworld.adapters.GalleryAdapter;
import com.ais.eduworld.adapters.WorkAdapter;
import com.ais.eduworld.fragments.DateDialog;
import com.ais.eduworld.model.WorkModel;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class HomeWorkActivity extends AppCompatActivity implements DateDialog.MyDateListner {
    private RecyclerView RvWork;
    private WorkAdapter adapter;
    private ImageView cal;
    private LinearLayout tap;
    public static TextView calDate;
    private ArrayList<String> hwlist;
    private ArrayList<WorkModel> titleList;
    private PrefConfig prefConfig;
    private String currentDate;
    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Home Work");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.mipmap.ic_launcher);
        abar.setHomeButtonEnabled(true);
        prefConfig = new PrefConfig(this);
        hwlist = new ArrayList<>();
        titleList = new ArrayList<>();
        currentDate = viewModel.getapidate();
        if(NetworkClass.isNetworkStatusAvailable(this)){
            new HomeWorkClass().execute(currentDate);
        }else {
            Toast.makeText(HomeWorkActivity.this,"Check your Network Connection",Toast.LENGTH_LONG).show();
        }



        initView();
    }

    public void initView() {
        RvWork = findViewById(R.id.workRv);
        cal = findViewById(R.id.ivCal);
        calDate = findViewById(R.id.tvdate1);
        tap  = findViewById(R.id.lltap);
        RvWork.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RvWork.setLayoutManager(manager);
        calDate.setText(currentDate);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                DateDialog dateDialog = new DateDialog();
                dateDialog.show(getSupportFragmentManager(),"myDialog");
            }
        });

        tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1 && hwlist.size() != 0){
                    Intent intent = new Intent(HomeWorkActivity.this,FilterActivity.class);
                    intent.putExtra("FilterList",hwlist);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Please select date or data is empty",Toast.LENGTH_LONG).show();
                }

            }
        });

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


    @Override
    public void onFinishUserDialog(String mDate,String nmonth) {
       calDate.setText(mDate);
      if(NetworkClass.isNetworkStatusAvailable(this)){
          new HomeWorkClass().execute(mDate);
      }else {
          Toast.makeText(HomeWorkActivity.this,"Check your Network Connection",Toast.LENGTH_LONG).show();

      }


    }

    private class HomeWorkClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            String mdate = strings[0];
            result = obj.getMethodURL(viewModel.HOMEWORK_URL+prefConfig.getStudentClass()+"/"+prefConfig.getStudentSection()+"/"+prefConfig.getSchoolId()+"/"+mdate);
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            titleList.clear();
            String jsonStr = result.toString();
            Log.e("Response",jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.optJSONObject(i);
                        JSONArray galleryArray = object.optJSONArray("DATA");
                        for (int gallery = 0; gallery<galleryArray.length(); gallery++){
                            WorkModel model = new WorkModel();
                            JSONObject inObj = galleryArray.optJSONObject(gallery);
                            String subname = inObj.optString("SUBNAME");
                            String cw      = inObj.optString("CW");
                            String hw      = inObj.optString("HW");
                            if(hw==""){
                                int strLength = hw.length();
                                String l1 = hw.substring(strLength-4);
                                Log.e("strjpg--->",l1);
                                if (l1.equalsIgnoreCase(".jpg")){
                                    hwlist.add(viewModel.HOMEWORK_IMAGE_URL+hw);
                                    model.setImage(viewModel.HOMEWORK_IMAGE_URL+hw);
                                    model.setTitle(subname);
                                    titleList.add(model);
                                }else {
                                    model.setTitle(subname);
                                    model.setClassWork(cw);
                                    model.setHomeWork(hw);
                                    titleList.add(model);
                                }
                            }else if(hw!=""){
                                int strLength = hw.length();
                                String l1 = hw.substring(strLength-strLength);
                                Log.e("strjpg--->",l1);
                                if (l1.equalsIgnoreCase(".jpg")){
                                    hwlist.add(viewModel.HOMEWORK_IMAGE_URL+hw);
                                    model.setImage(viewModel.HOMEWORK_IMAGE_URL+hw);
                                    model.setTitle(subname);
                                    titleList.add(model);
                                }else {
                                    model.setTitle(subname);
                                    model.setClassWork(cw);
                                    model.setHomeWork(hw);
                                    titleList.add(model);
                                }
                            }





                        }
                    }
                    adapter = new WorkAdapter(HomeWorkActivity.this,titleList);
                    RvWork.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
