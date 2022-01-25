package com.motion.internetconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.motion.internetconnection.databinding.ActivityScoreBinding;

public class ScoreActivity extends AppCompatActivity {
    private ActivityScoreBinding scoreBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scoreBinding=ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(scoreBinding.getRoot());
        Intent intent =getIntent();
        int getResult=intent.getIntExtra("rightAnswer",0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int max=preferences.getInt("max",0);
        String score=String.format("Сиздин жыйынтык %s\n Максимальный резултат %s",getResult,max);
        scoreBinding.tvScore.setText(score);
    }

    public void onClickGetStarted(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}