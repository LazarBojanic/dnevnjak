package rs.raf.projekat1.lazar_bojanic_rn11621.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.List;

import rs.raf.projekat1.lazar_bojanic_rn11621.R;
import rs.raf.projekat1.lazar_bojanic_rn11621.activity.AddObligationActivity;
import rs.raf.projekat1.lazar_bojanic_rn11621.adapter.ObligationsRecyclerViewAdapter;
import rs.raf.projekat1.lazar_bojanic_rn11621.model.Obligation;
import rs.raf.projekat1.lazar_bojanic_rn11621.util.DatabaseHelper;
import rs.raf.projekat1.lazar_bojanic_rn11621.util.Util;
import rs.raf.projekat1.lazar_bojanic_rn11621.viewmodel.ObligationListViewModel;


public class ScheduleFragment extends Fragment {

    private RadioGroup radioGroupPriority;

    private RadioButton radioButtonLow;
    private RadioButton radioButtonMid;
    private RadioButton radioButtonHigh;
    private RadioButton radioButtonSelected;
    private FloatingActionButton floatingActionButtonAdd;
    private RecyclerView recyclerViewObligations;
    private ObligationsRecyclerViewAdapter obligationsRecyclerViewAdapter;


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

        initView();
        initListeners();
        initObservers();
        radioButtonLow.callOnClick();
    }
    private void initView(){
        radioGroupPriority = requireActivity().findViewById(R.id.radioGroupPriority);
        radioButtonLow = requireActivity().findViewById(R.id.radioButtonLow);
        radioButtonMid = requireActivity().findViewById(R.id.radioButtonMid);
        radioButtonHigh = requireActivity().findViewById(R.id.radioButtonHigh);
        //floatingActionButtonAdd = requireActivity().findViewById(R.id.floatingActionButtonAdd);
        floatingActionButtonAdd = requireActivity().findViewById(R.id.floatingActionButtonAdd);
        recyclerViewObligations = requireActivity().findViewById(R.id.recyclerViewObligations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewObligations.setLayoutManager(layoutManager);
        ObligationListViewModel obligationListViewModel = new ViewModelProvider(requireActivity()).get(ObligationListViewModel.class);
        obligationsRecyclerViewAdapter = new ObligationsRecyclerViewAdapter(requireActivity(), obligationListViewModel);
        recyclerViewObligations.setAdapter(obligationsRecyclerViewAdapter);

    }
    private void initObservers(){
        ObligationListViewModel obligationListViewModel = new ViewModelProvider(requireActivity()).get(ObligationListViewModel.class);
        obligationListViewModel.getObligationListMutableLiveDataExisting().observe(requireActivity(), new Observer<List<Obligation>>() {
            @Override
            public void onChanged(List<Obligation> obligationList) {
                if(obligationList != null){
                    obligationsRecyclerViewAdapter.setObligationList(obligationList);
                    obligationsRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

        obligationListViewModel.getCurrentDateMutableLiveData().observe(requireActivity(), new Observer<LocalDate>() {
            @Override
            public void onChanged(LocalDate currentDate) {
                if(currentDate != null){
                    ((TextView) requireActivity().findViewById(R.id.textViewToolbar)).setText(Util.localDateToString(currentDate));
                    obligationsRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

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
        floatingActionButtonAdd.setOnClickListener(view -> {
            ObligationListViewModel obligationListViewModel = new ViewModelProvider(requireActivity()).get(ObligationListViewModel.class);
            Intent intent = new Intent(requireActivity(), AddObligationActivity.class);
            intent.putExtra(requireActivity().getResources().getString(R.string.extraToolbarDate), Util.localDateToString(obligationListViewModel.getCurrentDateMutableLiveData().getValue()));
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