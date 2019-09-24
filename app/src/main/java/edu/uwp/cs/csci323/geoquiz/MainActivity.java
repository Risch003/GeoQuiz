package edu.uwp.cs.csci323.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;

    // chapter two challenge 3
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_INDEX2 = "index";
    private static final String KEY_INDEX3 = "index";
    private static int score = 0;
    private int messageId = 0;
    private String end = "";

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),

    };
    private int mCurrentIndex = 0;
    private ArrayList<Integer> answers = new ArrayList<Integer>(mQuestionBank.length);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        fillArray();

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            //answers = savedInstanceState.getIntegerArrayList(KEY_INDEX2);
            //score = savedInstanceState.getInt(KEY_INDEX3, 0);
        }

        viewById();
        setOnClick();
        updateQuestion();

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putIntegerArrayList(KEY_INDEX2,answers);
        //savedInstanceState.putInt(KEY_INDEX3,score);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        if (userPressedTrue == answerIsTrue) {
            messageId = R.string.correct_toast;
            answers.add(mCurrentIndex,1);
            score ++;
        } else {
            messageId = R.string.incorrect_toast;
            answers.add(mCurrentIndex, 1);

        }
        //Challenge: Chapter one Challenge one
        Toast gravity = Toast.makeText(MainActivity.this, messageId, Toast.LENGTH_SHORT);
        gravity.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 170);
        gravity.show();
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);


    }
    private void enable(){
        if(answers.get(mCurrentIndex).equals(0)){
            mFalseButton.setEnabled(true);
            mTrueButton.setEnabled(true);
        } else {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }
    }
    private void score(){
        if(mCurrentIndex == mQuestionBank.length-1){
            double percentage = ((double)score/(double)mQuestionBank.length) * 100;

            end = "You have Answered " + mQuestionBank.length + " Question. You got " + score + " right. Your percentage is " + (int)percentage + "%";
            Toast gravity = Toast.makeText(MainActivity.this, end, Toast.LENGTH_SHORT);
            gravity.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 170);
            gravity.show();
        }
    }

    private void fillArray(){
        for (int i = 0; i < mQuestionBank.length; i++) {
            answers.add(i, 0);
        }
    }

    private void viewById(){
        mQuestionTextView = (TextView) findViewById(R.id.question_text_window);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.previous_button);

    }
    private void setOnClick(){
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });



        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mCurrentIndex == mQuestionBank.length-1){
                    score();
                    mCurrentIndex = mQuestionBank.length-1;

                }else {
                    mCurrentIndex++;
                    updateQuestion();
                    enable();

                }
            }
        });

        // Chapter two challenge one
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == mQuestionBank.length-1){
                    score();
                    mCurrentIndex = mQuestionBank.length-1;

                }else {
                    mCurrentIndex++;
                    updateQuestion();
                    enable();

                }
            }
        });

        // chapter 2 challenge 2
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0){
                    mCurrentIndex = 0;

                }else {
                    mCurrentIndex--;
                    updateQuestion();
                    enable();
                }
            }
        });
    }

}
