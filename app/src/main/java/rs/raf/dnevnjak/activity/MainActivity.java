package rs.raf.dnevnjak.activity;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.adapter.CalendarRecyclerViewAdapter;
import rs.raf.dnevnjak.util.DatabaseHelper;
import rs.raf.dnevnjak.util.Util;
import rs.raf.dnevnjak.viewmodel.ObligationListViewModel;
import rs.raf.dnevnjak.viewpager.CustomPagerAdapter;
import rs.raf.dnevnjak.viewpager.ViewPagerFragments;

public class MainActivity extends AppCompatActivity  {

    private BottomNavigationView bottomNavigationView;
    private ViewPagerFragments viewPagerFragments;
    private Toolbar toolbarMain;
    private TextView textViewToolbar;
    private LocalDate date;
    private ObligationListViewModel obligationListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        obligationListViewModel = new ViewModelProvider(this).get(ObligationListViewModel.class);
        obligationListViewModel.getObligationListMutableLiveData(this, LocalDate.now());
        initListeners();
        textViewToolbar.setText(Util.localDateToString(LocalDate.now()));
        displayFragment(R.id.itemCalendar);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(getResources().getString(R.string.stateObligationList), (Serializable) obligationListViewModel.getObligationListMutableLiveDataExisting().getValue());
    }
    private void initView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPagerFragments = findViewById(R.id.viewPagerFragments);
        viewPagerFragments.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
        toolbarMain = findViewById(R.id.toolbarMain);
        textViewToolbar = findViewById(R.id.textViewToolbar);
        date = LocalDate.now();

    }
    private void initListeners(){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            displayFragment(item.getItemId());
            return true;
        });
    }
    private void initObservers(){

    }
    public void displayFragment(int itemId){
        Fragment fragment = null;

        int fragmentNumber = 0;
        switch (itemId) {
            case R.id.itemCalendar:
                fragmentNumber = CustomPagerAdapter.FRAGMENT_1;
                break;
            case R.id.itemSchedule:
                fragmentNumber = CustomPagerAdapter.FRAGMENT_2;
                break;
            case R.id.itemAccount:
                fragmentNumber = CustomPagerAdapter.FRAGMENT_3;
                break;

        }
        viewPagerFragments.setCurrentItem(fragmentNumber);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}