package com.ais.eduworld.expand;


import java.util.Arrays;
import java.util.List;

import com.ais.eduworld.R;

public class GenreDataFactory {

  public static List<Genre> makeGenres() {
    return Arrays.asList(makeRockGenre(),
        makeJazzGenre(),
        makeClassicGenre(),
        makeSalsaGenre(),
        makeBluegrassGenre());
  }





  public static Genre makeRockGenre() {
    return new Genre("Student", makeRockArtists(), R.drawable.reading);
  }



  public static List<Artist> makeRockArtists() {
    Artist student = new Artist("Student Information", true,R.drawable.info);
    Artist card = new Artist("Identity Card", true,R.drawable.idcard);
  //  Artist reoSpeedwagon = new Artist("REO Speedwagon", false);
  //  Artist boston = new Artist("Boston", true);

    return Arrays.asList(student, card);
  }

  public static Genre makeJazzGenre() {
    return new Genre("Academics", makeJazzArtists(), R.drawable.book);
  }



  public static List<Artist> makeJazzArtists() {
    Artist milesDavis = new Artist("Home Work", true,R.drawable.dailyhomework);
    Artist ellaFitzgerald = new Artist("Assignments/WorkSheet", true,R.drawable.assign);
    Artist billieHoliday = new Artist("Datesheet/Syllabus/Planner", false,R.drawable.circular);
    Artist calender = new Artist("Calendar", false,R.drawable.calendar);
    Artist attendance = new Artist("Attendance", false,R.drawable.attendance);
    Artist holidays = new Artist("Holidays", false,R.drawable.attendance);

    return Arrays.asList(milesDavis, ellaFitzgerald, billieHoliday,calender,attendance,holidays);
  }

  public static Genre makeClassicGenre() {
    return new Genre("Notification Circular", makeClassicArtists(), R.drawable.notification2);
  }



  public static List<Artist> makeClassicArtists() {
    Artist beethoven = new Artist("Notification", false,R.drawable.notification);
    Artist bach = new Artist("Circulars", true,R.drawable.circular);
//    Artist brahms = new Artist("Johannes Brahms", false);
//    Artist puccini = new Artist("Giacomo Puccini", false);

    return Arrays.asList(beethoven, bach);
  }

  public static Genre makeSalsaGenre() {
    return new Genre("Other", makeSalsaArtists(), R.drawable.other);
  }


  public static List<Artist> makeSalsaArtists() {
    Artist hectorLavoe = new Artist("School Directory", true,R.drawable.direc);
    Artist celiaCruz = new Artist("School Fee", false,R.drawable.fee);
  //  Artist willieColon = new Artist("Result", false,R.drawable.result);
//    Artist marcAnthony = new Artist("Marc Anthony", false);

    return Arrays.asList(hectorLavoe, celiaCruz);
  }

  public static Genre makeBluegrassGenre() {
    return new Genre("Social Media", makeBluegrassArtists(), R.drawable.social_media);
  }



  public static List<Artist> makeBluegrassArtists() {
    Artist billMonroe = new Artist("Facebook",true,R.drawable.facebook2);
    Artist earlScruggs = new Artist("YouTube", false,R.drawable.youtube);
//    Artist osborneBrothers = new Artist("Osborne Brothers", true);
//    Artist johnHartford = new Artist("John Hartford", false);

    return Arrays.asList(billMonroe, earlScruggs);
  }

}

