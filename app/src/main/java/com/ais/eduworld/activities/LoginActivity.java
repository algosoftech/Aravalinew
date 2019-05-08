package com.ais.eduworld.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ais.eduworld.R;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout loginBtn;
    private EditText admissionNo,password;
    private PrefConfig prefConfig;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefConfig = new PrefConfig(this);
        initView();

    }

    public void initView(){
        loginBtn = findViewById(R.id.llsignIn);
        admissionNo = findViewById(R.id.etAdmissionNo);
        password    = findViewById(R.id.etPassword);
        String uid = prefConfig.getAdminNo();
        if (uid != ""){
            admissionNo.setText(uid);
        }
        loginBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llsignIn:
                String admission = admissionNo.getText().toString();
                String pass   = password.getText().toString();
                if (admission.length() == 0){
                    admissionNo.setError("Please enter admission number");
                    admissionNo.requestFocus();
                }else if (pass.length() == 0){
                    password.setError("Please enter password");
                    password.requestFocus();
                }else if(NetworkClass.isNetworkStatusAvailable(this)) {

                    new LoginClass().execute(admissionNo.getText().toString(),password.getText().toString());
                }else {
                    Toast.makeText(LoginActivity.this,"Check your network Connection",Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    private class LoginClass extends AsyncTask<String,String,JSONArray>{


        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            String admin = strings[0];
            String  pass = strings[1];
            result = obj.getMethodURL(viewModel.LOGIN_URL+"/"+admin+"/"+pass);
            return result;
        }

        @Override
        protected void onPreExecute() {
            progressDialog= new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(JSONArray result) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            super.onPostExecute(result);
            String jsonStr = result.toString();
            Log.e("ResponseLogin",jsonStr);
            if (jsonStr != null) {
                try {

                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.optJSONObject(i);
                        if(jsonObject.getBoolean("SUCCESS")==true){
                            JSONArray jsonArray1=jsonObject.optJSONArray("DATA");
                            for(int p=0;p<jsonArray1.length();p++){
                                JSONObject jsonObject1=jsonArray1.optJSONObject(p);
                                String parentId = jsonObject1.getString("Parentid");
                                System.out.println("mpId---->"+parentId);
                                prefConfig.setParentId(parentId);
                                prefConfig.setLoggedIn(true);

                            }
                        }

                    }

                    Intent intent = new Intent(LoginActivity.this,StudentListActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
                }
            } else if(jsonStr==null|| jsonStr.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
