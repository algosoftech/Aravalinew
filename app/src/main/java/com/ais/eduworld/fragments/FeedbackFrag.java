package com.ais.eduworld.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ais.eduworld.R;
import com.ais.eduworld.adapters.GalleryAdapter;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFrag extends Fragment implements View.OnClickListener {

    private LinearLayout submit;
    private EditText title,message;
    private PrefConfig prefConfig;
    public FeedbackFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
         submit = view.findViewById(R.id.llSubmit);
         title  = view.findViewById(R.id.etTitle);
         message = view.findViewById(R.id.etDesc);
         prefConfig = new PrefConfig(getContext());
         submit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        String title1 = title.getText().toString();
        String msg   = message.getText().toString();
        try {
              if (title1.length()==0){
                  title.setError("Please enter title");
                  title.requestFocus();
              }else if (msg.length() == 0){
                  message.setError("Please enter message");
                  message.requestFocus();
              }else {
                  new FeedBackClass().execute(title1,msg);
              }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class FeedBackClass extends AsyncTask<String,String,String>{
        JsonClass obj = new JsonClass();
        String result;

        @Override
        protected String doInBackground(String... strings) {
            String sub = strings[0];
            String msg = strings[1];
            Log.e("msub",sub+"...msg--."+msg);
            Log.e("admin--->",prefConfig.getAdminNo());
            result = obj.getRegistrationFromURL(viewModel.FEEDBACK_URL+prefConfig.getAdminNo()+"&subject="+sub+"&desc="+msg);
         //  result = obj.getRegistrationFromURL("http://ais.aravalieduworld.com/APi/Customer/InserData?Admno=2018001&subject=sanjeevtest&desc=kih");
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String jsonStr = result.toString();
            Log.e("ResponseFeedback",result);
            if (jsonStr != null) {
                   title.setText("");
                   message.setText("");
                Toast.makeText(getContext(), "Successfully submitted feedback", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
