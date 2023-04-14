package rs.raf.dnevnjak.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import rs.raf.dnevnjak.model.ServiceUser;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, "dnevnjak.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createDatabaseQuery = """
                    CREATE TABLE "service_users" (
                    	"id" INTEGER PRIMARY KEY,
                    	"email" TEXT NOT NULL,
                    	"username" TEXT NOT NULL,
                    	"pass" TEXT NOT NULL
                    );
                """;
        sqLiteDatabase.execSQL(createDatabaseQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addUser(ServiceUser serviceUser){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", serviceUser.getEmail());
        contentValues.put("username", serviceUser.getUsername());
        contentValues.put("pass", serviceUser.getPass());

        long rows = sqLiteDatabase.insert("service_users", null, contentValues);

        return rows >= 0;
    }
    public void updateUser(ServiceUser serviceUser){

    }
    public void deleteUserById(Integer id){

    }
}