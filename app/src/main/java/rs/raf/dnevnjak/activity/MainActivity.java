package rs.raf.dnevnjak.activity;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.List;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.fragment.AccountFragment;
import rs.raf.dnevnjak.fragment.CalendarFragment;
import rs.raf.dnevnjak.fragment.ScheduleFragment;
import rs.raf.dnevnjak.util.Util;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayoutFragments;
    private Toolbar toolbarMain;
    private TextView textViewToolbar;
    private LocalDate date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListeners();
        displayFragment(R.id.itemCalendar);
    }

    private void initView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayoutFragments = findViewById(R.id.frameLayoutFragments);
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
    public void displayFragment(int itemId){
        Fragment fragment = null;
        String dateString = date.getMonth().name() + ", " + date.getDayOfMonth() + ". " + date.getYear() + ".";
        switch (itemId) {
            case R.id.itemCalendar:
                fragment = new CalendarFragment();
                List<LocalDate> dateList = Util.generateCalendarPage(LocalDate.now());
                Util.printDateList(dateList, this);
                break;
            case R.id.itemSchedule:
                fragment = new ScheduleFragment();
                textViewToolbar.setText(dateString);
                break;
            case R.id.itemAccount:
                fragment = new AccountFragment();
                textViewToolbar.setText(dateString);
                break;

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(frameLayoutFragments.getId(), fragment);
            ft.commit();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}