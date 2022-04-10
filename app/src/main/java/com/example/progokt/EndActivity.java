package com.example.progokt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        int correctAnswers = intent.getIntExtra("correctAnswers", 0);

        TextView correctTextView = findViewById(R.id.correctTextView);
        //correctTextView.setText("Helyes válaszok: " + correctAnswers + "!");
        correctTextView.setText("Gratulálok, nagyon kitartó voltál!");

        ImageButton homeImageButton = findViewById(R.id.homeImageButton);
        homeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(EndActivity.this, MainActivity.class);
                EndActivity.this.startActivity(myIntent);
            }
        });

    }
}