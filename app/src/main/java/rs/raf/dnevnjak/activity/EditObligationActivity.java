package rs.raf.dnevnjak.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.util.DatabaseHelper;
import rs.raf.dnevnjak.util.Util;
import rs.raf.dnevnjak.viewmodel.ObligationViewModel;

public class EditObligationActivity extends AppCompatActivity {

    private RadioGroup radioGroupPriority;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMid;
    private RadioButton radioButtonHigh;
    private EditText editTextTitle;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextDescription;
    private Button buttonSave;
    private Button buttonCancel;
    private RadioButton radioButtonSelected;
    private ObligationViewModel obligationViewModel;

    public EditObligationActivity() {

    }
    public void attachObligationToViewElements(Obligation obligation){
        if(obligation != null){
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
    }
    public void getExtraObligation(Bundle savedInstanceState){
        if(savedInstanceState == null){
            Intent intent = getIntent();
            Obligation obligation = intent.getSerializableExtra(getResources().getString(R.string.extraObligation), Obligation.class);
            Log.i(getResources().getString(R.string.dnevnjakTag), "Regular " + obligation.toString());
            obligationViewModel.getObligationMutableLiveData().postValue(obligation);
            obligationViewModel.getObligationMutableLiveData().observe(this, updatedObligation -> {
                if(updatedObligation != null){
                    attachObligationToViewElements(updatedObligation);
                    Log.i(getResources().getString(R.string.dnevnjakTag), "Updated " +  updatedObligation.toString());
                }
                else{
                    Log.i(getResources().getString(R.string.dnevnjakTag), "Updated obligation is nulllll");
                }
            });
        }
        else{
            obligationViewModel.getObligationMutableLiveData().postValue(savedInstanceState.getSerializable(getResources().getString(R.string.stateObligation), Obligation.class));
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_obligation);
        initView();
        obligationViewModel = new ViewModelProvider(this).get(ObligationViewModel.class);
        getExtraObligation(savedInstanceState);

        initObservers();
        initListeners();
        radioButtonLow.callOnClick();

    }
    private void initObservers(){
        obligationViewModel.getObligationMutableLiveData().observe(this, obligation -> {
            attachObligationToViewElements(obligation);
        });
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

        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);

    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(getResources().getString(R.string.stateObligation), obligationViewModel.getObligationMutableLiveData().getValue());
    }
    private void initListeners(){
        buttonSave.setOnClickListener(view -> {
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
            Obligation obligation = obligationViewModel.getObligationMutableLiveData().getValue();
            Integer obligationId = 1;
            if(obligation != null){
                obligationId = obligation.getId();
            }

            if(databaseHelper.updateObligation(this, obligationId, title, description, priority, LocalDate.now(), startTimeString, endTimeString)){
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
            Obligation oldObligation = obligationViewModel.getObligationMutableLiveData().getValue();
            if(oldObligation != null){
                oldObligation.setPriority("low");
            }
            obligationViewModel.getObligationMutableLiveData().postValue(oldObligation);
        });
        radioButtonMid.setOnClickListener(view -> {
            selectButton(radioButtonMid);
            Obligation oldObligation = obligationViewModel.getObligationMutableLiveData().getValue();
            if(oldObligation != null){
                oldObligation.setPriority("mid");
            }
            obligationViewModel.getObligationMutableLiveData().postValue(oldObligation);
        });
        radioButtonHigh.setOnClickListener(view -> {
            selectButton(radioButtonHigh);
            Obligation oldObligation = obligationViewModel.getObligationMutableLiveData().getValue();
            if(oldObligation != null){
                oldObligation.setPriority("high");
            }
            obligationViewModel.getObligationMutableLiveData().postValue(oldObligation);
        });
        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Obligation oldObligation = obligationViewModel.getObligationMutableLiveData().getValue();
                if(oldObligation != null){
                    oldObligation.setTitle(charSequence.toString());
                }

                obligationViewModel.getObligationMutableLiveData().postValue(oldObligation);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Obligation oldObligation = obligationViewModel.getObligationMutableLiveData().getValue();
                if(oldObligation != null){
                    oldObligation.setStartTime(Util.timeStringToLocalTime(charSequence.toString()));
                }

                obligationViewModel.getObligationMutableLiveData().postValue(oldObligation);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Obligation oldObligation = obligationViewModel.getObligationMutableLiveData().getValue();
                if(oldObligation != null){
                    oldObligation.setEndTime(Util.timeStringToLocalTime(charSequence.toString()));
                }

                obligationViewModel.getObligationMutableLiveData().postValue(oldObligation);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Obligation oldObligation = obligationViewModel.getObligationMutableLiveData().getValue();
                if(oldObligation != null){
                    oldObligation.setDescription(charSequence.toString());
                }

                obligationViewModel.getObligationMutableLiveData().postValue(oldObligation);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
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
