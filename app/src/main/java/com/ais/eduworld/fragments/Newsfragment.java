package com.ais.eduworld.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ais.eduworld.R;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Newsfragment extends Fragment {

    private TextView desc;
    private PrefConfig prefConfig;
    public Newsfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_newsfragment, container, false);
        desc  = view.findViewById(R.id.tvNewsDesc);
        prefConfig = new PrefConfig(getContext());
        try {
             new NewsClass().execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    private class NewsClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            result = obj.getMethodURL(viewModel.NEWS_URL+prefConfig.getSchoolId());
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
                           String des  = object.optString("DESCRIPTION");
                          desc.setText(des);
                      }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
