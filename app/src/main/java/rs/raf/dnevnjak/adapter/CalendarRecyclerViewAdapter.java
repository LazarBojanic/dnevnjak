package rs.raf.dnevnjak.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.activity.AddObligationActivity;
import rs.raf.dnevnjak.activity.ObligationDetailsActivity;
import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.util.Util;
import rs.raf.dnevnjak.viewmodel.ObligationListViewModel;

public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.CalendarDayViewHolder> {
    private List<LocalDate> dateList;
    private List<Obligation> obligationList;
    private LayoutInflater layoutInflater;
    public CalendarRecyclerViewAdapter() {

    }
    private OnCalendarDayClickListener onCalendarDayClickListener;

    public interface OnCalendarDayClickListener {
        void onCalendarDayClicked(LocalDate date);
    }

    public void setOnCalendarDayClickListener(OnCalendarDayClickListener onCalendarDayClickListener) {
        this.onCalendarDayClickListener = onCalendarDayClickListener;
    }
    @Override
    public void onBindViewHolder(@NonNull CalendarRecyclerViewAdapter.CalendarDayViewHolder holder, int position) {
        holder.date = dateList.get(position);
        holder.textViewCalendarDay.setText(String.valueOf(holder.date.getDayOfMonth()));
        matchDatesWithObligations(holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCalendarDayClickListener != null) {
                    onCalendarDayClickListener.onCalendarDayClicked(holder.date);
                }
            }
        });
    }
    @NonNull
    @Override
    public CalendarRecyclerViewAdapter.CalendarDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()) .inflate(R.layout.layout_calendar_day, parent, false);
        return new CalendarRecyclerViewAdapter.CalendarDayViewHolder(view);
    }
    public List<LocalDate> getDateList() {
        return dateList;
    }
    public void setDateList(List<LocalDate> dateList) {
        this.dateList = dateList;
        notifyDataSetChanged();
    }
    public CalendarRecyclerViewAdapter(Context context, ObligationListViewModel obligationListViewModel) {
        this.layoutInflater = LayoutInflater.from(context);
        this.obligationList = obligationListViewModel.getObligationListMutableLiveDataExisting().getValue();
        this.dateList = Util.generateCalendarPage(LocalDate.now());
    }
    public void matchDatesWithObligations(@NonNull CalendarRecyclerViewAdapter.CalendarDayViewHolder holder){
        Log.i("DNEVNJAK", "obl size" + obligationList.size());
        Log.i("DNEVNJAK", obligationList.toString());
        for(int i = 0; i < obligationList.size(); i++){
            if(obligationList.get(i).getDate().equals(holder.date)){
                Log.i("DNEVNJAK", obligationList.get(i).getTitle() + "matched on date "+ holder.date);
                String priority = obligationList.get(i).getPriority();
                switch (priority){
                    case "low":
                        holder.frameLayoutCalendarDay.setBackgroundColor(Color.GREEN);
                        break;
                    case "mid":
                        holder.frameLayoutCalendarDay.setBackgroundColor(Color.YELLOW);
                        break;
                    case "high":
                        holder.frameLayoutCalendarDay.setBackgroundColor(Color.RED);
                        break;
                }
            }
        }

    }
    @Override
    public int getItemCount() {
        return dateList.size();
    }


    public class CalendarDayViewHolder extends RecyclerView.ViewHolder {
        private LocalDate date;
        private TextView textViewCalendarDay;
        private FrameLayout frameLayoutCalendarDay;

        public CalendarDayViewHolder(View itemView) {
            super(itemView);
            initView();
            initListeners();
        }
        private void initView(){
            textViewCalendarDay = itemView.findViewById(R.id.textViewCalendarDay);
            frameLayoutCalendarDay = itemView.findViewById(R.id.frameLayoutCalendarDay);
        }
        private void initListeners(){


        }
    }
}
