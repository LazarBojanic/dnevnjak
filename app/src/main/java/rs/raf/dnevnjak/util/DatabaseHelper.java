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
    public boolean passwordIsValid(Context context, String pass) {
        if(!pass.equals("")){
            if(pass.length() >= 5){
                String specialCharacters = "~#^|$%&*!";
                for (int i = 0; i < pass.length(); i++) {
                    if (specialCharacters.contains(String.valueOf(pass.charAt(i)))) {
                        Toast.makeText(context, "Password cannot specials characters (~#^|$%&*!)", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                return true;
            }
            else{
                Toast.makeText(context, "Password must have at least 5 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else{
            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
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
                        Integer id = resultSet.getInt(Math.abs(resultSet.getColumnIndex("id")));
                        if(id >= 1){
                            serviceUser.setId(id);
                            if(Util.putUserSharedPreference(context, serviceUser)){
                                resultSet.close();
                                sqLiteDatabase.close();
                                return true;
                            }
                            else{
                                resultSet.close();
                                sqLiteDatabase.close();
                                return false;
                            }
                        }
                        else{
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            resultSet.close();
                            sqLiteDatabase.close();
                            return false;
                        }
                    }
                    else{
                        Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show();
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
        return passwordIsValid(context, serviceUser.getPass());
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
    public boolean checkPasswordValidityWhenChanging(Context context, Integer id, String oldPass, String newPass, String confirmNewPass){
        if(newPass.equals(confirmNewPass)){
            if(!oldPass.equals(newPass)){
                if(passwordIsValid(context, newPass)){
                    return verifyOldPasswordMatches(context, id, oldPass);
                }
                else{
                    return false;
                }
            }
            else{
                Toast.makeText(context, "New password must be different from old password", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else{
            Toast.makeText(context, "New password not incorrectly confirmed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean verifyOldPasswordMatches(Context context, Integer id, String oldPass){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor resultSet = sqLiteDatabase.rawQuery("SELECT * FROM users WHERE id = ?", new String[]{id.toString()});
        if(resultSet.moveToFirst()){
            String columnPass = resultSet.getString(Math.abs(resultSet.getColumnIndex("pass")));
            if(Hasher.checkPassword(oldPass, columnPass)){
                resultSet.close();
                sqLiteDatabase.close();
                return true;
            }
            else{
                Toast.makeText(context, "Old password doesn't match", Toast.LENGTH_SHORT).show();
                resultSet.close();
                sqLiteDatabase.close();
                return false;
            }
        }
        else{
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            resultSet.close();
            sqLiteDatabase.close();
            return false;
        }
    }
    public boolean changeUserPassword(Context context, String oldPass, String newPass, String confirmNewPass){
        ServiceUser serviceUser = Util.getUserSharedPreference(context);
        if(serviceUser != null){
            Integer id = serviceUser.getId();
            if(checkPasswordValidityWhenChanging(context, id, oldPass, newPass, confirmNewPass)){
                SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("pass", confirmNewPass);
                int rows = sqLiteDatabase.update("users", contentValues, "id = ?", new String[] {id.toString()});
                if(rows > 0){
                    Toast.makeText(context, "Password successfully updated", Toast.LENGTH_SHORT).show();
                    sqLiteDatabase.close();
                    serviceUser.setPass(newPass);
                    return Util.putUserSharedPreference(context, serviceUser);
                }
                else{
                    Toast.makeText(context, "Error updating password", Toast.LENGTH_SHORT).show();
                    sqLiteDatabase.close();
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }

    }
    public void updateUser(ServiceUser serviceUser){

    }
    public void deleteUserById(Integer id){

    }
}