package rs.raf.dnevnjak.activity;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.model.ServiceUser;
import rs.raf.dnevnjak.util.DatabaseHelper;

public class LoginAndRegisterActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPass;
    private Button buttonLogin;
    private Button buttonRegister;
    private Button buttonExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);

        initView();
        initListeners();
    }

    private void initView(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPass = findViewById(R.id.editTextPass);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonExit = findViewById(R.id.buttonExit);
    }

    private void initListeners(){
        buttonLogin.setOnClickListener(view -> {
            try{
                ServiceUser serviceUser = new ServiceUser(-1, editTextEmail.getText().toString(), editTextUsername.getText().toString(), editTextPass.getText().toString());

                DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
                if(databaseHelper.loginUser(this, serviceUser)){
                    Log.i(getResources().getString(R.string.dnevnjakTag), "Login Successful");
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.i(getResources().getString(R.string.dnevnjakTag), "Login Failed");
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e){
                Log.i(getResources().getString(R.string.dnevnjakTag), e.getMessage());
            }
        });

        buttonRegister.setOnClickListener(view -> {
            try{
                ServiceUser serviceUser = new ServiceUser(-1, editTextEmail.getText().toString(), editTextUsername.getText().toString(), editTextPass.getText().toString());

                DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
                if(databaseHelper.registerUser(this, serviceUser)){
                    Log.i(getResources().getString(R.string.dnevnjakTag), "Registration Successful");
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.i(getResources().getString(R.string.dnevnjakTag), "Registration Failed");
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }

            }
            catch(Exception e){
                Log.i(getResources().getString(R.string.dnevnjakTag), e.getMessage());
            }
        });
        buttonExit.setOnClickListener(v -> {
            finish();
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}