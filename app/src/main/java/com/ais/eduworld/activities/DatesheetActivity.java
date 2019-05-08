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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.ais.eduworld.R;
import com.ais.eduworld.adapters.DirectoryAdapter;
import com.ais.eduworld.adapters.SheetAdapter;
import com.ais.eduworld.model.DirectoryModel;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class DatesheetActivity extends AppCompatActivity {
    private RecyclerView RvSheet;
    private SheetAdapter adapter;
    private PrefConfig prefConfig;
    private ArrayList<DirectoryModel> sheetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datesheet);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Datesheet/Syllabus/Planner");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
//        textviewTitle.setTypeface(typeface);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.mipmap.ic_launcher);
        abar.setHomeButtonEnabled(true);
        prefConfig = new PrefConfig(this);
        sheetList = new ArrayList<>();
        initView();
    }

    public void initView(){
        RvSheet = findViewById(R.id.sheetRv);
        RvSheet.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RvSheet.setLayoutManager(manager);
        if(NetworkClass.isNetworkStatusAvailable(this)){
            new DatesheetClass().execute();

        }else {
            Toast.makeText(DatesheetActivity.this,"Check your Network Connection",Toast.LENGTH_LONG).show();
        }


    }

     private class DatesheetClass extends AsyncTask<String,String,JSONArray>{

         JsonClass obj = new JsonClass();
         JSONArray result;
         @Override
         protected JSONArray doInBackground(String... strings) {
             result = obj.getMethodURL(viewModel.DATESHEET_URL+prefConfig.getStudentClass()+"&schoolid="+prefConfig.getSchoolId());
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
                         JSONObject jsonObject = jsonArray.getJSONObject(i);
                         //  String parentId = jsonObject.getString("Parentid");
                         JSONArray dirArray = jsonObject.optJSONArray("DATA");
                         for (int j = 0; j<dirArray.length(); j++){
                             DirectoryModel model = new DirectoryModel();
                             JSONObject dataObject = dirArray.optJSONObject(j);
                             String title = dataObject.optString("NNAME");
                             String mdate    = dataObject.optString("ENDATE");
                             String mpdf    = dataObject.optString("ASSIGNMENT");
                             String ntitle=dataObject.optString("Ntitle");

                             model.setName(title);
                             model.setAssign(mpdf);
                             model.setEndate(mdate);
                             model.setNtitle(ntitle);
                             sheetList.add(model);
                         }
                     }

                     adapter = new SheetAdapter(DatesheetActivity.this,sheetList);
                     RvSheet.setAdapter(adapter);

                 } catch (JSONException e) {
                     e.printStackTrace();
                     Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
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
