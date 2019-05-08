package com.ais.eduworld.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;




import com.ais.eduworld.R;

//public class CurrentDateDecorator implements DayViewDecorator {
//    private Context context;
//    private Drawable highlightDrawable;
//
//    public CurrentDateDecorator(Context context){
//        this.context = context;
//        highlightDrawable = this.context.getResources().getDrawable(R.drawable.mcalbg);
//    }
//
//    @Override
//    public boolean shouldDecorate(CalendarDay calendarDay) {
//        return calendarDay.equals(CalendarDay.today());
//    }
//
//    @Override
//    public void decorate(DayViewFacade view) {
//        view.setBackgroundDrawable(highlightDrawable);
//        view.addSpan(new ForegroundColorSpan(Color.YELLOW));
//        view.addSpan(new StyleSpan(Typeface.BOLD));
//        view.addSpan(new RelativeSizeSpan(1.5f));
//    }
//}
