package rs.raf.dnevnjak.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalDate;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.app.DnevnjakApp;
import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.model.ServiceUser;
import rs.raf.dnevnjak.serialize.GsonSerializer;

public class Util {
    public static ServiceUser getUserSharedPreference(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (DnevnjakApp.lock){
            sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.dnevnjakSharedPreferences), MODE_PRIVATE);
            if(sharedPreferences != null){
                if(sharedPreferences.contains(context.getResources().getString(R.string.userSharedPreference))){
                    String serviceUserJson = sharedPreferences.getString(context.getResources().getString(R.string.userSharedPreference),  "USER_SP_UNAVAILABLE");
                    Log.i(context.getResources().getString(R.string.dnevnjakTag), serviceUserJson);
                    return GsonSerializer.deserialize(serviceUserJson, ServiceUser.class);
                }
                else{
                    Log.i(context.getResources().getString(R.string.dnevnjakTag), "USER_SP Shared Preference doesn't exist");
                    return null;
                }
            }
            else{
                Log.i(context.getResources().getString(R.string.dnevnjakTag), "Shared Preferences null");
                return null;
            }
        }
    }
    public static boolean putUserSharedPreference(Context context, ServiceUser serviceUser) {
        SharedPreferences sharedPreferences;
        synchronized (DnevnjakApp.lock){
            sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.dnevnjakSharedPreferences), MODE_PRIVATE);
            if(sharedPreferences != null){
                String serviceUserJson = GsonSerializer.serialize(serviceUser);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(context.getResources().getString(R.string.userSharedPreference), serviceUserJson);
                editor.apply();
                return true;
            }
            else{
                Log.i(context.getResources().getString(R.string.dnevnjakTag), "Shared Preferences null");
                return false;
            }
        }
    }
    public static boolean removeUserSharedPreference(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (DnevnjakApp.lock){
            sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.dnevnjakSharedPreferences), MODE_PRIVATE);
            if(sharedPreferences != null){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(context.getResources().getString(R.string.userSharedPreference));
                editor.apply();
                return true;
            }
            else{
                Log.i(context.getResources().getString(R.string.dnevnjakTag), "Shared Preferences null");
                return false;
            }
        }
    }
    public static List<LocalDate> generateCalendarPage(LocalDate currentDate){
        int numberOfDays = 1;
        LocalDate iterationDate = currentDate;
        boolean dayIsMondayAndMonthIsDifferentThanCurrentMonth;
        do{
            numberOfDays++;
            iterationDate = iterationDate.minusDays(1);

            dayIsMondayAndMonthIsDifferentThanCurrentMonth = iterationDate.getDayOfWeek().equals(DayOfWeek.MONDAY) && !iterationDate.getMonth().equals(currentDate.getMonth());
        }
        while(!dayIsMondayAndMonthIsDifferentThanCurrentMonth);
        int daysLeft = currentDate.getMonth().length(currentDate.isLeapYear()) - currentDate.getDayOfMonth();
        numberOfDays += daysLeft;
        List<LocalDate> dateList = new ArrayList<>();
        for(int i = 0; i < numberOfDays; i++){
            dateList.add(iterationDate.plusDays(i));
        }
        return dateList;
    }
    public static String dateListToString(List<LocalDate> dateList){
        StringBuilder dateListString = new StringBuilder();
        for(int i = 0; i < dateList.size(); i++){
            dateListString.append(dateList.get(i).toString());
        }
        return dateListString.toString();
    }
    public static void printDateList(List<LocalDate> dateList, Context context){
        for(int i = 0; i < dateList.size(); i++){
            Log.i(context.getResources().getString(R.string.dnevnjakTag), dateList.get(i).toString());
        }
    }

    public static LocalDate dateStringToLocalDate(String dateString){
        try{
            return LocalDate.parse(dateString);
        }
        catch(IllegalArgumentException e){
            return null;
        }
    }
    public static String localDateToString(LocalDate date){
        try{
            return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        catch(DateTimeParseException e){
            return null;
        }
    }
    public static LocalTime timeStringToLocalTime(String timeString){
        try{
            return LocalTime.parse(timeString);
        }
        catch(DateTimeParseException e){
            return null;
        }
    }
    public static String localTimeToString(LocalTime time){
        try{
            return time.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
        catch(DateTimeParseException e){
            return null;
        }
    }
    public static boolean timeIntervalsAreValid(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        // Check if the intervals are valid
        if (start1.isAfter(end1)) {
            return false;
        }
        // Check if the intervals overlap
        return !(start1.isBefore(end2) && end1.isAfter(start2));
    }
    public static boolean timeIntervalStringIsValid(String startString, String endString){
        if(startString.equals("") && endString.equals("")){
            return true;
        }
        else if(startString.equals("") && !endString.equals("")){
            return false;
        }
        else if(!startString.equals("") && endString.equals("")){
            return false;
        }

        LocalTime start = Util.timeStringToLocalTime(startString);
        LocalTime end = Util.timeStringToLocalTime(endString);
        if(start != null && end != null){
            return !start.isAfter(end);
        }
        else{
            return false;
        }
    }
    public static boolean verifyDateAndTime(String existingDateString, LocalDate date, String existingStartTimeString, String existingEndTimeString, String startTimeString, String endTimeString){
        if(startTimeString.equals("") && endTimeString.equals("")){
            return true;
        }
        else if(startTimeString.equals("") && !endTimeString.equals("")){
            return false;
        }
        else if(!startTimeString.equals("") && endTimeString.equals("")){
            return false;
        }

        LocalDate existingDate = Util.dateStringToLocalDate(existingDateString);
        if(existingDate != null && date != null){
            if(existingDate.equals(date)){
                LocalTime existingStartTime = Util.timeStringToLocalTime(existingStartTimeString);
                LocalTime existingEndTime = Util.timeStringToLocalTime(existingStartTimeString);
                LocalTime startTime = Util.timeStringToLocalTime(startTimeString);
                LocalTime endTime = Util.timeStringToLocalTime(endTimeString);
                if(existingStartTime != null && existingEndTime != null && startTime != null && endTime != null){
                    return timeIntervalsAreValid(existingStartTime, existingEndTime, startTime, endTime);
                }
                else{
                    return false;
                }
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }

}
