package com.blogspot.vadimaz.instantpoker;

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
        Player opponent = new Player("Janna");
        game.getPlayers().add(opponent);

        game.getDealer().dealCardsToPlayer(opponent);

        ArrayList<Card> showDown = game.getDealer().dealShowDown();

        ArrayList<Combination> combinations = new ArrayList<>(game.getPlayers().size());
        for (Player p: game.getPlayers()) {
            combinations.add(Combinations.getCombination(p, showDown, p.getHand()));
        }
        Collections.sort(combinations);
        for (Combination c: combinations) {
            Log.d(TAG, c.toString());
        }
        Combinations.getWinner(combinations);

        CardUtils.setCardTextAndColor((ViewGroup) llTop.getChildAt(0), opponent.getHand().get(0));
        CardUtils.setCardTextAndColor((ViewGroup) llTop.getChildAt(1), opponent.getHand().get(1));
        CardUtils.setCardTextAndColor((ViewGroup) llBottom.getChildAt(0), player.getHand().get(0));
        CardUtils.setCardTextAndColor((ViewGroup) llBottom.getChildAt(1), player.getHand().get(1));

        for (int i = 0; i < llCenter.getChildCount(); i++) {
            CardUtils.setCardTextAndColor((ViewGroup) llCenter.getChildAt(i), showDown.get(i));
        }
    }
}
