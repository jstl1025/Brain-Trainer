package com.prototype.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int TOTAL_ANSWERS = 4;

    Button goButton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;
    TextView sumTextView;
    TextView resultTextView;
    TextView scoreTextView;
    TextView timerTextView;
    ConstraintLayout gameConstraintLayout;
    ArrayList<Integer> answers;
    int correctAnswerIdx;
    int outOf;
    int score;

    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        gameConstraintLayout.setVisibility(View.VISIBLE);
        restart(view);
    }

    public void chooseAnswer(View view) {
        outOf++;
        if (Integer.parseInt(view.getTag().toString()) == correctAnswerIdx) {
            resultTextView.setText(R.string.correct_answer);
            score++;
        } else {
            resultTextView.setText(R.string.incorrect_answer);
        }
        resultTextView.setVisibility(View.VISIBLE);
        scoreTextView.setText(getString(R.string.score, score, outOf));
        newProblem();
    }

    public void restart(View view) {
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);
        outOf = 0;
        score = 0;
        scoreTextView.setText(getString(R.string.score, score, outOf));
        button0.setClickable(true);
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);
        newProblem();
        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(getString(R.string.timer, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                resultTextView.setText(getString(R.string.times_up));
                resultTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);

                button0.setClickable(false);
                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        playAgainButton = findViewById(R.id.playAgainButton);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        gameConstraintLayout = findViewById(R.id.gameConstraintLayout);

        goButton.setVisibility(View.VISIBLE);
        gameConstraintLayout.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);
        answers = new ArrayList<>();
    }

    public void newProblem() {
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        sumTextView = findViewById(R.id.sumTextView);
        sumTextView.setText(getString(R.string.sum_string, a, b));

        answers.clear();
        correctAnswerIdx = rand.nextInt(4);
        int correctAnswer = a + b;

        for (int i = 0; i < TOTAL_ANSWERS; i++) {
            if (i == correctAnswerIdx) {
                answers.add(correctAnswer);
            } else {
                int wrongAnswer = rand.nextInt(41);
                while (wrongAnswer == correctAnswer) {
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        button0.setText(getString(R.string.answer, answers.get(0)));
        button1.setText(getString(R.string.answer, answers.get(1)));
        button2.setText(getString(R.string.answer, answers.get(2)));
        button3.setText(getString(R.string.answer, answers.get(3)));
    }
}
