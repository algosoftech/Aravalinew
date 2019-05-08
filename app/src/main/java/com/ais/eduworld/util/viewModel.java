package com.ais.eduworld.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class viewModel {
    public static String BASE_URL = "http://ais.aravalieduworld.com/";
    public static String SLIDER_BASE_URL = "http://ais.aravalieduworld.com/";
    public static final String SLIDER_URL = BASE_URL + "APi/Customer/Getslider?schoolnameholid=";
    public static final String LOGIN_URL = BASE_URL + "APi/Customer/Getbyparent";
    public static final String STUDENT_LIST_URL = BASE_URL + "APi/Customer/GetbyparentID";
    public static final String IMAGE_URL = BASE_URL + "/gallery/";
    public static final String HOMEWORK_IMAGE_URL = "http://ais.aravalieduworld.com/images/";
    public static final String PROFILE_IMAGE_URL = "http://ais.aravalieduworld.com/photos/";
    public static final String GALLERY_LIST_URL = BASE_URL + "APi/Customer/Getgallery?schoolnameholid=";
    public static final String NEWS_URL = BASE_URL + "APi/Customer/Getnews?school_id=";
    public static final String FEEDBACK_URL = BASE_URL + "APi/Customer/InserData?Admno=";
    public static final String DIRECTORY_URL = BASE_URL + "APi/Customer/GetlistDirectory?schoolname=";
    public static final String FACEBOOK_URL = BASE_URL + "APi/Customer/GETsocial?schoolid=";
    public static final String LOCATION_URL = BASE_URL + "APi/Customer/Getschoolinfo?schoolid=";
    public static final String HOMEWORK_URL = BASE_URL + "APi/Customer/Getlisthwcw/";
    public static final String ASSIGNMENT_URL = BASE_URL + "APi/Customer/GetAssignment?cls=";
    public static final String HOLIDAYLIST_URL = BASE_URL + "APi/Customer/Getlistholiday?schoolnameholid=";
    public static final String CIRCULARLIST_URL = BASE_URL + "APi/Customer/circulars?cls=";
    public static final String PDF_BASE_URL = "https://docs.google.com/gview?embedded=true&url=http://ais.aravalieduworld.com/";
    public static final String ATTENDANCE_URL = BASE_URL + "APi/Customer/attendanceview?admnoattendance=";
   // http://ais.aravalieduworld.com/APi/Customer/attendanceview2?admnoattendance=20100076&mnt=
    public static final String TRANSPORT_URL = BASE_URL + "APi/Customer/transportview?routename=";
    public static final String DATESHEET_URL = BASE_URL + "APi/Customer/datesheetplannesy?cls=";
    public static final String TOTAL_EVENT_URL = BASE_URL +"APi/Customer/datesheetplannesys?cls=";
    public static final String DAY_EVENT_URL  = BASE_URL+"APi/Customer/Calenderapidate?school_id=";
    public static final String NOTIFICATION_URL = BASE_URL+"APi/Customer/Getnotification?schoolnameholid=";
    public static final String RESULT_URL      = BASE_URL+"APi/Customer/Getlistresult?admnorsult=";
    public static final String TOKEN_URL       = BASE_URL+"api/Customer/updatelogin?admno=";
    public static final String TOTAL_ATTANDANCE = BASE_URL+"APi/Customer/attendanc_Summary?admno=";
    public static final String SCHOOL_DETAILS_HEADER= BASE_URL+"APi/Customer/Getschoolinfo?schoolid=";
    //http://ais.aravalieduworld.com/api/Customer/updatelogin?admno=20180001&PUBLISH=1111110  push notification


    public static String getapidate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String apdate = df.format(c.getTime());
        return apdate;
    }

    public static int getMonth(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(calendar.MONTH)+1;
        return month;
    }

}
