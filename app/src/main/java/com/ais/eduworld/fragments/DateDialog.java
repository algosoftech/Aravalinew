package com.ais.eduworld.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import com.ais.eduworld.R;
import com.ais.eduworld.activities.AttachActivity;
import com.ais.eduworld.activities.AttendanceActivity;
import com.ais.eduworld.activities.HomeWorkActivity;
import com.ais.eduworld.adapters.AttendanceAdapter;


public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    public interface MyDateListner{
        void onFinishUserDialog(String mDate,String amonth);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK,this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        MyDateListner activity = (MyDateListner)getActivity();

        int nmonth = month+1;
        String stringOfDate = year + "-" + nmonth + "-" + day;
    //    HomeWorkActivity.calDate.setText(HomeWorkActivity.calDate.getText() + "\n\nFormatted date: " + stringOfDate);
    //    AttendanceActivity.mDate.setText(AttendanceActivity.mDate.getText()+ "\n\nFormatted date: "+stringOfDate);
        activity.onFinishUserDialog(stringOfDate,String.valueOf(nmonth));
    }
}
