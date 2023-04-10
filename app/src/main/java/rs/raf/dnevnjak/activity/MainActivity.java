package rs.raf.dnevnjak.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import rs.raf.dnevnjak.R;
import rs.raf.dnevnjak.model.Student;

public class MainActivity extends AppCompatActivity {

    private Button buttonStudent;
    public static final Integer STUDENT_RECIEVED_REQUEST_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListeners();
    }

    private void initView(){
        buttonStudent = findViewById(R.id.buttonStudent);
    }
    private void initListeners(){
        buttonStudent.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentActivity.class);
            Student student = new Student("test");
            intent.putExtra(StudentActivity.STUDENT_KEY, student);
            startActivityForResult(intent, STUDENT_RECIEVED_REQUEST_CODE);
        });
    }
}