package com.ais.eduworld.activities;

import android.content.Intent;
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
import java.util.List;

import com.ais.eduworld.R;
import com.ais.eduworld.adapters.DirectoryAdapter;
import com.ais.eduworld.model.DirectoryModel;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class DirectoryActivity extends AppCompatActivity {
    private RecyclerView RvDirectory;
    private PrefConfig prefConfig;
    private List<DirectoryModel> dirList;
    private DirectoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Directory");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
//        textviewTitle.setTypeface(typeface);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.mipmap.ic_launcher);
        abar.setHomeButtonEnabled(true);
        dirList = new ArrayList<>();
        prefConfig = new PrefConfig(this);
        setContent();
    }

    public void setContent(){
        RvDirectory = findViewById(R.id.directoryRv);
        RvDirectory.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RvDirectory.setLayoutManager(manager);
    if(NetworkClass.isNetworkStatusAvailable(this)){
    new DirectoryClass().execute();
    }

    }

     private class DirectoryClass extends AsyncTask<String,String,JSONArray>{
         JsonClass obj = new JsonClass();
         JSONArray result;
         @Override
         protected JSONArray doInBackground(String... strings) {
             result = obj.getMethodURL(viewModel.DIRECTORY_URL+prefConfig.getSchoolId());
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
                             String Department = dataObject.optString("Department");
                             String name    = dataObject.optString("name");
                             String Mob1    = dataObject.optString("Mob1");
                             model.setDepartment(Department);
                             model.setName(name);
                             model.setMob(Mob1);
                             dirList.add(model);
                         }
                     }
                    adapter = new DirectoryAdapter(DirectoryActivity.this,dirList);
                    RvDirectory.setAdapter(adapter);

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
