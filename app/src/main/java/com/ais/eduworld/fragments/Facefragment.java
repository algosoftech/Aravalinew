package com.ais.eduworld.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ais.eduworld.R;
import com.ais.eduworld.adapters.GalleryAdapter;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Facefragment extends Fragment {

    private WebView fbwv;
    private PrefConfig prefConfig;
    public Facefragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facefragment, container, false);
        fbwv = view.findViewById(R.id.fbwv);
        prefConfig = new PrefConfig(getContext());

        if (NetworkClass.isNetworkStatusAvailable(getContext())){
            new FacebookClass().execute();
        }else {
            Toast.makeText(getContext(),"Please check your Network Connection",Toast.LENGTH_LONG).show();
        }




        return view;
    }

    private class FacebookClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            result = obj.getMethodURL(viewModel.FACEBOOK_URL+prefConfig.getSchoolId());
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
                            JSONObject inObj = galleryArray.optJSONObject(gallery);
                            String path = inObj.optString("fb");

                            fbwv.loadUrl(path);
                            WebSettings webSettings = fbwv.getSettings();
                            webSettings.setJavaScriptEnabled(true);
                            fbwv.setWebViewClient(new WebViewClient());
                        }
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
