package com.example.sampleminorproject;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections; // Import Collections for shuffling

public class AptitudeTestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;
    ImageView backbutton;
    TextView topBackground, timer;
    private CountDownTimer countDownTimer; // Countdown timer variable
    private final long START_TIME_IN_MILLIS = 300000; // 5 minutes in milliseconds
    int score = 0;
    int totalQuestion = QuestionAnswerAptitude.question.length; // Use the QuestionAnswerAptitude class
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    ArrayList<Integer> questionIndices; // List to hold randomized indices

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize the question indices and shuffle them
        questionIndices = new ArrayList<>();
        for (int i = 0; i < totalQuestion; i++) {
            questionIndices.add(i); // Add indices to the list
        }
        Collections.shuffle(questionIndices); // Shuffle the indices

        backbutton = findViewById(R.id.backarrow);
        backbutton.setOnClickListener(v -> showQuitDialog());

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);
        timer = findViewById(R.id.timer); // Initialize the timer TextView

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total Questions : " + totalQuestion);

        loadNewQuestion();
        startTimer(); // Start the timer

        topBackground = findViewById(R.id.topbackground);
        Animation topToDown = AnimationUtils.loadAnimation(this, R.anim.toptodown);
        Animation DownToTop = AnimationUtils.loadAnimation(this, R.anim.downtotop);
        Animation FadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);

        // Apply the animation to the TextView
        topBackground.startAnimation(topToDown);
        backbutton.startAnimation(topToDown);
        totalQuestionsTextView.startAnimation(topToDown);
        questionTextView.startAnimation(topToDown);
        submitBtn.startAnimation(DownToTop);
        ansA.startAnimation(FadeIn);
        ansB.startAnimation(FadeIn);
        ansC.startAnimation(FadeIn);
        ansD.startAnimation(FadeIn);
        timer.startAnimation(topToDown);
    }

    @Override
    public void onClick(View view) {
        // Reset button background colors
        ansA.setBackgroundColor(Color.parseColor("#388BC0"));
        ansB.setBackgroundColor(Color.parseColor("#388BC0"));
        ansC.setBackgroundColor(Color.parseColor("#388BC0"));
        ansD.setBackgroundColor(Color.parseColor("#388BC0"));

        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit_btn) {
            // Check if the answer is correct
            if (selectedAnswer.equals(QuestionAnswerAptitude.correctAnswers[questionIndices.get(currentQuestionIndex)])) { // Use shuffled indices
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();
        } else {
            // Answer selected
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.BLACK);
        }
    }

    void loadNewQuestion() {
        // Check if the quiz is finished
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        // Load the question and answer options using shuffled indices
        questionTextView.setText(QuestionAnswerAptitude.question[questionIndices.get(currentQuestionIndex)]); // Use shuffled indices
        ansA.setText(QuestionAnswerAptitude.choices[questionIndices.get(currentQuestionIndex)][0]);
        ansB.setText(QuestionAnswerAptitude.choices[questionIndices.get(currentQuestionIndex)][1]);
        ansC.setText(QuestionAnswerAptitude.choices[questionIndices.get(currentQuestionIndex)][2]);
        ansD.setText(QuestionAnswerAptitude.choices[questionIndices.get(currentQuestionIndex)][3]);
    }

    void finishQuiz() {
        countDownTimer.cancel(); // Cancel the timer when finishing the quiz

        // Calculate the percentage score
        int percentageScore = (int) (((double) score / totalQuestion) * 100);

        // Determine pass/fail status based on percentage score
        String passStatus = (percentageScore >= 60) ? "Passed" : "Failed"; // 60% as passing criterion

        // Show quiz result dialog with percentage score and correct answers
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("You answered " + score + " out of " + totalQuestion + " questions correctly.\n" +
                        "Your score is: " + percentageScore + "/100")
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        startTimer(); // Restart the timer
        loadNewQuestion();
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Ensure any previous timer is cancelled
        }

        countDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) { // 1 second interval
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timer.setText("00:00");

                // Display a messagebox (AlertDialog) saying "Timer Ended"
                new AlertDialog.Builder(AptitudeTestActivity.this)
                        .setTitle("Time's Up!")
                        .setMessage("The timer has ended.")
                        .setCancelable(false) // Disable dismissing by tapping outside
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // After the user clicks OK, go to the HomeInput activity
                                Intent intent = new Intent(AptitudeTestActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish(); // Close the current activity
                            }
                        })
                        .show();
            }
        }.start();
    }

    private void updateTimer(long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished / 1000) / 60; // Calculate remaining minutes
        int seconds = (int) (millisUntilFinished / 1000) % 60; // Calculate remaining seconds

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timer.setText(timeLeftFormatted); // Update the timer TextView
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancel the timer when activity is destroyed to avoid leaks
        }
    }

    @Override
    public void onBackPressed() {
        showQuitDialog(); // Show the quit dialog when the back button is pressed
    }

    private void showQuitDialog() {
        new AlertDialog.Builder(AptitudeTestActivity.this)
                .setTitle("Quit Test")
                .setMessage("Do you want to quit the test?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    if (countDownTimer != null) {
                        countDownTimer.cancel(); // Cancel the timer when quitting
                    }
                    Intent intent = new Intent(AptitudeTestActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                })
                .setNegativeButton("No", null) // User clicked "No", just dismiss the dialog
                .show();
    }
}
