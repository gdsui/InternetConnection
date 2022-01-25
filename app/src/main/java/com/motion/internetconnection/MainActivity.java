package com.motion.internetconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.motion.internetconnection.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    String question;
    int rightAnswer;
    int rightAnswerPosition;
    boolean isPositive;
    int max = 5;
    int min = 30;
    int countOfQuestion=0;
    int countOfRightAnswer=0;
    ArrayList<TextView> options = new ArrayList<>();
    private boolean gameOver =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        options.add(binding.tvOptionOne);
        options.add(binding.textOptionTwo);
        options.add(binding.tvOptionThree);
        options.add(binding.tvOptionFour);
        playNext();

        CountDownTimer timer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {
                int seconds = (int) (l / 1000);
                binding.tvTimer.setText(Integer.toString(seconds));
            }

            @Override
            public void onFinish() {
                gameOver=true;
                Intent intent=new Intent(MainActivity.this,ScoreActivity.class);
                intent.putExtra("rightAnswer",countOfRightAnswer);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Сизге ерилген уакыт сонуна жетти", Toast.LENGTH_LONG).show();
            }
        };
        timer.start();
    }

    private void playNext() {
        generateQuestion();
        for (int i = 0; i < options.size(); i++) {
            if (rightAnswerPosition == i) {
                options.get(i).setText(Integer.toString(rightAnswer));
            } else {
                options.get(i).setText(Integer.toString(generateWrongAnswer()));
            }

        }
        String score=String.format("%s / %s",countOfRightAnswer,countOfQuestion);
        binding.tvCorrectAnswer.setText(score);
    }

    private void generateQuestion() {
        int a = (int) (Math.random() * (max - min + 1) + min);
        int b = (int) (Math.random() * (max - min + 1) + min);
        int mark = (int) (Math.random() * 2);
        isPositive = mark == 1;
        if (isPositive) {
            rightAnswer = a + b;
            question = String.format("%s + %s", a, b);
        } else {
            rightAnswer = a - b;
            question = String.format("%s - %s", a, b);
        }
        binding.tvQuestion.setText(question);
        rightAnswerPosition = (int) (Math.random() * 4);
    }

    private int generateWrongAnswer() {
        int result;
        do {
            result = (int) (Math.random() * max * 2 + 1) - (max - min);
        } while (result == rightAnswer);
        return result;

    }

    public void onClickAnsWer(View view) {
        if (!gameOver) {
            TextView textView = (TextView) view;
            String answer = textView.getText().toString();
            int chosenAnswer = Integer.parseInt(answer);
            if (chosenAnswer == rightAnswer) {
                countOfRightAnswer++;
                Toast.makeText(getApplicationContext(), "Азаматсыз", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Туура эмсе", Toast.LENGTH_SHORT).show();
            }
            countOfQuestion++;
            playNext();
        }
    }
}