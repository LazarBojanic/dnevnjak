package rs.raf.dnevnjak.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.model.Obligation;
import rs.raf.dnevnjak.util.Util;
import rs.raf.dnevnjak.viewmodel.ObligationViewModel;

public class ObligationDetailsActivity extends AppCompatActivity {
    private ObligationViewModel obligationViewModel;
    private RadioGroup radioGroupPriority;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMid;
    private RadioButton radioButtonHigh;
    private TextView textViewTitle;
    private TextView textViewTime;
    private TextView textViewDescription;
    private Button buttonEdit;
    private Button buttonDelete;
    private RadioButton radioButtonSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obligation_details);

        initView();
        obligationViewModel = new ViewModelProvider(this).get(ObligationViewModel.class);
        getExtraObligation(savedInstanceState);
        initObservers();
        initListeners();

        radioButtonLow.callOnClick();

    }
    private void initObservers(){
        obligationViewModel.getObligationMutableLiveData().observe(this, obligation -> {
            if(obligation != null){
                attachObligationToViewElements(obligation);
            }
        });
    }
    public void getExtraObligation(Bundle savedInstanceState){
        if(savedInstanceState == null){
            Intent intent = getIntent();
            Obligation obligation = intent.getSerializableExtra(getResources().getString(R.string.extraObligation), Obligation.class);
            if(obligation == null){
                String date = intent.getStringExtra(getResources().getString(R.string.extraDate));

            }
            Log.i(getResources().getString(R.string.dnevnjakTag), "Regular " + obligation.toString());
            obligationViewModel.getObligationMutableLiveData().postValue(obligation);

        }
        else{
            obligationViewModel.getObligationMutableLiveData().postValue(savedInstanceState.getSerializable(getResources().getString(R.string.stateObligation), Obligation.class));
        }
        Obligation updatedObligation = obligationViewModel.getObligationMutableLiveData().getValue();
        if(updatedObligation != null){
            attachObligationToViewElements(updatedObligation);
            Log.i(getResources().getString(R.string.dnevnjakTag), "Updated " +  updatedObligation.toString());
        }
        else{
            Log.i(getResources().getString(R.string.dnevnjakTag), "Updated obligation is null");
        }
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
            textViewTitle.setText(obligation.getTitle());
            String startTimeString = Util.localTimeToString(obligation.getStartTime());
            String endTimeString = Util.localTimeToString(obligation.getEndTime());
            String timeString = startTimeString + " - " + endTimeString;
            textViewTime.setText(timeString);
            textViewDescription.setText(obligation.getDescription());
        }

    }


    private void initView(){
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMid = findViewById(R.id.radioButtonMid);
        radioButtonHigh = findViewById(R.id.radioButtonHigh);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewTime = findViewById(R.id.textViewTime);
        textViewDescription = findViewById(R.id.textViewDescription);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);


    }
    private void initListeners(){
        buttonEdit.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditObligationActivity.class);
            intent.putExtra(getResources().getString(R.string.extraObligation), obligationViewModel.getObligationMutableLiveData().getValue());
            startActivity(intent);
        });
        buttonDelete.setOnClickListener(view -> {
            //TODO delete
        });
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(getResources().getString(R.string.stateObligation), obligationViewModel.getObligationMutableLiveData().getValue());
    }
}