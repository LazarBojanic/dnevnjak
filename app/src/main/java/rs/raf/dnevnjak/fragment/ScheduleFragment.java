package rs.raf.dnevnjak.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.activity.AddObligationActivity;
import rs.raf.dnevnjak.adapter.CalendarRecyclerViewAdapter;
import rs.raf.dnevnjak.adapter.ObligationsRecyclerViewAdapter;
import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.util.DatabaseHelper;
import rs.raf.dnevnjak.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RadioGroup radioGroupPriority;

    private RadioButton radioButtonLow;
    private RadioButton radioButtonMid;
    private RadioButton radioButtonHigh;
    private RadioButton radioButtonSelected;
    private FloatingActionButton floatingActionButtonAdd;
    private ImageButton imageButtonAdd;
    private RecyclerView recyclerViewObligations;
    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListeners();
        radioButtonLow.callOnClick();
    }
    private void initView(){
        radioGroupPriority = requireActivity().findViewById(R.id.radioGroupPriority);
        radioButtonLow = requireActivity().findViewById(R.id.radioButtonLow);
        radioButtonMid = requireActivity().findViewById(R.id.radioButtonMid);
        radioButtonHigh = requireActivity().findViewById(R.id.radioButtonHigh);
        //floatingActionButtonAdd = requireActivity().findViewById(R.id.floatingActionButtonAdd);
        imageButtonAdd = requireActivity().findViewById(R.id.imageButtonAdd);
        recyclerViewObligations = requireActivity().findViewById(R.id.recyclerViewObligations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewObligations.setLayoutManager(layoutManager);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(requireActivity());
        ObligationsRecyclerViewAdapter obligationsRecyclerViewAdapter = new ObligationsRecyclerViewAdapter(databaseHelper.getAllObligations(requireActivity()));
        recyclerViewObligations.setAdapter(obligationsRecyclerViewAdapter);
    }
    private void initListeners(){
        radioButtonLow.setOnClickListener(view -> {
            selectButton(radioButtonLow);
        });
        radioButtonMid.setOnClickListener(view -> {
            selectButton(radioButtonMid);
        });
        radioButtonHigh.setOnClickListener(view -> {
            selectButton(radioButtonHigh);
        });
        /*floatingActionButtonAdd.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), AddObligationActivity.class);
            startActivity(intent);
        });*/
        imageButtonAdd.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), AddObligationActivity.class);
            startActivity(intent);
        });
    }

    private void selectButton(RadioButton radioButton){
        if (radioButtonSelected != null) {
            // Deselect the previous button
            radioButtonSelected.setElevation(0);
        }
        // Select the new button
        radioButton.setElevation(50);
        radioButtonSelected = radioButton;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
}