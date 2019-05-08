package com.ais.eduworld.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.ais.eduworld.R;
import com.ais.eduworld.activities.FilterActivity;
import com.ais.eduworld.adapters.GalleryAdapter;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    public RecyclerView galleryRv;
    private PrefConfig prefConfig;
    private ArrayList<String> galleryList;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryRv = view.findViewById(R.id.rvGallery);
        prefConfig = new PrefConfig(getContext());
        galleryList = new ArrayList<>();
       // galleryRv.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        galleryRv.setLayoutManager(manager);

        if (NetworkClass.isNetworkStatusAvailable(getContext())){
            new GalleryClass(getActivity(),galleryRv).execute();
        }else {
            Toast.makeText(getContext(),"Please check your Network Connection",Toast.LENGTH_LONG).show();
        }




        return view;
    }

    private class GalleryClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
        Activity mContext;
        RecyclerView recyclerView;
        public GalleryClass(Activity activity,RecyclerView recyclerView){
            this.mContext = activity;
            this.recyclerView = recyclerView;
        }
        @Override
        protected JSONArray doInBackground(String... strings) {
            result = obj.getMethodURL(viewModel.GALLERY_LIST_URL+prefConfig.getSchoolId());
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
                            String path = inObj.optString("Photopath");
                            Log.d("images-->",path);
                            galleryList.add(viewModel.IMAGE_URL+path);
                        }
                    }
                    GalleryAdapter adapter = new GalleryAdapter(mContext,galleryList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
