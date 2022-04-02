package com.example.progokt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RadioGroup classRadioGroup = findViewById(R.id.classRadioGroup);
        final RadioGroup gradeRadioGroup = findViewById(R.id.gradeRadioGroup);
//        final RadioGroup questionRadioGroup = findViewById(R.id.questionRadioGroup);

        ImageButton startTestButton = findViewById(R.id.startTestButton);

        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, TestActivity.class);
                RadioButton selectedClass = (RadioButton) findViewById(classRadioGroup.getCheckedRadioButtonId());
                RadioButton selectedGrade = (RadioButton) findViewById(gradeRadioGroup.getCheckedRadioButtonId());
//                RadioButton selectedQuestionNumber = (RadioButton) findViewById(questionRadioGroup.getCheckedRadioButtonId());

                myIntent.putExtra("className", selectedClass.getContentDescription());
                myIntent.putExtra("grade", selectedGrade.getText());
//                myIntent.putExtra("questionNumber", selectedQuestionNumber.getText());
                MainActivity.this.startActivity(myIntent);
            }
        });
    }


}