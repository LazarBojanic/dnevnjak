package rs.raf.dnevnjak.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.fasterxml.jackson.core.JsonProcessingException;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.model.ServiceUser;
import rs.raf.dnevnjak.util.JacksonSerializer;
import rs.raf.dnevnjak.viewmodel.SplashViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListeners();
    }

    private void initView(){

    }
    private void initListeners(){

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}