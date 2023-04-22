package rs.raf.projekat1.lazar_bojanic_rn11621.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.time.LocalDate;
import java.util.List;

import rs.raf.projekat1.lazar_bojanic_rn11621.R;
import rs.raf.projekat1.lazar_bojanic_rn11621.util.DatabaseHelper;
import rs.raf.projekat1.lazar_bojanic_rn11621.util.Util;


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
