package rs.raf.projekat1.lazar_bojanic_rn11621.activity;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import rs.raf.projekat1.lazar_bojanic_rn11621.R;
import rs.raf.projekat1.lazar_bojanic_rn11621.adapter.CalendarRecyclerViewAdapter;
import rs.raf.projekat1.lazar_bojanic_rn11621.util.DatabaseHelper;
import rs.raf.projekat1.lazar_bojanic_rn11621.util.Util;
import rs.raf.projekat1.lazar_bojanic_rn11621.viewmodel.ObligationListViewModel;
import rs.raf.projekat1.lazar_bojanic_rn11621.viewpager.CustomPagerAdapter;
import rs.raf.projekat1.lazar_bojanic_rn11621.viewpager.ViewPagerFragments;

public class MainActivity extends AppCompatActivity  {

    private BottomNavigationView bottomNavigationView;
    private ViewPagerFragments viewPagerFragments;
    private Toolbar toolbarMain;
    private TextView textViewToolbar;
    private LocalDate date;
    private ObligationListViewModel obligationListViewModel;

    Menu menuNavBar;
    MenuItem calendarItem;
    MenuItem scheduleItem;
    MenuItem accountItem;

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

        menuNavBar = bottomNavigationView.getMenu();
        calendarItem = menuNavBar.findItem(R.id.itemCalendar);
        scheduleItem = menuNavBar.findItem(R.id.itemSchedule);
        accountItem = menuNavBar.findItem(R.id.itemAccount);

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

        int itemCalendarId = calendarItem.getItemId();
        int itemScheduleId = scheduleItem.getItemId();
        int itemAccountId = accountItem.getItemId();

        if(itemId == itemCalendarId){
            fragmentNumber = CustomPagerAdapter.FRAGMENT_1;
        }
        else if(itemId == itemScheduleId){
            fragmentNumber = CustomPagerAdapter.FRAGMENT_2;
        }
        else if(itemId == itemAccountId){
            fragmentNumber = CustomPagerAdapter.FRAGMENT_3;
        }

        viewPagerFragments.setCurrentItem(fragmentNumber);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}