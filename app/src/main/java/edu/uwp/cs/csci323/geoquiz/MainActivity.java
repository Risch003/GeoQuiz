package edu.uwp.cs.csci323.geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_africa,true),
            new Question(R.string.question_asia,true),

    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_window);

        mTrueButton = (Button) findViewById(R.id.true_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkAnswer(true);

            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                checkAnswer(false);

            }
        });


        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mQuestionTextView =  findViewById(R.id.question_text_window);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton = findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex == 0){


                    mCurrentIndex = (mQuestionBank.length -1 );
                }
                else {

                    mCurrentIndex = (mCurrentIndex - 1);
                }
                updateQuestion();
            }
        }));
        updateQuestion();




    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageId = R.string.correct_toast;
        } else {
            messageId = R.string.incorrect_toast;
        }
        //Challenge: Chapter one Challenge one
        Toast gravity = Toast.makeText(MainActivity.this, messageId, Toast.LENGTH_SHORT);
        gravity.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 170);
        gravity.show();
    }





}
