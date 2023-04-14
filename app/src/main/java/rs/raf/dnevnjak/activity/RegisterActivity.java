package rs.raf.dnevnjak.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.model.ServiceUser;
import rs.raf.dnevnjak.util.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPass;
    private Button buttonRegister;
    private Button buttonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initListeners();
    }

    private void initView(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPass = findViewById(R.id.editTextPass);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonBack = findViewById(R.id.buttonBack);
    }
    private void initListeners(){
        buttonRegister.setOnClickListener(v -> {
            try{
                ServiceUser serviceUser = new ServiceUser(-1, editTextEmail.getText().toString(), editTextUsername.getText().toString(), editTextPass.getText().toString());

                DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
                databaseHelper.addUser(serviceUser);
                Log.i("dnevnjak", "Successfully added user");
            }
            catch(Exception e){
                Log.e("dnevnjak", e.getMessage());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}