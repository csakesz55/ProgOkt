package com.example.progokt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    ArrayList<Integer> usedQuestions = new ArrayList<>();
    Question question = new Question();
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        final String className = intent.getStringExtra("className");
        String gradeString = intent.getStringExtra("grade").substring(0,2);
        if (gradeString.substring(1,2).equals(".")){
            gradeString = gradeString.substring(0,1);
        }

        final int grade = Integer.parseInt(gradeString);

        final ImageView feedbackImageView = findViewById(R.id.feedbackImageView);
        TextView classTextView = findViewById(R.id.classTextView);
        final TextView questionTextView = findViewById(R.id.questionTextView);
        final Button answerButton1 = findViewById(R.id.answerButton1);
        final Button answerButton2 = findViewById(R.id.answerButton2);
        final Button answerButton3 = findViewById(R.id.answerButton3);
        final Button answerButton4 = findViewById(R.id.answerButton4);

        ArrayList<Button> answerButtons = new ArrayList<>();
        answerButtons.add(answerButton1);
        answerButtons.add(answerButton2);
        answerButtons.add(answerButton3);
        answerButtons.add(answerButton4);

        classTextView.setText(className);

        JSONArray questionsJSONArray = getJSONArrayFromFile();
        final ArrayList<Question> questionArrayList = getQuestionArrayList(questionsJSONArray);

        question = getQuestion(className, grade, questionArrayList);

        setQuestion(question, questionTextView, answerButton1, answerButton2, answerButton3, answerButton4);

        for (final Button button : answerButtons){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (button.getText().toString().equals(question.getAnswer())){
                        feedbackImageView.setImageDrawable(getDrawable(R.drawable.green_tick));
                        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                        feedbackImageView.startAnimation(animFadeOut);
                        feedbackImageView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                feedbackImageView.setImageResource(0);
                            }
                        },1000);
                        correctAnswers++;
                        question = getQuestion(className, grade, questionArrayList);
                        if (question.getId() == -1) {
                            Intent myIntent = new Intent(TestActivity.this, EndActivity.class);
                            myIntent.putExtra("correctAnswers", correctAnswers);
                            TestActivity.this.startActivity(myIntent);
                        } else {
                            setQuestion(question, questionTextView, answerButton1, answerButton2, answerButton3, answerButton4);
                        }
                    } else {
                        feedbackImageView.setImageDrawable(getDrawable(R.drawable.red_cross));
                        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                        feedbackImageView.startAnimation(animFadeOut);
                        feedbackImageView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                feedbackImageView.setImageResource(0);
                            }
                        },1000);
                    }
                }
            });
        }

    }

    private Question getQuestion(String className, int grade, ArrayList<Question> questionArrayList){
        for (int i=0; i<questionArrayList.size(); i++){
            Question q = questionArrayList.get(i);
            if (!usedQuestions.contains(q.getId()) && q.getClassName().equals(className) && q.getGrade() == grade){
                usedQuestions.add(q.getId());
                return q;
            }
        }
        return new Question(-1);
    }

    private JSONArray getJSONArrayFromFile(){
        ParseJson parseJson = new ParseJson();
        try {
            JSONObject jsonObject = new JSONObject(parseJson.loadJSONFromAsset(TestActivity.this, "Questions.json"));
            return jsonObject.getJSONArray("questions");
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private ArrayList<Question> getQuestionArrayList(JSONArray questionsJSONArray) {
        Gson gson = new Gson();
        ArrayList<Question> questionArrayList = new ArrayList<>();
        for (int i = 0; i<questionsJSONArray.length(); i++){
            Question question = null;
            try {
                question = gson.fromJson(questionsJSONArray.getString(i), Question.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            questionArrayList.add(question);
        }
        return questionArrayList;
    }

    private void setQuestion(Question question, TextView questionTextView, Button answerButton1, Button answerButton2, Button answerButton3, Button answerButton4){
        questionTextView.setText(question.getQuestionText());
        answerButton1.setText(question.option1);
        answerButton2.setText(question.option2);
        answerButton3.setText(question.option3);
        answerButton4.setText(question.option4);
    }
}