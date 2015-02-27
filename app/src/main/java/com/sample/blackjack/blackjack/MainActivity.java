package com.sample.blackjack.blackjack;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        TextView firstCard;
        TextView secondCard;
        TextView thirdCard;

        Button nextCardButton;
        Button resetButton;
        Button endTurnButton;

        Random r = new Random();

        int scoreUser = 0;
        int scoreComputer = 0;
        int turn = 1;


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            firstCard = (TextView) rootView.findViewById(R.id.input1);
            secondCard = (TextView) rootView.findViewById(R.id.input2);
            thirdCard = (TextView) rootView.findViewById(R.id.input3);
            nextCardButton = (Button) rootView.findViewById(R.id.generateButton);
            resetButton = (Button) rootView.findViewById(R.id.resetButton);
            endTurnButton = (Button) rootView.findViewById(R.id.buttonEndTurn);

            nextCardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scoreUser += r.nextInt(10)+1;
                    scoreComputer += r.nextInt(10)+1;

                    if(turn == 1){
                        firstCard.setText(Integer.toString(scoreUser));
                    } else if(turn == 2){
                        secondCard.setText(Integer.toString(scoreUser));
                        endTurnButton.setEnabled(true);
                    } else {
                        thirdCard.setText(Integer.toString(scoreUser));
                        determineWinner();
                    }
                    turn++;
                }

            });

            endTurnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    determineWinner();
                }
            });

            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetGame();
                }
            });


            return rootView;
        }


        private void determineWinner(){
            if(scoreUser > 21 || (scoreComputer > scoreUser && scoreComputer < 21)){
                Toast.makeText(getActivity(), "You Lose! Score: " + Integer.toString(scoreUser) + " Comp. Score: " + Integer.toString(scoreComputer)  , Toast.LENGTH_LONG).show();
            } else if(scoreUser <= 21 && scoreUser >= scoreComputer){
                Toast.makeText(getActivity(), "You Win!" + Integer.toString(scoreUser) + " Comp. Score: " + Integer.toString(scoreComputer) , Toast.LENGTH_LONG).show();
            } else if(scoreComputer > 21){
                Toast.makeText(getActivity(), "You Win!" + Integer.toString(scoreUser) + " Comp. Score: " + Integer.toString(scoreComputer), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "We missed something! " + Integer.toString(scoreUser) + " Comp. Score: " + Integer.toString(scoreComputer), Toast.LENGTH_LONG).show();
            }
            resetGame();

        }

        private void resetGame(){
            scoreUser = 0;
            scoreComputer = 0;
            turn = 1;
            firstCard.setText("First Card");
            secondCard.setText("Second Card");
            thirdCard.setText("Third Card");
        }






    }
}
