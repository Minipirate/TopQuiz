package nessti.quiz.com.controller;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.os.Handler;
    import android.view.MotionEvent;
    import android.view.View;
    import android.widget.TextView;
    import android.widget.Button;
    import android.widget.Toast;

    import java.util.Arrays;

    import nessti.quiz.com.R;
    import nessti.quiz.com.model.Question;
    import nessti.quiz.com.model.QuestionBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("GameActivity::onCreate()");

        mQuestionBank = this.generateQuestions();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        }else {
            mScore = 0;
            mNumberOfQuestions = 4;
        }

        mEnableTouchEvents = true;

        // Wire widgets
        mQuestionTextView = (TextView) findViewById(R.id.activity_game_question_text);
        mAnswerButton1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswerButton2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswerButton3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswerButton4 = (Button) findViewById(R.id.activity_game_answer4_btn);

        // Use the tag property to 'name' buttons
        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);



        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);



        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
       int responseIndex = (int) v.getTag();

       if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
           // Good answer
           Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
       } else {
           // Wrong answer
           Toast.makeText(this, "Wrong answer!", Toast.LENGTH_LONG).show();
       }

       mEnableTouchEvents = false;

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               mEnableTouchEvents = true;

               // If this is the last question, ends the game
               //Else, display the next question
               if (--mNumberOfQuestions == 0) {
                   // No question left, end the game
                   endGame();
               } else {
                   mCurrentQuestion = mQuestionBank.getQuestion();
                   displayQuestion(mCurrentQuestion);
               }
           }
       }, 2000); // LENGTH_SORT is usually 2 second long
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent (ev);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // End the activity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void displayQuestion(final Question question){
            mQuestionTextView.setText(question.getQuestion());
            mAnswerButton1.setText(question.getChoiceList().get(0));
            mAnswerButton2.setText(question.getChoiceList().get(1));
            mAnswerButton3.setText(question.getChoiceList().get(2));
            mAnswerButton4.setText(question.getChoiceList().get(3));

        }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42", "101", "600", "742"),
                3);

        Question question4 = new Question ("How many rings does Saturn have ?",
                Arrays.asList("1", "2", "3", "4"),
                2);


        return new QuestionBank(Arrays.asList(question1, question2, question3, question4));
    }

    @Override
    protected void onStart(){
        super.onStart();

        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();

        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();

        System.out.println("GameActivity::onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();

        System.out.println("GameActivity::onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        System.out.println("GameActivity::onDestroy()");
    }
}


