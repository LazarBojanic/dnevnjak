package rs.raf.dnevnjak.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.util.DatabaseHelper;
import rs.raf.dnevnjak.util.Util;

public class AddObligationActivity extends AppCompatActivity {

    private RadioGroup radioGroupPriority;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMid;
    private RadioButton radioButtonHigh;
    private EditText editTextTitle;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextDescription;
    private Button buttonCreate;
    private Button buttonCancel;
    private RadioButton radioButtonSelected;

    public AddObligationActivity() {

    }

    public void attachExtraObligation(){
        Intent intent = getIntent();
        Obligation obligation = intent.getSerializableExtra(getResources().getString(R.string.extraObligation), Obligation.class);

        switch(obligation.getPriority()){
            case "low":
                radioButtonLow.toggle();
                break;
            case "mid":
                radioButtonMid.toggle();
                break;
            case "high":
                radioButtonHigh.toggle();
                break;
        }
        editTextTitle.setText(obligation.getTitle());
        String startTimeString = Util.localTimeToString(obligation.getStartTime());
        String endTimeString = Util.localTimeToString(obligation.getEndTime());
        editTextStartTime.setText(startTimeString);
        editTextEndTime.setText(endTimeString);

        editTextDescription.setText(obligation.getDescription());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_obligation);

        initView();
        initListeners();
        radioButtonLow.callOnClick();
    }

    private void initView(){
        radioGroupPriority = findViewById(R.id.radioGroupPriority);

        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMid = findViewById(R.id.radioButtonMid);
        radioButtonHigh = findViewById(R.id.radioButtonHigh);

        editTextTitle = findViewById(R.id.editTextTitle);

        editTextStartTime = findViewById(R.id.editTextStartTime);
        editTextEndTime = findViewById(R.id.editTextEndTime);

        editTextDescription = findViewById(R.id.editTextDescription);

        buttonCreate = findViewById(R.id.buttonCreate);
        buttonCancel = findViewById(R.id.buttonCancel);
    }

    private void initListeners(){
        buttonCreate.setOnClickListener(view -> {

            int selectedRadioButtonId = radioGroupPriority.getCheckedRadioButtonId();
            String priority = "";

            switch(selectedRadioButtonId){
                case R.id.radioButtonLow:
                    priority = "low";
                    break;
                case R.id.radioButtonMid:
                    priority = "mid";
                    break;
                case R.id.radioButtonHigh:
                    priority = "high";
                    break;

            }


            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();
            String startTimeString = editTextStartTime.getText().toString();
            String endTimeString = editTextEndTime.getText().toString();
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
            if(databaseHelper.addObligation(this, title, description, priority, LocalDate.now(), startTimeString, endTimeString)){
                Log.i(getResources().getString(R.string.dnevnjakTag), "success");
                finish();
            }
            else{
                Log.i(getResources().getString(R.string.dnevnjakTag), "error");
            }
        });
        buttonCancel.setOnClickListener(view -> {
            finish();
        });
        radioButtonLow.setOnClickListener(view -> {
            selectButton(radioButtonLow);
        });
        radioButtonMid.setOnClickListener(view -> {
            selectButton(radioButtonMid);
        });
        radioButtonHigh.setOnClickListener(view -> {
            selectButton(radioButtonHigh);
        });
    }
    private void selectButton(RadioButton radioButton){
        if (radioButtonSelected != null) {
            // Deselect the previous button
            radioButtonSelected.setElevation(0);
        }
        // Select the new button
        radioButton.setElevation(50);
        radioButtonSelected = radioButton;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
