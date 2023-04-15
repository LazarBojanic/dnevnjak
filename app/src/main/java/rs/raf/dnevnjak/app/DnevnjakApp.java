package rs.raf.dnevnjak.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import rs.raf.dnevnjak.util.DatabaseHelper;


public class DnevnjakApp extends Application {
    public static final String DNEVNJAK_TAG = "dnevnjak_tag";
    public static SharedPreferences sharedPreferences;
    @Override
    public void onCreate(){
        super.onCreate();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();
        sharedPreferences = getSharedPreferences("DNEVNJAK_SHARED_PREFERENCES", MODE_PRIVATE);

    }
}
