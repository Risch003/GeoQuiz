/*
Cameron Risch
Assignment 1
9/15/19
CSCI-323

 */


package edu.uwp.cs.csci323.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    // TODO image button challenge chapter 2
    private ImageButton mNextButton;

    // TODO challenge chapter 2 prev button
    private ImageButton mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";

    // extra indexes for saved instances
    private static final String KEY_INDEX2 = "anything";
    private static final String KEY_INDEX3 = "somerthinf";
    private static final String KEY_INDEX4 = "work";
    private static final String KEY_INDEX5 = "wjjoijork";
    private static final String KEY_INDEX6 = "fnajksdf";

    // TODO challenge chapter 6 cheats lefts
    private int cheatsLeft = 4;


    private static final int REQUEST_CODE_CHEAT = 0;
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
    private boolean mIsCheater;
    // TODO chapter 3 challenge preventing repeated answers
    private boolean answer[];

    // TODO chapter 5 challenge
    private boolean cheated[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        // TODO chapter 3 challenge
        answer= new  boolean[mQuestionBank.length];

        // TODO chapter 5 challenge
        cheated = new boolean[mQuestionBank.length];

        // TODO saved instance states for challenges
        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            answer = savedInstanceState.getBooleanArray(KEY_INDEX2);
            score = savedInstanceState.getInt(KEY_INDEX3, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_INDEX4,false);
            cheated = savedInstanceState.getBooleanArray(KEY_INDEX5);
            cheatsLeft = savedInstanceState.getInt(KEY_INDEX6,0);
        }

        viewById();
        enable();
        setOnClick();
        updateQuestion();

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            if(mIsCheater){
                cheated[mCurrentIndex] = true;
            }

        }

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

        // TODO saved instance states for challenges
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBooleanArray(KEY_INDEX2,answer);
        savedInstanceState.putInt(KEY_INDEX3,score);
        savedInstanceState.putBoolean(KEY_INDEX4,mIsCheater);
        savedInstanceState.putBooleanArray(KEY_INDEX5,cheated);
        savedInstanceState.putInt(KEY_INDEX6,cheatsLeft);
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

        if(mIsCheater){
            messageId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageId = R.string.correct_toast;
                score++;
            } else {
                messageId = R.string.incorrect_toast;

            }
        }
        // TODO Challenge: Chapter one Challenge one
        Toast gravity = Toast.makeText(MainActivity.this, messageId, Toast.LENGTH_SHORT);
        gravity.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 170);
        gravity.show();
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);


    }
    // TODO chapter 3 challenge
    private void enable(){
        if(answer[mCurrentIndex]){
            mFalseButton.setEnabled(false);
            mTrueButton.setEnabled(false);
        } else {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }
    // TODO chapter 5 challenge
    private void didCheat(){

        if (cheated[mCurrentIndex]){
            mIsCheater = true;
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

    private void viewById() {
        mQuestionTextView = (TextView) findViewById(R.id.question_text_window);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.previous_button);
        mCheatButton = findViewById(R.id.cheat_button);

    }

    // all the on click listeners
    private void setOnClick(){
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                answer [mCurrentIndex]=true;
                didCheat();
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                answer [mCurrentIndex]=true;
                didCheat();
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
                    //mCurrentIndex +=1;
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    mIsCheater = false;
                    updateQuestion();
                    enable();

                }
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cheatsLeft > 0){
                    cheatsLeft--;
                }
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                Toast gravity = Toast.makeText(MainActivity.this, "You have " + cheatsLeft + " cheats left", Toast.LENGTH_SHORT);
                gravity.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 170);
                gravity.show();
                if (cheatsLeft == 0){
                    mCheatButton.setEnabled(false);
                }
                startActivityForResult(intent, REQUEST_CODE_CHEAT);

            }
        });

        // TODO Chapter two challenge one
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == mQuestionBank.length-1){
                    score();
                    mCurrentIndex = mQuestionBank.length-1;

                }else {
                    mCurrentIndex =+1;
                    mIsCheater = false;
                    updateQuestion();
                    enable();

                }
            }
        });

        // TODO chapter 2 challenge 2 prev button
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0){
                    mCurrentIndex = 0;

                }else {
                    mCurrentIndex-=1;
                    mIsCheater = false;
                    updateQuestion();
                    enable();
                }
            }
        });
    }

}
