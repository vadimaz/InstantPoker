package com.blogspot.vadimaz.instantpoker;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.vadimaz.instantpoker.game.Card;
import com.blogspot.vadimaz.instantpoker.game.Combination;
import com.blogspot.vadimaz.instantpoker.game.Combinations;
import com.blogspot.vadimaz.instantpoker.game.Game;
import com.blogspot.vadimaz.instantpoker.game.Player;
import com.blogspot.vadimaz.instantpoker.utils.CardUtils;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    private final static String TAG = "cards";
    LinearLayout llTop, llCenter, llBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        llTop = findViewById(R.id.llTop);
        llCenter =  findViewById(R.id.llCenter);
        llBottom = findViewById(R.id.llBottom);

        Game game = (Game) getIntent().getSerializableExtra("game");

        Player player = game.getPlayers().get(0);
        game.addObserver(player);
        final Card playerCard1 = player.getHand().get(0);
        final Card playerCard2 = player.getHand().get(1);
        game.addObserver(playerCard1);
        game.addObserver(playerCard2);

        Player opponent = new Player("Opponent");
        game.addObserver(opponent);
        game.getPlayers().add(opponent);
        game.getDealer().dealCardsToPlayer(opponent);
        final Card opponentCard1 = opponent.getHand().get(0);
        final Card opponentCard2 = opponent.getHand().get(1);
        game.addObserver(opponentCard1);
        game.addObserver(opponentCard2);

        final ArrayList<Card> showDown = game.getDealer().dealShowDown();
        for (Card card: showDown){
            game.addObserver(card);
        }
        game.setActivity(this);

        playerCard1.showFront(llBottom.getChildAt(0).getId());
        playerCard2.showFront(llBottom.getChildAt(1).getId());

        opponentCard1.show(llTop.getChildAt(0).getId());
        opponentCard2.show(llTop.getChildAt(1).getId());

        for (int i = 0; i < showDown.size(); i++) {
            Card card = showDown.get(i);
            card.show(llCenter.getChildAt(i).getId());
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showDown.get(0).flip();
                showDown.get(1).flip();
                showDown.get(2).flip();
            }
        }, 1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showDown.get(3).flip();

            }
        }, 2500);

        showDown.get(4).flip(4000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                opponentCard1.flip();
                opponentCard2.flip();

            }
        }, 5500);

/*
        CardUtils.setCardTextAndColor((ViewGroup) llTop.getChildAt(0), opponent.getHand().get(0));
        CardUtils.setCardTextAndColor((ViewGroup) llTop.getChildAt(1), opponent.getHand().get(1));
        CardUtils.setCardTextAndColor((ViewGroup) llBottom.getChildAt(0), player.getHand().get(0));
        CardUtils.setCardTextAndColor((ViewGroup) llBottom.getChildAt(1), player.getHand().get(1));

        for (int i = 0; i < llCenter.getChildCount(); i++) {
            CardUtils.setCardTextAndColor((ViewGroup) llCenter.getChildAt(i), showDown.get(i));
        }
*/



        ArrayList<Combination> combinations = new ArrayList<>(game.getPlayers().size());
        for (Player p: game.getPlayers()) {
            combinations.add(Combinations.getCombination(p, showDown, p.getHand()));
        }
        Collections.sort(combinations);
        for (Combination c: combinations) {
            Log.d(TAG, c.toString());
        }
        Combinations.getWinner(combinations);
    }
}
