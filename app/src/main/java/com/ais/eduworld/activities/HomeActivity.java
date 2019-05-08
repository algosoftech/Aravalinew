package com.ais.eduworld.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.ais.eduworld.fragments.changePasswordfragment;
import com.ais.eduworld.util.NetworkClass;
import com.google.firebase.iid.FirebaseInstanceId;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.ais.eduworld.R;
import com.ais.eduworld.adapters.GalleryAdapter;
import com.ais.eduworld.adapters.HomePagerAdapter;
import com.ais.eduworld.expand.GenreAdapter;
import com.ais.eduworld.fragments.Contacts;
import com.ais.eduworld.fragments.EventFragment;
import com.ais.eduworld.fragments.Facefragment;
import com.ais.eduworld.fragments.FeedbackFrag;
import com.ais.eduworld.fragments.GalleryFragment;
import com.ais.eduworld.fragments.Homefragment;
import com.ais.eduworld.fragments.Locationfragment;
import com.ais.eduworld.fragments.Newsfragment;
import com.ais.eduworld.fragments.Profilefragment;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;


import static com.ais.eduworld.expand.GenreDataFactory.makeGenres;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager homeSliderPager;
    private LinearLayout sliderDots;
    private HomePagerAdapter sliderAdapter;
    private ArrayList<String> sliderList;
    private ImageView[] dots;
    private int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 700;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4500;
    public GenreAdapter adapter;
    Toolbar toolbar;
    private Fragment fragment;
    private PrefConfig prefConfig;
    private TextView aboutText;
    private Typeface typeface;
    private MenuItem item1,item2,item3,item4,item5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");


        initview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     if(item.getItemId()==R.id.action_settings){
         Intent i=new Intent(HomeActivity.this,StudentListActivity.class);
         startActivity(i);
     }

     return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void initview(){
        homeSliderPager = findViewById(R.id.viewPage);
        aboutText  = findViewById(R.id.tvAbout);
        sliderDots = findViewById(R.id.SliderDots);
        sliderList = new ArrayList<>();
        prefConfig = new PrefConfig(this);
        aboutText.setTypeface(typeface);
        try {
            if(NetworkClass.isNetworkStatusAvailable(this)){
                new SliderClass().execute();
                new SchoolClass().execute();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        expandfunction();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sendToken();
    }

    public void sendToken(){
          String mtoken = FirebaseInstanceId.getInstance().getToken();
          Log.e("myToken--->",mtoken);
          if (mtoken != null){
              if(NetworkClass.isNetworkStatusAvailable(this)){
                  new SendTokenClass().execute(mtoken);
              }

          }
    }

    private class SendTokenClass extends AsyncTask<String,String,String>{
        JsonClass obj = new JsonClass();
        String result;
        @Override
        protected String doInBackground(String... strings) {
            String sendtoken = strings[0];
            Log.e("AdmissionNo",""+prefConfig.getAdminNo());
            result = obj.getStringResponseFromServer(viewModel.TOKEN_URL+prefConfig.getAdminNo()+"&PUBLISH="+sendtoken);

//            result = obj.getRegistrationFromURL(viewModel.TOKEN_URL+"20180001&PUBLISH=125455555");

            Log.e("SendResult",""+result);
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TokenStringSend",""+result);
//            String jsonStr = result.toString();
//            Log.e("ResponseToken",result);
            if (result != null&& !result.isEmpty()) {

            } else if(result==null) {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }

//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            String jsonStr = result.toString();
//            Log.e("ResponseFeedback",result);
//            if (jsonStr != null&& !jsonStr.isEmpty()) {
//
//                Toast.makeText(HomeActivity.this, "Successfully submitted feedback", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(HomeActivity.this, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    private class SchoolClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            result = obj.getMethodURL(viewModel.LOCATION_URL+prefConfig.getSchoolId());
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            String jsonStr = result.toString();
            Log.e("ResponseSchoolClass",""+jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.optJSONObject(i);
                        JSONArray galleryArray = object.optJSONArray("DATA");
                        for (int gallery = 0; gallery<galleryArray.length(); gallery++){
                            JSONObject inObj = galleryArray.optJSONObject(gallery);
                            String name1 = inObj.optString("name");
                            String about = inObj.optString("About");
                            toolbar.setTitle(name1);
                            aboutText.setText(about);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SliderClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            try {
                result = obj.getMethodURL(viewModel.SLIDER_URL+prefConfig.getSchoolId());
                return result;
            }catch (Exception e){
                e.printStackTrace();
                return result.put("empty");
            }

        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            String jsonStr = result.toString();
            Log.e("ResponseSlider",jsonStr);
            if (jsonStr != null&& !jsonStr.isEmpty()) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.optJSONObject(i);
                        JSONArray galleryArray = object.optJSONArray("DATA");
                        for (int gallery = 0; gallery<galleryArray.length(); gallery++){
                            JSONObject inObj = galleryArray.optJSONObject(gallery);
                            String path = inObj.optString("Photopath");
                            Log.d("images-->",path);
                            sliderList.add(viewModel.SLIDER_BASE_URL+path);
                        }
                    }

                    sliderAdapter = new HomePagerAdapter(HomeActivity.this, sliderList);
                    homeSliderPager.setAdapter(sliderAdapter);

                    displayImage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if(jsonStr==null) {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.action_one:
                    toolbar.setTitle("Aravali International School");
                    fragment = new Homefragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_two:
                    toolbar.setTitle("Transport");
                    fragment = new EventFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_three:
                    toolbar.setTitle("Gallery");
                    fragment = new GalleryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_four:
                    toolbar.setTitle("Contact");
                    fragment = new Contacts();
                    loadFragment(fragment);
                    return true;
                case R.id.action_five:
                    toolbar.setTitle("Location");
                    fragment = new Locationfragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }

    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void expandfunction(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        adapter = new GenreAdapter(makeGenres(),HomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void displayImage() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == sliderList.size()) {
                    currentPage = 0;
                }
                homeSliderPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        dots = new ImageView[sliderList.size()];

        for (int i = 0; i < sliderList.size(); i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDots.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        homeSliderPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < sliderList.size(); i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                toolbar.setTitle("Aravali International School");
                fragment = new Homefragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_profile:
                toolbar.setTitle("My Profile");
                fragment = new Profilefragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_news:
                toolbar.setTitle("News");
                fragment = new Newsfragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_gallery:
                toolbar.setTitle("Gallery");
                fragment = new GalleryFragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_achievement:
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_feedback:
                toolbar.setTitle("Feedback");
                fragment = new FeedbackFrag();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_appUpdate:
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_likeFb:
                toolbar.setTitle("Like Us On Facebook");
                fragment = new Facefragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_Password:
                toolbar.setTitle("Change Password");
                fragment=new changePasswordfragment();
                loadFragment(fragment);
                drawer.closeDrawer(GravityCompat.START);
//                String url = "http://aravalieduworld.com/PasswordReset.aspx";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
                return true;
//                Intent i=new Intent().setAction("http://aravalieduworld.com/PasswordReset.aspx");
//                startActivity(i);
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you really want to exit?")
                        .setTitle("Logout");


                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent1 = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(intent1);
                        prefConfig.setLoggedIn(false);
                        finish();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert =builder.create();
                alert.show();
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_studentList:
                 Intent intent = new Intent(this,StudentListActivity.class);
                 startActivity(intent);
                return true;
        }
        return false;
    }
}
