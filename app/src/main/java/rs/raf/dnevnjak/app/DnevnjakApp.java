package rs.raf.dnevnjak.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.time.LocalDate;
import java.util.List;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.util.DatabaseHelper;
import rs.raf.dnevnjak.util.Util;


public class DnevnjakApp extends Application {

    public static final Object lock = new Object();
    @Override
    public void onCreate(){
        super.onCreate();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();
        synchronized (lock){
            getSharedPreferences(getResources().getString(R.string.dnevnjakSharedPreferences), MODE_PRIVATE);
        }

    }
}
