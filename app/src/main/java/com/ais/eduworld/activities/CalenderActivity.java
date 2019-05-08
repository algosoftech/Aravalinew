package com.ais.eduworld.activities;

import android.graphics.Color;
import android.os.AsyncTask;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ais.eduworld.util.NetworkClass;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.ais.eduworld.R;
import com.ais.eduworld.adapters.AttendanceAdapter;
import com.ais.eduworld.adapters.CalendarAdapter;
import com.ais.eduworld.model.AttendanceModel;
import com.ais.eduworld.model.CalendarModel;
import com.ais.eduworld.util.JsonClass;
import com.ais.eduworld.util.PrefConfig;
import com.ais.eduworld.util.viewModel;

public class CalenderActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForApi = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private boolean shouldShow = false;
    private CompactCalendarView compactCalendarView;
    private RecyclerView RvCalendar;
    private CalendarAdapter adapter;
    private ArrayList<CalendarModel> totalEventList;
    private ArrayList<String> monthlyEventList;
    private PrefConfig prefConfig;
    private String currentMonth,currentDay,currentYear,changeMonth,mformat;
    private ArrayList<Integer> commonList;
    public String[] currentArray;
    public String[] mutableArray;
    public String delimeter = "-";
    public String delimeter1= "/";
    List<String> mutableBookings;
    private ArrayList<Date> dateList;
    TextView textviewTitle,tvMonthTitle;
    private int inyear,inmonth;
    private Date date1;
    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
         textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Calendar");
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
//        textviewTitle.setTypeface(typeface);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.mipmap.ic_launcher);
        abar.setHomeButtonEnabled(true);
        prefConfig = new PrefConfig(this);
        totalEventList = new ArrayList<>();
        monthlyEventList = new ArrayList<>();
        commonList = new ArrayList<>();
        dateList = new ArrayList<>();
        mutableBookings = new ArrayList<>();
        String currentDate = viewModel.getapidate();
        currentArray = currentDate.split(delimeter);
        currentYear = currentArray[0];
        currentMonth = currentArray[1];
        currentDay = currentArray[2];
        events = new ArrayList<>();
        initView();
        if(NetworkClass.isNetworkStatusAvailable(this)){
                new getTotalDataClass().execute();

        }else {
            Toast.makeText(this,"Check your Network Connection",Toast.LENGTH_LONG).show();
        }

    }

    public void initView(){
        RvCalendar   = findViewById(R.id.calendarRv);
        RvCalendar.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RvCalendar.setLayoutManager(manager);
        tvMonthTitle = findViewById(R.id.tvMonthTitle);
        final ListView bookingsListView = findViewById(R.id.bookings_listview);
        final ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, mutableBookings);
        bookingsListView.setAdapter(adapter);
        compactCalendarView = findViewById(R.id.compactcalendar_view);

        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(false);
        //compactCalendarView.setIsRtl(true);

        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
//        Event ev1 = new Event(Color.GREEN, 1553144800000L, "Some extra data that I want to store.");
//        compactCalendarView.addEvent(ev1);



        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
//        Event ev2 = new Event(Color.GREEN, 1551676000000L);
//        compactCalendarView.addEvent(ev2);

        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
         // can also take a Date object

        // events has size 2 with the 2 events inserted previously
      //  Log.d(TAG, "Events: " + events);



       tvMonthTitle.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                tvMonthTitle.setText(dateFormatForMonth.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked)+ " with events " + bookingsFromMap);
                Log.e("mformat--->",dateFormatForApi.format(dateClicked));

                    if (NetworkClass.isNetworkStatusAvailable(CalenderActivity.this)){
                        new DateEventClass().execute(dateFormatForApi.format(dateClicked));

                    }else {
                        Toast.makeText(CalenderActivity.this,"Check your Network Connection",Toast.LENGTH_LONG).show();
                    }


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tvMonthTitle.setText(dateFormatForMonth.format(firstDayOfNewMonth));

                String scroldate = dateFormatForApi.format(firstDayOfNewMonth);

                currentArray = scroldate.split(delimeter);
                currentYear = currentArray[0];
                currentMonth = currentArray[1];
                currentDay = currentArray[2];
                if(NetworkClass.isNetworkStatusAvailable(CalenderActivity.this)){
                    new getTotalDataClass().execute();
                    dateList.clear();
                }else {
                    Toast.makeText(CalenderActivity.this,"Check your Network Connection",Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        tvMonthTitle.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
    }



    private class DateEventClass extends AsyncTask<String,String,JSONArray>{
        String url = "http://ais.aravalieduworld.com/APi/Customer/Calenderapidate?school_id=1&CWHWDATE=2018-12-31";
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            String mdate = strings[0];
            result = obj.getMethodURL(viewModel.DAY_EVENT_URL+prefConfig.getSchoolId()+"&CWHWDATE="+mdate);
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            String jsonStr = result.toString();
            Log.e("DayEventResponse",jsonStr);
            totalEventList.clear();
            events.clear();
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.optJSONObject(i);
                        JSONArray galleryArray = object.optJSONArray("DATA");
                        for (int gallery = 0; gallery<galleryArray.length(); gallery++){
                            CalendarModel model = new CalendarModel();
                            JSONObject inObj = galleryArray.optJSONObject(gallery);
                            String Ntitle = inObj.optString("Ntitle");
                            String NNAME   = inObj.optString("NNAME");
                            String ASSIGNMENT = inObj.optString("ASSIGNMENT");
                            String STARTDATE  = inObj.optString("STARTDATE");
                            model.setASSIGNMENT(ASSIGNMENT);
                            model.setNNAME(Ntitle);
                            model.setNNAME(NNAME);
                            model.setSTARTDATE(STARTDATE);

                            totalEventList.add(model);
                        }
                    }

                    adapter = new CalendarAdapter(CalenderActivity.this,totalEventList);
                    RvCalendar.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class getTotalDataClass extends AsyncTask<String,String,JSONArray>{
        JsonClass obj = new JsonClass();
        JSONArray result;
        @Override
        protected JSONArray doInBackground(String... strings) {
            result = obj.getMethodURL(viewModel.TOTAL_EVENT_URL+prefConfig.getStudentClass()+"&schoolid="+prefConfig.getSchoolId());
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            String jsonStr = result.toString();
            Log.e("TotalEventResponse",jsonStr);
            totalEventList.clear();
            compactCalendarView.removeAllEvents();
            String ordate = "";
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.optJSONObject(i);
                        JSONArray galleryArray = object.optJSONArray("DATA");
                        for (int gallery = 0; gallery<galleryArray.length(); gallery++){
                            CalendarModel model = new CalendarModel();
                            JSONObject inObj = galleryArray.optJSONObject(gallery);
                            String Ntitle = inObj.optString("Ntitle");
                            String NNAME   = inObj.optString("NNAME");
                            String ASSIGNMENT = inObj.optString("ASSIGNMENT");
                            String STARTDATE  = inObj.optString("STARTDATE");
                            ordate = STARTDATE.substring(0,10);


                            mutableArray = ordate.split(delimeter1);

//                            Log.e("apDate-->",mutableArray[0]);//month
//                            Log.e("cuMonth-->",currentArray[1]);//month
//                            Log.e("apDate1-->",mutableArray[1]);//day
//                            Log.e("apDate2-->",mutableArray[2]);//year
//                            Log.e("cuYear-->",currentArray[0]);//year

                            String muMonth = mutableArray[0];
                            String muYear  = mutableArray[2];
                            String cyear = muYear.substring(0,4);
                            inyear = Integer.parseInt(cyear);
                            inmonth = Integer.parseInt(muMonth);

                            mformat = mutableArray[1]+"-"+mutableArray[0]+"-"+cyear;
                            Log.e("apDate-->",mformat);//month

                            if (muMonth.length() == 1){
                                changeMonth = "0"+muMonth;

                                if ((currentMonth.contentEquals(changeMonth))){
                                    model.setSTARTDATE(STARTDATE);
                                    model.setASSIGNMENT(ASSIGNMENT);
                                    model.setNNAME(Ntitle);
                                    model.setNNAME(NNAME);
                                    Log.e("mutable","yes");
                                    long mtimeStamp = getTimeStampData(mformat);
                                    Event ev1 = new Event(Color.GREEN, mtimeStamp, "Some extra data that I want to store.");
                                    compactCalendarView.addEvent(ev1);
                                    totalEventList.add(model);
                                }
                            }else {
                                if (currentMonth.contentEquals(muMonth)){
                                    model.setSTARTDATE(STARTDATE);
                                    model.setASSIGNMENT(ASSIGNMENT);
                                    model.setNNAME(Ntitle);
                                    model.setNNAME(NNAME);
                                    long mtimeStamp = getTimeStampData(mformat);
                                    Event ev1 = new Event(Color.GREEN, mtimeStamp, "Some extra data that I want to store.");
                                    compactCalendarView.addEvent(ev1);

                                    Log.e("mutable","No");
                                    totalEventList.add(model);
                                }
                            }

                        }

                        adapter = new CalendarAdapter(CalenderActivity.this,totalEventList);
                        RvCalendar.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public long getTimeStampData(String mdate){
       // String str_date="21-03-2019";
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = (Date)formatter.parse(mdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Today is " +date.getTime());
        return date.getTime();
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
}
