package rs.raf.dnevnjak.activity;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.List;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.fragment.AccountFragment;
import rs.raf.dnevnjak.fragment.CalendarFragment;
import rs.raf.dnevnjak.fragment.ScheduleFragment;
import rs.raf.dnevnjak.util.Util;
import rs.raf.dnevnjak.viewpager.CustomPagerAdapter;
import rs.raf.dnevnjak.viewpager.ViewPagerFragments;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPagerFragments viewPagerFragments;
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
    public void displayFragment(int itemId){
        Fragment fragment = null;
        String dateString = date.getMonth().name() + ", " + date.getDayOfMonth() + ". " + date.getYear() + ".";
        int fragmentNumber = 0;
        switch (itemId) {
            case R.id.itemCalendar:
                fragmentNumber = CustomPagerAdapter.FRAGMENT_1;
                List<LocalDate> dateList = Util.generateCalendarPage(LocalDate.now());
                Util.printDateList(dateList, this);
                break;
            case R.id.itemSchedule:
                fragmentNumber = CustomPagerAdapter.FRAGMENT_2;
                textViewToolbar.setText(dateString);
                break;
            case R.id.itemAccount:
                fragmentNumber = CustomPagerAdapter.FRAGMENT_3;
                textViewToolbar.setText(dateString);
                break;

        }
//        if (fragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(viewPagerFragments.getId(), fragment);
//            ft.commit();
//        }
        viewPagerFragments.setCurrentItem(fragmentNumber);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}