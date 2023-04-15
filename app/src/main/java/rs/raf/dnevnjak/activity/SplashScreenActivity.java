package rs.raf.dnevnjak.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.app.DnevnjakApp;
import rs.raf.dnevnjak.util.Util;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSplashScreen();
        setContentView(R.layout.activity_splash_screen);
    }

    public void checkUserSharedPreference(){
        SharedPreferences sharedPreferences = DnevnjakApp.sharedPreferences;
        if(sharedPreferences != null){
            if(sharedPreferences.contains("USER_SP")){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, LoginAndRegisterActivity.class);
                startActivity(intent);
            }
        }
        else{
            Intent intent = new Intent(this, LoginAndRegisterActivity.class);
            startActivity(intent);
        }
    }
    public void initSplashScreen(){
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            try {
                Thread.sleep(1000);
                Util.checkUserSharedPreference(this);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;
        });
    }
}