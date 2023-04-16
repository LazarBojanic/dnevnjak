package rs.raf.dnevnjak.activity;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.app.DnevnjakApp;
import rs.raf.dnevnjak.fragment.AccountFragment;
import rs.raf.dnevnjak.fragment.CalendarFragment;
import rs.raf.dnevnjak.fragment.ScheduleFragment;
import rs.raf.dnevnjak.model.ServiceUser;
import rs.raf.dnevnjak.util.JacksonSerializer;
import rs.raf.dnevnjak.viewmodel.SplashViewModel;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayoutFragments;

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
    }
    private void initListeners(){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.i(getResources().getString(R.string.dnevnjakTag), "Switching to" + item.getItemId());
            displayFragment(item.getItemId());
            return true;
        });
    }
    public void displayFragment(int itemId){
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (itemId) {
            case R.id.itemCalendar:
                fragment = new CalendarFragment();
                title  = "Calendar";
                break;
            case R.id.itemSchedule:
                fragment = new ScheduleFragment();
                title = "Schedule";
                break;
            case R.id.itemAccount:
                fragment = new AccountFragment();
                title = "Account";
                break;

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(frameLayoutFragments.getId(), fragment);
            ft.commit();
            Log.i(getResources().getString(R.string.dnevnjakTag), title);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            Log.i(getResources().getString(R.string.dnevnjakTag), title);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}