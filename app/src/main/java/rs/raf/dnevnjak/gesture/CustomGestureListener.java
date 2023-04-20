package rs.raf.dnevnjak.gesture;

import android.view.GestureDetector;
import android.view.MotionEvent;

import java.time.LocalDate;
import java.util.List;

import rs.raf.dnevnjak.adapter.CalendarRecyclerViewAdapter;
import rs.raf.dnevnjak.util.Util;

public class CustomGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private List<LocalDate> dateList;
    private CalendarRecyclerViewAdapter calendarRecyclerViewAdapter;

    public CustomGestureListener(List<LocalDate> dateList, CalendarRecyclerViewAdapter calendarRecyclerViewAdapter){
        this.dateList = dateList;
        this.calendarRecyclerViewAdapter = calendarRecyclerViewAdapter;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY < 0) {
                    onSwipeUp();
                } else {
                    onSwipeDown();
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void onSwipeUp() {
        LocalDate nextPageDate = calendarRecyclerViewAdapter.getDateList().get(calendarRecyclerViewAdapter.getDateList().size() - 1).plusDays(1);
        List<LocalDate> nextCalendarPageDateList = Util.getNextCalendarPage(nextPageDate);
        calendarRecyclerViewAdapter.setDateList(nextCalendarPageDateList);
        calendarRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void onSwipeDown() {
        LocalDate previousPageDate = calendarRecyclerViewAdapter.getDateList().get(0).minusDays(1);
        List<LocalDate> previousCalendarPageDateList = Util.getNextCalendarPage(previousPageDate);
        calendarRecyclerViewAdapter.setDateList(previousCalendarPageDateList);
        calendarRecyclerViewAdapter.notifyDataSetChanged();
    }
}
