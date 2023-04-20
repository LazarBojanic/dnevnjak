package rs.raf.dnevnjak.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.adapter.CalendarRecyclerViewAdapter;
import rs.raf.dnevnjak.gesture.CustomGestureListener;
import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.util.DatabaseHelper;
import rs.raf.dnevnjak.util.Util;
import rs.raf.dnevnjak.viewmodel.ObligationListViewModel;
import rs.raf.dnevnjak.viewmodel.ObligationViewModel;
import rs.raf.dnevnjak.viewpager.CustomPagerAdapter;


public class CalendarFragment extends Fragment implements CalendarRecyclerViewAdapter.OnCalendarDayClickListener {

    private CalendarRecyclerViewAdapter calendarRecyclerViewAdapter;
    private RecyclerView recyclerViewCalendar;
    private CustomGestureListener gestureListener;
    private GestureDetector gestureDetector;
    public CalendarFragment() {

    }

    @Override
    public void onCalendarDayClicked(LocalDate date) {
        ObligationListViewModel obligationListViewModel = new ViewModelProvider(requireActivity()).get(ObligationListViewModel.class);
        obligationListViewModel.getObligationListMutableLiveDataExisting().postValue(DatabaseHelper.getInstance(requireActivity()).getAllObligationsByDate(requireActivity(), date));
        obligationListViewModel.getCurrentDateMutableLiveData().postValue(date);
        ((BottomNavigationView) requireActivity().findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.itemSchedule);

    }

    public void initListeners(){
        recyclerViewCalendar.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return super.onInterceptTouchEvent(recyclerView, motionEvent);
            }
        });
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ObligationListViewModel obligationListViewModel = new ViewModelProvider(requireActivity()).get(ObligationListViewModel.class);

        getExtraObligationList(savedInstanceState);

        List<Obligation> obligationList = obligationListViewModel.getObligationListMutableLiveDataExisting().getValue();
        if(obligationList != null){
            Log.i("DNEVNJAK", "CALENDAR FRAGMENT ON VIEW CREATED - " + obligationList.toString());
        }else{
            Log.i("DNEVNJAK", "CALENDAR FRAGMENT ON VIEW CREATED - obligationList is null");
        }
        initView();
        initObservers();
        initListeners();
    }
    public void initView(){
        ObligationListViewModel obligationListViewModel = new ViewModelProvider(requireActivity()).get(ObligationListViewModel.class);
        recyclerViewCalendar = requireActivity().findViewById(R.id.recyclerViewCalendar);
        recyclerViewCalendar.setLayoutManager(new GridLayoutManager(requireActivity(), 7));
        calendarRecyclerViewAdapter = new CalendarRecyclerViewAdapter(requireActivity(), obligationListViewModel);
        calendarRecyclerViewAdapter.setOnCalendarDayClickListener(this);
        recyclerViewCalendar.setAdapter(calendarRecyclerViewAdapter);

        gestureListener = new CustomGestureListener(calendarRecyclerViewAdapter.getDateList(), calendarRecyclerViewAdapter);
        gestureDetector = new GestureDetector(requireActivity(), gestureListener);

    }

    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }


    public void getExtraObligationList(Bundle savedInstanceState){
        ObligationListViewModel obligationListViewModel = new ViewModelProvider(requireActivity()).get(ObligationListViewModel.class);
        if(savedInstanceState != null){
            obligationListViewModel.getObligationListMutableLiveDataExisting().postValue((List<Obligation>) savedInstanceState.getSerializable(getResources().getString(R.string.stateObligationList)));
        }
    }


    private void initObservers(){
        ObligationListViewModel obligationListViewModel = new ViewModelProvider(requireActivity()).get(ObligationListViewModel.class);
        obligationListViewModel.getObligationListMutableLiveDataExisting().observe(requireActivity(), obligationList -> {
            if(obligationList != null){

            }
        });
    }
}