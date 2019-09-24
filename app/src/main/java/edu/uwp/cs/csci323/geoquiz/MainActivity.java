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
    private int mForcedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mForcedIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }



        mQuestionTextView = (TextView) findViewById(R.id.question_text_window);



        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkAnswer(true);
                mFalseButton.setEnabled(false);
                mTrueButton.setEnabled(false);

            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkAnswer(false);
                mFalseButton.setEnabled(false);
                mTrueButton.setEnabled(false);
            }
        });



        mNextButton = findViewById(R.id.next_button);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == mQuestionBank.length-1){
                    if(mForcedIndex == mQuestionBank.length){
                        double percentage = ((double)score/(double)mForcedIndex) * 100;

                        end = "You have Answered " + mForcedIndex + " Question. You got " + score + " right. Your percentage is " + (int)percentage + "%";
                        Toast gravity = Toast.makeText(MainActivity.this, end, Toast.LENGTH_SHORT);
                        gravity.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 170);
                        gravity.show();
                    }
                    mCurrentIndex = mQuestionBank.length-1;


                }else {
                    mCurrentIndex +=1;
                    updateQuestion();


                    if (mCurrentIndex == mForcedIndex) {
                        mTrueButton.setEnabled(true);
                        mFalseButton.setEnabled(true);

                    } else {
                        mTrueButton.setEnabled(false);
                        mFalseButton.setEnabled(false);

                    }
                }

            }
        });

        // Chapter two challenge one
        mQuestionTextView =  findViewById(R.id.question_text_window);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == mQuestionBank.length-1){

                    mCurrentIndex = mQuestionBank.length-1;

                }else {
                    mCurrentIndex +=1;
                    updateQuestion();
                    if (mCurrentIndex == mForcedIndex) {
                        mTrueButton.setEnabled(true);
                        mFalseButton.setEnabled(true);

                    } else {
                        mTrueButton.setEnabled(false);
                        mFalseButton.setEnabled(false);
                    }
                }

            }
        });

        // chapter 2 challenge 2
        mPrevButton = findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0){

                    mCurrentIndex = 0;

                }else {
                    mCurrentIndex -=1;
                    updateQuestion();
                    if (mCurrentIndex == mForcedIndex) {
                        mTrueButton.setEnabled(true);
                        mFalseButton.setEnabled(true);

                    } else {
                        mTrueButton.setEnabled(false);
                        mFalseButton.setEnabled(false);
                    }
                }
            }
        });
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
        savedInstanceState.putInt(KEY_INDEX,mForcedIndex);
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
            score +=1;


        } else {
            messageId = R.string.incorrect_toast;
        }
        //Challenge: Chapter one Challenge one
        Toast gravity = Toast.makeText(MainActivity.this, messageId, Toast.LENGTH_SHORT);
        gravity.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 170);
        gravity.show();
        mForcedIndex +=1;

    }
}
