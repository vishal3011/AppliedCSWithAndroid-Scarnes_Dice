package com.android.example.scarnesdice;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static int userOverallScore = 0;
    public static int userTurnScore = 0;
    public static int compOverallScore = 0;
    public static int compTurnScore = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            TextView text = (TextView)findViewById(R.id.computerScore);
            if(computerTurn() == true) {
                if (compTurnScore < 20) {
                    timerHandler.postDelayed(this, 500);
                }
                else{
                    compOverallScore += compTurnScore;
                    enableButtons(true);
                    compTurnScore = 0;
                }
            }
            else{
                compTurnScore = 0;
                enableButtons(true);
            }
            text.setText("Computer Score: " + compOverallScore);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void rollDie(View view){

        int diceValue = randomValue();
        setDiceFace(diceValue);
        if(diceValue != 1)
            userTurnScore += diceValue;
        else {
            enableButtons(false);
            userTurnScore = 0;
            timerHandler.postDelayed(timerRunnable,1000);
        }
    }
    public void enableButtons(boolean val){
        Button roll = (Button)findViewById(R.id.rollButton);
        Button hold = (Button)findViewById(R.id.holdButton);
        Button reset = (Button)findViewById(R.id.resetButton);
        roll.setEnabled(val);
        hold.setEnabled(val);
        reset.setEnabled(val);
    }
    public int randomValue(){
        Random rand = new Random();
        int value;
        value = rand.nextInt(6) + 1; //1 minimum 6 maximum
        return value;
    }
    public boolean computerTurn(){
        int diceValue = randomValue();
        setDiceFace(diceValue);
        if(diceValue != 1) {
            compTurnScore += diceValue;
            return true;
        }
        else {
            compTurnScore = 0;
            return false;
        }
    }
    public void setDiceFace(int diceValue){
        ImageView v = (ImageView)findViewById(R.id.imageView);
        switch (diceValue){
            case 1: v.setImageResource(R.drawable.dice1);
                break;
            case 2: v.setImageResource(R.drawable.dice2);
                break;
            case 3: v.setImageResource(R.drawable.dice3);
                break;
            case 4: v.setImageResource(R.drawable.dice4);
                break;
            case 5: v.setImageResource(R.drawable.dice5);
                break;
            case 6: v.setImageResource(R.drawable.dice6);
                break;
        }
    }
    public void holdDie(View view){
        enableButtons(false);
        TextView text = (TextView)findViewById(R.id.yourScore);
        userOverallScore += userTurnScore;
        userTurnScore = 0;
        text.setText("Your score: " + userOverallScore+" ");
        timerHandler.postDelayed(timerRunnable,0);
    }
    public void resetDie(View view){
        TextView userText = (TextView)findViewById(R.id.yourScore);
        TextView compText = (TextView)findViewById(R.id.computerScore);
        userOverallScore = 0;
        userTurnScore = 0;
        compOverallScore = 0;
        compTurnScore = 0;
        userText.setText("Your score: " + userOverallScore);
        compText.setText("Computer Score: " + compOverallScore);
    }
}