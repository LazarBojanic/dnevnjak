package rs.raf.dnevnjak.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.activity.AddObligationActivity;
import rs.raf.dnevnjak.adapter.ObligationsRecyclerViewAdapter;
import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.util.DatabaseHelper;
import rs.raf.dnevnjak.viewmodel.ObligationListViewModel;


public class ScheduleFragment extends Fragment {



    private RadioGroup radioGroupPriority;

    private RadioButton radioButtonLow;
    private RadioButton radioButtonMid;
    private RadioButton radioButtonHigh;
    private RadioButton radioButtonSelected;
    private FloatingActionButton floatingActionButtonAdd;
    private ImageButton imageButtonAdd;
    private RecyclerView recyclerViewObligations;

    private ObligationListViewModel obligationListViewModel;
    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obligationListViewModel = new ViewModelProvider(requireActivity()).get(ObligationListViewModel.class);
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

        ObligationsRecyclerViewAdapter obligationsRecyclerViewAdapter = new ObligationsRecyclerViewAdapter(obligationListViewModel, requireActivity());
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
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
}