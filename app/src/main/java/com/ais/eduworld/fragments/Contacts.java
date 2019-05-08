package com.ais.eduworld.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.List;

import com.ais.eduworld.R;
import com.ais.eduworld.activities.DirectoryActivity;
import com.ais.eduworld.adapters.DirectoryAdapter;
import com.ais.eduworld.model.DirectoryModel;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contacts extends Fragment {
    private RecyclerView RvDirectory;
    private PrefConfig prefConfig;
    private List<DirectoryModel> dirList;
    private DirectoryAdapter adapter;

    public Contacts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        dirList = new ArrayList<>();
        prefConfig = new PrefConfig(getContext());
        RvDirectory = view.findViewById(R.id.directoryRv);
        RvDirectory.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        RvDirectory.setLayoutManager(manager);

        if(NetworkClass.isNetworkStatusAvailable(getContext())){
            new ContactClass().execute();

        }else {
            Toast.makeText(getContext(),"Please check your Network Connection",Toast.LENGTH_LONG).show();
        }




        return view;
    }

     private class ContactClass extends AsyncTask<String,String,JSONArray>{
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
                     adapter = new DirectoryAdapter(getContext(),dirList);
                     RvDirectory.setAdapter(adapter);

                 } catch (JSONException e) {
                     e.printStackTrace();
                     Toast.makeText(getContext(),"", Toast.LENGTH_SHORT).show();
                 }
             } else {
                 Toast.makeText(getContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
             }
         }
     }

}
