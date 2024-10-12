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
import java.util.Collections;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;
    ImageView backbutton;
    TextView topBackground, timer;
    private CountDownTimer countDownTimer; // Countdown timer variable
    private final long START_TIME_IN_MILLIS = 300000; // 5 minutes in milliseconds
    int score = 0;
    int totalQuestion = QuestionAnswerIQ.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    // ArrayList to hold randomized question indices
    ArrayList<Integer> questionIndices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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

        // Initialize and shuffle the question indices for randomization
        for (int i = 0; i < totalQuestion; i++) {
            questionIndices.add(i);
        }
        Collections.shuffle(questionIndices);

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
            if (selectedAnswer.equals(QuestionAnswerIQ.correctAnswers[questionIndices.get(currentQuestionIndex)])) {
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

        // Load the question and answer options using the randomized indices
        questionTextView.setText(QuestionAnswerIQ.question[questionIndices.get(currentQuestionIndex)]);
        ansA.setText(QuestionAnswerIQ.choices[questionIndices.get(currentQuestionIndex)][0]);
        ansB.setText(QuestionAnswerIQ.choices[questionIndices.get(currentQuestionIndex)][1]);
        ansC.setText(QuestionAnswerIQ.choices[questionIndices.get(currentQuestionIndex)][2]);
        ansD.setText(QuestionAnswerIQ.choices[questionIndices.get(currentQuestionIndex)][3]);
    }

    void finishQuiz() {
        countDownTimer.cancel(); // Cancel the timer when finishing the quiz
        String passStatus = (score > totalQuestion * 0.60) ? "Passed" : "Failed";

        // Calculate IQ based on the score (correct answers)
        int iqScore = calculateIQ(score);

        // Show quiz result dialog with IQ score
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion + "\nYour IQ is: " + iqScore)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        // Reinitialize and shuffle the question indices for the new quiz
        questionIndices.clear();
        for (int i = 0; i < totalQuestion; i++) {
            questionIndices.add(i);
        }
        Collections.shuffle(questionIndices);
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
                new AlertDialog.Builder(QuizActivity.this)
                        .setTitle("Time's Up!")
                        .setMessage("The timer has ended.")
                        .setCancelable(false) // Disable dismissing by tapping outside
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // After the user clicks OK, go to the HomeActivity activity
                                Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
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
        new AlertDialog.Builder(QuizActivity.this)
                .setTitle("Quit Test")
                .setMessage("Do you want to quit the test?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    if (countDownTimer != null) {
                        countDownTimer.cancel(); // Cancel the timer when quitting
                    }
                    Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                })
                .setNegativeButton("No", null) // User clicked "No", just dismiss the dialog
                .show();
    }

    private int calculateIQ(int correctAnswers) {
        if (correctAnswers <= 5) {
            return 70 + correctAnswers * 2;  // Gradual increase
        } else if (correctAnswers <= 10) {
            return 80 + (correctAnswers - 5) * 2;
        } else if (correctAnswers <= 15) {
            return 90 + (correctAnswers - 10) * 2;
        } else if (correctAnswers <= 20) {
            return 110 + (correctAnswers - 15) * 2;
        } else if (correctAnswers <= 23) {
            return 120 + (correctAnswers - 20) * 2;
        } else {
            return 140;  // Maximum IQ score
        }
    }
}
