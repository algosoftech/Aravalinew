package com.ais.eduworld.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;

import com.ais.eduworld.R;


public class PrefConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file),Context.MODE_PRIVATE);
    }

        public void setParentId(String pid){
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putString(context.getString(R.string.parentId),pid);
       editor.commit();
    }

   public String getParentId(){
      return sharedPreferences.getString(context.getString(R.string.parentId),"");
   }

    public void setFatherMobile(String mobile){
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putString(context.getString(R.string.profile_father_mob),mobile);
       editor.commit();
    }

   public String getFatherMobile(){
      return sharedPreferences.getString(context.getString(R.string.profile_father_mob),"");
   }

    public void setAdminNo(String adminNo){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_admin),adminNo);
        editor.commit();
    }

    public String getAdminNo(){
        return sharedPreferences.getString(context.getString(R.string.profile_admin),"");
    }

    public void setSchoolName(String sName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_school_name),sName);
        editor.commit();
    }

    public String getSchoolName(){
        return sharedPreferences.getString(context.getString(R.string.profile_school_name),"");
    }

    public void setSchoolId(String id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_school_id),id);
        editor.commit();
    }

    public String getSchoolId(){
        return sharedPreferences.getString(context.getString(R.string.profile_school_id),"");
    }

    public void setStudentName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_student_name),name);
        editor.commit();
    }

    public String getStudentName(){
        return sharedPreferences.getString(context.getString(R.string.profile_student_name),"");
    }

    public void setStudentClass(String sclass){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_student_class),sclass);
        editor.commit();
    }

    public String getStudentClass(){
        return sharedPreferences.getString(context.getString(R.string.profile_student_class),"");
    }

    public void setStudentSection(String section){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_section),section);
        editor.commit();
    }

    public String getStudentSection(){
        return sharedPreferences.getString(context.getString(R.string.profile_section),"");
    }

    public void setStudentRollNo(String rollNo){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_roll_no),rollNo);
        editor.commit();
    }

    public String getRollNo(){
        return sharedPreferences.getString(context.getString(R.string.profile_roll_no),"");
    }

    public void setStudentDob(String dob){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_dob),dob);
        editor.commit();
    }

    public String getStudentDob(){
        return sharedPreferences.getString(context.getString(R.string.profile_dob),"");
    }


    public void setFatherName(String fatherName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_father_name),fatherName);
        editor.commit();
    }

    public String getFatherName(){
        return sharedPreferences.getString(context.getString(R.string.profile_father_name),"");
    }

    public void setFatherEmail(String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_father_emil),email);
        editor.commit();
    }

    public String getFatherEmail(){
        return sharedPreferences.getString(context.getString(R.string.profile_father_emil),"");
    }

    public void setMotherName(String motherName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_mother_name),motherName);
        editor.commit();
    }

    public String getMotherName(){
        return sharedPreferences.getString(context.getString(R.string.profile_mother_name),"");
    }

    public void setStudentPic(String image){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_student_pic),image);
        editor.commit();
    }

    public String getStudentPic(){
        return sharedPreferences.getString(context.getString(R.string.profile_student_pic),"");
    }

    public void displayToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public void setLoggedIn(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.login_status),status);
        editor.commit();
    }

    public boolean getLoggedIn(){
        return sharedPreferences.getBoolean(context.getString(R.string.login_status),false);
    }

    public void setBusRout(String rout){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_student_busRout),rout);
        editor.commit();
    }

    public String getBusRout(){
        return sharedPreferences.getString(context.getString(R.string.profile_student_busRout),"");
    }


    public void setAddress(String address){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.profile_student_Address),address);
        editor.commit();
    }

    public String getAddress(){
        return sharedPreferences.getString(context.getString(R.string.profile_student_Address),"");
    }

    public void setLatitude(long latitude){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(context.getString(R.string.latitude),latitude);
        editor.commit();
    }

    public long getLatitude(){
        return sharedPreferences.getLong(context.getString(R.string.latitude),0L);
    }

    public void setLongitude(long longitude){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(context.getString(R.string.longitude),longitude);
        editor.commit();
    }

    public long getLongitude(){
        return sharedPreferences.getLong(context.getString(R.string.longitude),0L);
    }

}
