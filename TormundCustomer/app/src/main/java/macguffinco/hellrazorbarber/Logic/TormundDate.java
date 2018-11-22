package macguffinco.hellrazorbarber.Logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class TormundDate {

    private static Calendar calendar=Calendar.getInstance();

    public static   Date Today(){

        return  calendar.getTime();
    }

    public static  int Year(){

        return  calendar.get(Calendar.YEAR);
    }

    public static  int Month(){

        return  calendar.get(Calendar.MONTH);
    }

    public static  int Day(){

        return  calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static  int Week_of_Year(){

        return  calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static  String hour_24(){

        return  new SimpleDateFormat("HH:mm aa").format(calendar.getTime());
    }
    public static  String hour_12(){


        try {

            return  new SimpleDateFormat("hh:mm aa").format(calendar.getTime());

        }catch (Exception e){
            return  "";
        }


    }

    public static  String TodayDDMMMYYY(){


        try {
            String day=new SimpleDateFormat("dd").format(calendar.getTime());
            String month= new SimpleDateFormat("MMM").format(calendar.getTime()).substring(0,1).toUpperCase()
                    +new SimpleDateFormat("MMM").format(calendar.getTime()).substring(1);

            String year=  new SimpleDateFormat("YYYY").format(calendar.getTime());

            return day+" "+month+" "+year;

        }catch (Exception e){
            return  "";
        }


    }

    public static  int day_of_week(){

        return  calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static  String dayName_of_week() {

        try {
            String day=new SimpleDateFormat("EEEE").format(calendar.getTime());
            return new String(day.substring(0,1).toUpperCase()+day.substring(1));

        }catch (Exception e){
            return"";
        }

    }

}
