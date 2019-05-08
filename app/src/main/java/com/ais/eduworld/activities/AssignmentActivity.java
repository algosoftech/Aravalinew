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
import com.ais.eduworld.adapters.WorkAdapter;
import com.ais.eduworld.model.AssignModel;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class AssignmentActivity extends AppCompatActivity {
    private RecyclerView RvAssign;
    private AssignAdapter adapter;
    private PrefConfig prefConfig;
    private ArrayList<AssignModel> assignList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Assignments And WorkSheet");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
//        textviewTitle.setTypeface(typeface);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.mipmap.ic_launcher);
        abar.setHomeButtonEnabled(true);
        prefConfig = new PrefConfig(this);
        assignList = new ArrayList<>();
        initView();
    }

    public void initView() {
        RvAssign = findViewById(R.id.assignRv);
        RvAssign.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RvAssign.setLayoutManager(manager);
        try {
            if(NetworkClass.isNetworkStatusAvailable(this)){
                new AssignmentClass().execute();
            }

        } catch (Exception e) {
            e.printStackTrace();
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

    private class AssignmentClass extends AsyncTask<String, String, JSONArray> {
        JsonClass obj = new JsonClass();
        JSONArray result;

        @Override
        protected JSONArray doInBackground(String... strings) {
            result = obj.getMethodURL(viewModel.ASSIGNMENT_URL + prefConfig.getStudentClass() + "&school_id=" + prefConfig.getSchoolId() + "&section=" + prefConfig.getStudentSection());
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            String jsonStr = result.toString();
            Log.e("Response", jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.optJSONObject(i);
                        JSONArray galleryArray = object.optJSONArray("DATA");
                        for (int gallery = 0; gallery < galleryArray.length(); gallery++) {
                            AssignModel model = new AssignModel();
                            JSONObject inObj = galleryArray.optJSONObject(gallery);
                            String desc = inObj.optString("DESCRIPTION");
                            String assignments = inObj.optString("ASSIGNMENT");
                            String endate = inObj.optString("ENDATE");
                            model.setEndDate(endate);
                            model.setDesc(desc);
                            model.setAssign(assignments);
                            assignList.add(model);
                        }
                    }

                    adapter = new AssignAdapter(AssignmentActivity.this, assignList);
                    RvAssign.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
