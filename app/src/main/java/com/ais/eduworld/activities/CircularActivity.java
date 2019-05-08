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
import com.ais.eduworld.adapters.AssignAdapter;
import com.ais.eduworld.adapters.CircularAdapter;
import com.ais.eduworld.model.AssignModel;
import com.ais.eduworld.model.CircularModel;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class CircularActivity extends AppCompatActivity {
    private RecyclerView RvCircular;
    private CircularAdapter adapter;
    private PrefConfig prefConfig;
    private ArrayList<CircularModel> circularList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular);
        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Circular");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
//        textviewTitle.setTypeface(typeface);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.mipmap.ic_launcher);
        abar.setHomeButtonEnabled(true);
        prefConfig = new PrefConfig(this);
        circularList = new ArrayList<>();
        initView();
    }

    public void initView(){
        RvCircular = findViewById(R.id.circularRv);
        RvCircular.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RvCircular.setLayoutManager(manager);

        if(NetworkClass.isNetworkStatusAvailable(this)){
            new CircularClass().execute();
        }else {
            Toast.makeText(CircularActivity.this,"Check your Network Connection",Toast.LENGTH_LONG).show();
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

    private class CircularClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            result = obj.getMethodURL(viewModel.CIRCULARLIST_URL+prefConfig.getStudentClass()+"&school_id="+prefConfig.getSchoolId());
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
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.optJSONObject(i);
                        JSONArray galleryArray = object.optJSONArray("DATA");
                        for (int gallery = 0; gallery<galleryArray.length(); gallery++){
                            CircularModel model = new CircularModel();
                            JSONObject inObj = galleryArray.optJSONObject(gallery);
                            String nTitle = inObj.optString("Ntitle");
                            String name      = inObj.optString("NNAME");
                            String assign = inObj.optString("ASSIGNMENT");

                            model.setASSIGNMENT(assign);
                            model.setNNAME(name);
                            model.setNtitle(nTitle);
                            circularList.add(model);
                        }
                    }

                    adapter = new CircularAdapter(CircularActivity.this,circularList);
                    RvCircular.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
