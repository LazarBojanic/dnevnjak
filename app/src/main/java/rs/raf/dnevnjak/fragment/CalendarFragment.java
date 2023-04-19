package rs.raf.dnevnjak.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.util.List;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.adapter.CalendarRecyclerViewAdapter;
import rs.raf.dnevnjak.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment implements CalendarRecyclerViewAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CalendarRecyclerViewAdapter calendarRecyclerViewAdapter;
    private RecyclerView recyclerViewCalendar;

    public CalendarFragment() {
        // Required empty public constructor
    }
    public void initCalendarDays(){
        
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        List<LocalDate> dateList = Util.generateCalendarPage(LocalDate.now());

        int numberOfColumns = 7;
        recyclerViewCalendar.setLayoutManager(new GridLayoutManager(requireContext(), numberOfColumns));
        calendarRecyclerViewAdapter = new CalendarRecyclerViewAdapter(requireContext(), dateList);
        calendarRecyclerViewAdapter.setClickListener(this);
        recyclerViewCalendar.setAdapter(calendarRecyclerViewAdapter);
    }
    public void initView(){
        recyclerViewCalendar = requireActivity().findViewById(R.id.recyclerViewCalendar);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}