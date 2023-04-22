package rs.raf.projekat1.lazar_bojanic_rn11621.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import rs.raf.projekat1.lazar_bojanic_rn11621.R;
import rs.raf.projekat1.lazar_bojanic_rn11621.fragment.AccountFragment;
import rs.raf.projekat1.lazar_bojanic_rn11621.fragment.CalendarFragment;
import rs.raf.projekat1.lazar_bojanic_rn11621.fragment.ScheduleFragment;
import rs.raf.projekat1.lazar_bojanic_rn11621.util.DatabaseHelper;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText editTextOldPass;
    private EditText editTextNewPass;
    private EditText editTextConfirmNewPass;
    private Button buttonConfirm;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        initListeners();
    }

    private void initView(){
        editTextOldPass = findViewById(R.id.editTextOldPass);
        editTextNewPass = findViewById(R.id.editTextNewPass);
        editTextConfirmNewPass = findViewById(R.id.editTextConfirmNewPass);

        buttonConfirm = findViewById(R.id.buttonConfirm);
        //Log.i(getResources().getString(R.string.dnevnjakTag), buttonConfirm.getText().toString());
        buttonBack = findViewById(R.id.buttonBack);
    }
    private void initListeners(){
        buttonConfirm.setOnClickListener(v -> {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
            String oldPass = editTextOldPass.getText().toString();
            String newPass = editTextNewPass.getText().toString();
            String confirmNewPass = editTextConfirmNewPass.getText().toString();
            if(databaseHelper.changeUserPassword(this, oldPass, newPass, confirmNewPass)){
                finish();
            }
        });
        buttonBack.setOnClickListener(view -> {
            finish();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}