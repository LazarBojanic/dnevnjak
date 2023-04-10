package rs.raf.dnevnjak.activity;

import static rs.raf.dnevnjak.app.DnevnjakApp.DNEVNJAK_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.model.Student;

public class StudentActivity extends AppCompatActivity {
    public static final String STUDENT_KEY = "studentKey";
    private Student student;

    private TextView textViewMessage;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        initView();
        parseIntent();
        initListeners();
    }
    private void initView(){
        textViewMessage = findViewById(R.id.textViewMessage);
        buttonBack = findViewById(R.id.buttonBack);
    }
    private void parseIntent(){
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            this.student = (Student) intent.getExtras().getSerializable(STUDENT_KEY);
        }
        String message;
        if(this.student != null){
            message = "Student name is " + this.student.getName();
        }
        else{
            message = "Student name not received";
        }
        Log.i(DNEVNJAK_TAG, message);
        textViewMessage.setText(message);
    }

    private void initListeners(){

    }

}