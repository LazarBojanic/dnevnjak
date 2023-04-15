package rs.raf.dnevnjak.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import rs.raf.dnevnjak.activity.MainActivity;
import rs.raf.dnevnjak.activity.LoginAndRegisterActivity;
import rs.raf.dnevnjak.app.DnevnjakApp;

public class Util {
    public static void checkUserSharedPreference(Context context){
        SharedPreferences sharedPreferences = DnevnjakApp.sharedPreferences;
        if(sharedPreferences != null){
            if(sharedPreferences.contains("USER_SP")){
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
            else{
                Intent intent = new Intent(context, LoginAndRegisterActivity.class);
                context.startActivity(intent);
            }
        }
        else{
            Intent intent = new Intent(context, LoginAndRegisterActivity.class);
            context.startActivity(intent);
        }
    }

}
