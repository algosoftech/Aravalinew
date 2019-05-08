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
import com.ais.eduworld.activities.HomeWorkActivity;
import com.ais.eduworld.adapters.WorkAdapter;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.NetworkClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

   private TextView busRout,busStop,driver,conductor,incharge,remarks;
   private PrefConfig prefConfig;
    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        busRout = view.findViewById(R.id.tvbusRout);
        busStop = view.findViewById(R.id.tvbusStop);
        driver  = view.findViewById(R.id.tvDriver);
       // conductor = view.findViewById(R.id.tvConductorNumber);
        incharge  = view.findViewById(R.id.tvTransportIncharge);
        remarks   = view.findViewById(R.id.tvRemarks);
        prefConfig = new PrefConfig(getContext());

        if(NetworkClass.isNetworkStatusAvailable(getActivity())){
            new TransportClass().execute();
        }else {
            Toast.makeText(getContext(),"Please Check your Connection",Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private class TransportClass extends AsyncTask<String,String,String>{
        JsonClass obj = new JsonClass();
        @Override
        protected String doInBackground(String... strings) {
            return obj.getStringResponseFromServer(viewModel.TRANSPORT_URL+prefConfig.getBusRout()+"&school_id="+prefConfig.getSchoolId());
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("ResponseBus",result);
            String jsonStr = result.toString();

            if(jsonStr==null) {
                Toast.makeText(getContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            } else if (jsonStr != null && !jsonStr.isEmpty()) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.optJSONObject(i);
                        JSONArray galleryArray = object.optJSONArray("DATA");
                        for (int gallery = 0; gallery<galleryArray.length(); gallery++){
                            JSONObject inObj = galleryArray.optJSONObject(gallery);
                            String routename = inObj.optString("routename");
                            String stop     = inObj.optString("stop");
                            String driver_NAME = inObj.optString("driver_NAME");
                            String driver_mob  = inObj.optString("driver_mob");
                            String cond_NAME   = inObj.optString("cond_NAME");
                            String cond_mob    = inObj.optString("cond_mob");
                            String tpt_NAME   = inObj.optString("tpt_NAME");
                            String tpt_mob    = inObj.optString("tpt_mob");
                            String status     = inObj.optString("status");
                            String Reamrks1   = inObj.optString("Reamrks");

                           busRout.setText(routename);
                           busStop.setText(stop);
                           driver.setText(driver_NAME+" "+driver_mob);
                         //  conductor.setText(cond_NAME+" "+cond_mob);
                           incharge.setText(tpt_NAME);
                           remarks.setText(Reamrks1);
                        }
                    }

                } catch (JSONException e) {
                    try {
                        JSONObject object = new JSONObject(result);
                        Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
//            else {
//                Toast.makeText(getContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
