package rs.raf.dnevnjak.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.app.DnevnjakApp;
import rs.raf.dnevnjak.model.ServiceUser;

public class Util {
    public static ServiceUser getUserSharedPreference(Context context) {
        try{
            SharedPreferences sharedPreferences;
            synchronized (DnevnjakApp.lock){
                sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.dnevnjakSharedPreferences), MODE_PRIVATE);
                if(sharedPreferences != null){
                    if(sharedPreferences.contains(context.getResources().getString(R.string.userSharedPreference))){
                        String serviceUserJson = sharedPreferences.getString(context.getResources().getString(R.string.userSharedPreference),  "USER_SP_UNAVAILABLE");
                        Log.i(context.getResources().getString(R.string.dnevnjakTag), serviceUserJson);
                        return JacksonSerializer.deserialize(serviceUserJson, ServiceUser.class);
                    }
                    else{
                        Log.i(context.getResources().getString(R.string.dnevnjakTag), "USER_SP Shared Preference doesn't exist");
                        return null;
                    }
                }
                else{
                    Log.i(context.getResources().getString(R.string.dnevnjakTag), "Shared Preferences null");
                    return null;
                }
            }
        }
        catch(JsonProcessingException e){
            Log.i(context.getResources().getString(R.string.dnevnjakTag), "Deserialization error");
            return null;
        }
    }
    public static boolean putUserSharedPreference(Context context, ServiceUser serviceUser) {
        try{
            SharedPreferences sharedPreferences;
            synchronized (DnevnjakApp.lock){
                sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.dnevnjakSharedPreferences), MODE_PRIVATE);
                if(sharedPreferences != null){
                    String serviceUserJson = JacksonSerializer.serialize(serviceUser);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(context.getResources().getString(R.string.userSharedPreference), serviceUserJson);
                    editor.apply();
                    return true;
                }
                else{
                    Log.i(context.getResources().getString(R.string.dnevnjakTag), "Shared Preferences null");
                    return false;
                }
            }
        }
        catch(JsonProcessingException e){
            Log.i(context.getResources().getString(R.string.dnevnjakTag), "Deserialization error");
            return false;
        }
    }
    public static boolean removeUserSharedPreference(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (DnevnjakApp.lock){
            sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.dnevnjakSharedPreferences), MODE_PRIVATE);
            if(sharedPreferences != null){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(context.getResources().getString(R.string.userSharedPreference));
                editor.apply();
                return true;
            }
            else{
                Log.i(context.getResources().getString(R.string.dnevnjakTag), "Shared Preferences null");
                return false;
            }
        }
    }
}
