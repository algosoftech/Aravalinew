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
import com.ais.eduworld.adapters.NotificationAdapter;
import com.ais.eduworld.adapters.SheetAdapter;
import com.ais.eduworld.model.DirectoryModel;
import com.ais.eduworld.model.NotificationModel;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView RvNotification;
    private NotificationAdapter adapter;
    private PrefConfig prefConfig;
    private ArrayList<NotificationModel> notiList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Notifications");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.mipmap.ic_launcher);
        abar.setHomeButtonEnabled(true);
        prefConfig = new PrefConfig(this);
        notiList = new ArrayList<>();
        initView();
    }

    public void initView(){
        RvNotification = findViewById(R.id.notiRv);
        RvNotification.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RvNotification.setLayoutManager(manager);
        if(NetworkClass.isNetworkStatusAvailable(this)){
            new NotificationClass().execute();
        }else {
            Toast.makeText(this,"Please Check your Network Connection",Toast.LENGTH_LONG).show();
        }





    }

    private class NotificationClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            result = obj.getMethodURL(viewModel.NOTIFICATION_URL+prefConfig.getSchoolId()+"&cls="+prefConfig.getStudentClass());
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            String jsonStr = "" ;
            if (result.toString() != ""){
                jsonStr = result.toString();
            }
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
                            NotificationModel model = new NotificationModel();
                            JSONObject dataObject = dirArray.optJSONObject(j);
                            String Name = dataObject.optString("Name");
                            String Description    = dataObject.optString("Description");
                            String Nfrom    = dataObject.optString("Nfrom");
                            String ndate   = dataObject.optString("ndate");

                            model.setName(Name);
                            model.setDesc(Description);
                            model.setNfrom(Nfrom);
                            model.setNdate(ndate);

                            notiList.add(model);
                        }
                    }

                    adapter = new NotificationAdapter(NotificationActivity.this,notiList);
                    RvNotification.setAdapter(adapter);

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
