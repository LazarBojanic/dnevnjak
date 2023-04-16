package rs.raf.dnevnjak.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.Serializable;

import rs.raf.dnevnjak.app.DnevnjakApp;
import rs.raf.dnevnjak.model.ServiceUser;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, "dnevnjak.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = " CREATE TABLE users ( id INTEGER PRIMARY KEY, email TEXT NOT NULL, username TEXT NOT NULL, pass TEXT NOT NULL )";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean passwordIsValid(String str) {
        String specialCharacters = "~#^|$%&*!";
        for (int i = 0; i < str.length(); i++) {
            if (specialCharacters.contains(String.valueOf(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
    public boolean loginUser(Context context, ServiceUser serviceUser) throws JsonProcessingException {
        if(validateUserData(context, serviceUser)){
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            String query = "SELECT * FROM users WHERE email = '" + serviceUser.getEmail() + "' AND username = '" + serviceUser.getUsername() + "'";
            Cursor resultSet = sqLiteDatabase.rawQuery(query, null);

            if (resultSet.moveToFirst()){
                String columnPass = resultSet.getString(Math.abs(resultSet.getColumnIndex("pass")));
                if(columnPass != null){
                    if(Hasher.checkPassword(serviceUser.getPass(), columnPass)){
                        Util.putUserSharedPreference(context, serviceUser);
                        return true;
                    }
                    else{
                        Toast.makeText(context, "Password doesn't match", Toast.LENGTH_SHORT).show();
                        resultSet.close();
                        sqLiteDatabase.close();
                        return false;
                    }
                }
            }
            else{
                Toast.makeText(context, "Email or username incorrect", Toast.LENGTH_SHORT).show();
                resultSet.close();
                sqLiteDatabase.close();
                return false;
            }
        }
        return false;
    }
    public boolean validateUserData(Context context, ServiceUser serviceUser){
        if(serviceUser.getEmail().equals("")){
            Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(serviceUser.getUsername().equals("")){
            Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(serviceUser.getPass().equals("")){
            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            if(serviceUser.getPass().length() < 5){
                return false;
            }
            else{
                return passwordIsValid(serviceUser.getPass());
            }
        }
    }
    public boolean registerUser(Context context, ServiceUser serviceUser) throws JsonProcessingException {

        if(validateUserData(context, serviceUser)){
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM users WHERE email = '" + serviceUser.getEmail() + "'";
            Cursor resultSet = sqLiteDatabase.rawQuery(query, null);

            if (resultSet.moveToFirst()){
                String columnEmail = resultSet.getString(Math.abs(resultSet.getColumnIndex("email")));
                if(columnEmail != null){
                    Toast.makeText(context, "Email already exists", Toast.LENGTH_SHORT).show();
                    resultSet.close();
                    sqLiteDatabase.close();
                    return false;
                }
            }
            else{
                serviceUser.setPass(Hasher.hashPassword(serviceUser.getPass()));
                if(addUser(serviceUser)){
                    Toast.makeText(context, "Successful registration", Toast.LENGTH_SHORT).show();
                }
                else{
                    resultSet.close();
                    sqLiteDatabase.close();
                    return false;
                }
            }
            resultSet.close();
            sqLiteDatabase.close();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addUser(ServiceUser serviceUser){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", serviceUser.getEmail());
        contentValues.put("username", serviceUser.getUsername());
        contentValues.put("pass", serviceUser.getPass());

        long rows = sqLiteDatabase.insert("users", null, contentValues);
        sqLiteDatabase.close();

        return rows >= 0;
    }
    public void updateUser(ServiceUser serviceUser){

    }
    public void deleteUserById(Integer id){

    }
}