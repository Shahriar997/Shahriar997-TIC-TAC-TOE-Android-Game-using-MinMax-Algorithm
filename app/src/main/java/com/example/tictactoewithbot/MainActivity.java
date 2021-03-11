package com.example.tictactoewithbot;


import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    int player = 0; // 0 = circle.... 1 = cross...... 2 = empty
    int[] currentState = {2,2,2,2,2,2,2,2,2};  // initially all the cells are empty.
    int[][] goalStates = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive = true;

    GFG bot = new GFG();

    public void gameStart(View view) throws InterruptedException {


        ImageView counter = (ImageView) view;
        String tag = counter.getTag().toString();
        Log.i("tag info", ""+tag);

        int tappedCounter = Integer.parseInt(tag);

        if (currentState[tappedCounter] == 2 && gameActive) {

            currentState[tappedCounter] = player;

            counter.setTranslationY(-1100);

            counter.setImageResource(R.drawable.circle);
            player = 1;
            Log.i("Player 1", "Chaal disi");

            counter.setTranslationY(0);
            // checking if anyone reached to the goal state.
            boolean someOneWon;

            for (int[] goalState:goalStates) {    // goalState.length will give column length
                someOneWon = false;
                if(currentState[goalState[0]] == currentState[goalState[1]] && currentState[goalState[1]] == currentState[goalState[2]] && currentState[goalState[0]] != 2){
                    someOneWon = true;
                }
                if (someOneWon) {

                    TextView txt = (TextView) findViewById(R.id.playAgainText);
                    txt.setText("You Win!!!");
                    txtButtonReposition(view, "You're the winner!");
                    gameActive = false;
                    break;
                } else if (!someOneWon && notContain(currentState, 2)) {

                    TextView txt = (TextView) findViewById(R.id.playAgainText);
                    txt.setText("It's a Draw");
                    txtButtonReposition(view, "Draw!");
                    gameActive = false;
                    break;
                }
            }
        }

        if(!gameActive){
            return;
        }

        // bot's turn
        bot.updateBoard(currentState);
        int botMove = bot.runTicTacToeBot();
        counter = getImageViewObject(botMove, view);
        Log.i("image id: ", ""+counter.getId());
        tag = counter.getTag().toString();
        Log.i("tag info", ""+tag);

        tappedCounter = Integer.parseInt(tag);

        if (currentState[tappedCounter] == 2 && gameActive) {

            currentState[tappedCounter] = player;
            arrayPrint(currentState);

            counter.setTranslationY(-1100);
            counter.setImageResource(R.drawable.cross);
            player = 0;


            Handler handler = new Handler();
            ImageView finalCounter = counter;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finalCounter.animate().translationY(0).rotation(3600).setDuration(1000);
                }
            }, 1000);
            //what if it ends up with a draw


            // checking if anyone reached to the goal state.
            boolean someOneWon;

            for (int[] goalState:goalStates) {    // goalState.length will give column length
                someOneWon = false;
                if(currentState[goalState[0]] == currentState[goalState[1]] && currentState[goalState[1]] == currentState[goalState[2]] && currentState[goalState[0]] != 2){
                    someOneWon = true;
                }
                if (someOneWon) {

                    TextView txt = (TextView) findViewById(R.id.playAgainText);
                    txt.setText("BOT Wins!!!");
                    txtButtonReposition(view, "BOT is the winner!");
                    gameActive = false;
                    break;
                } else if (!someOneWon && notContain(currentState, 2)) {

                    TextView txt = (TextView) findViewById(R.id.playAgainText);
                    txt.setText("It's a Draw");
                    txtButtonReposition(view,"Draw!");
                    gameActive = false;
                    break;
                }
            }

        }

    }

    public ImageView getImageViewObject(int botMove, View view){
        ImageView img;
        if(botMove == 0){
            img = findViewById(R.id.imageView1);
        } else if(botMove == 1){
            img = findViewById(R.id.imageView2);
        } else if(botMove == 2){
            img = findViewById(R.id.imageView3);
        } else if(botMove == 3){
            img = findViewById(R.id.imageView4);
        } else if(botMove == 4){
            img = findViewById(R.id.imageView5);
        } else if(botMove == 5){
            img = findViewById(R.id.imageView6);
        } else if(botMove == 6){
            img = findViewById(R.id.imageView7);
        } else if(botMove == 7){
            img = findViewById(R.id.imageView8);
        } else{
            img = findViewById(R.id.imageView9);
        }
        return img;
    }

    public void arrayPrint(int[] a){
        String s = "";
        for(int i = 0; i < a.length; i++){
            s+=a[i]+", ";
        }
        Log.i("currentState", ""+s);
    }

    public void txtButtonReposition(View view, String toastMessage){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), ""+toastMessage, Toast.LENGTH_LONG).show();
                TextView txt = (TextView) findViewById(R.id.playAgainText);
                txt.setVisibility(View.VISIBLE);
                Button button = (Button) findViewById(R.id.button);
                button.setVisibility(View.VISIBLE);
            }
        }, 1000);


    }

    public boolean notContain(int[] a, int b){
        boolean contain = true;
        for(int i = 0 ; i < a.length; i++){
            if (a[i] == b){
                contain = false;
                break;
            }
        }

        return contain;
    }

    public void playAgain(View view){
        TextView txt = (TextView) findViewById(R.id.playAgainText);
        txt.setVisibility(View.INVISIBLE);
        Button button = (Button) findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for(int i = 0 ; i < currentState.length; i++){
            currentState[i] = 2;
        }


        player = 0;
        gameActive = true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}