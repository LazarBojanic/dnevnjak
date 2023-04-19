package rs.raf.dnevnjak.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.model.ServiceUser;
import rs.raf.dnevnjak.util.DatabaseHelper;
import rs.raf.dnevnjak.util.Util;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSplashScreen();
        setContentView(R.layout.activity_splash_screen);
    }

    public void initSplashScreen(){
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            try {
                Thread.sleep(500);
                ServiceUser serviceUser = Util.getUserSharedPreference(this);
                if(serviceUser != null){
                    Log.i(String.valueOf(R.string.dnevnjakTag), serviceUser.toString());
                    DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
                    if(databaseHelper.loginUserWithSharedPreferences(this, serviceUser)){
                        Log.i(getResources().getString(R.string.dnevnjakTag), "Login Successful");
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Log.i(getResources().getString(R.string.dnevnjakTag), "Login Failed 1");
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, LoginAndRegisterActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    Log.i(getResources().getString(R.string.dnevnjakTag), "Login Failed 2");
                    Intent intent = new Intent(this, LoginAndRegisterActivity.class);
                    startActivity(intent);
                }
            }
            catch (InterruptedException e) {
                Log.i(getResources().getString(R.string.dnevnjakTag), "Login Failed 3");
                Intent intent = new Intent(this, LoginAndRegisterActivity.class);
                startActivity(intent);
            }
            return false;
        });
    }
}