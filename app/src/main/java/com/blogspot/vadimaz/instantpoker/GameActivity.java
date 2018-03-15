package com.blogspot.vadimaz.instantpoker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.vadimaz.instantpoker.game.Card;
import com.blogspot.vadimaz.instantpoker.game.Combination;
import com.blogspot.vadimaz.instantpoker.game.Combinations;
import com.blogspot.vadimaz.instantpoker.game.Game;
import com.blogspot.vadimaz.instantpoker.game.Player;
import com.blogspot.vadimaz.instantpoker.game.Rank;
import com.blogspot.vadimaz.instantpoker.game.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private final static String TAG = "cards";
    TextView playerName, oppoName, oppoBet, playerBet, tvBank;
    LinearLayout llTop, llCenter, llBottom;
    int bet, bank;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        llTop = findViewById(R.id.llTop);
        llCenter =  findViewById(R.id.llCenter);
        llBottom = findViewById(R.id.llBottom);
        playerName = findViewById(R.id.playerName);
        oppoName = findViewById(R.id.oppoName);
        oppoBet = findViewById(R.id.oppoBet);
        playerBet = findViewById(R.id.playerBet);
        tvBank = findViewById(R.id.tvBank);
        llCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toast != null) {
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        final Game game = (Game) getIntent().getSerializableExtra("game");
        bet = getIntent().getIntExtra("bet", 0);
        bank = getIntent().getIntExtra("bank", 100);

        final Player player = game.getPlayers().get(0);
        game.addObserver(player);
        playerName.setText(player.getName());
        playerBet.setText("$"+bet);
        tvBank.setText("Bank: $" + bank);
        final Card playerCard1 = player.getHand().get(0);
        final Card playerCard2 = player.getHand().get(1);
        game.addObserver(playerCard1);
        game.addObserver(playerCard2);

        Player opponent = new Player("Opponent");
        game.addObserver(opponent);
        oppoName.setText(opponent.getName());
        oppoBet.setText("$"+bet);
        game.getPlayers().add(opponent);

        game.getDealer().dealCardsToPlayer(opponent);
        //game.getDealer().dealOwnCardsToPlayer(opponent, new Card(Rank.KING, Suit.CLUBS), new Card(Rank.THREE, Suit.CLUBS)); // for test

        final Card opponentCard1 = opponent.getHand().get(0);
        final Card opponentCard2 = opponent.getHand().get(1);
        game.addObserver(opponentCard1);
        game.addObserver(opponentCard2);

        final ArrayList<Card> showDown = game.getDealer().dealShowDown();
        //final ArrayList<Card> showDown = game.getDealer().dealOwnShowDown(new Card(Rank.TEN, Suit.CLUBS), new Card(Rank.JACK, Suit.CLUBS), new Card(Rank.QUEEN, Suit.CLUBS), new Card(Rank.NINE, Suit.CLUBS), new Card(Rank.NINE, Suit.DIAMONDS));

        for (Card card: showDown){
            game.addObserver(card);
        }
        game.setActivity(this);


        ArrayList<Combination> combinations = new ArrayList<>(game.getPlayers().size());
        for (Player p: game.getPlayers()) {
            combinations.add(Combinations.getCombination(p, showDown, p.getHand()));
        }
        Collections.sort(combinations);
        for (Combination c: combinations) {
            Log.d(TAG, c.toString());
        }
        final ArrayList<Combination> winners = Combinations.getWinner(combinations);

        playerCard1.showFront(llBottom.getChildAt(0));
        playerCard2.showFront(llBottom.getChildAt(1));

        opponentCard1.show(llTop.getChildAt(0));
        opponentCard2.show(llTop.getChildAt(1));

        for (int i = 0; i < showDown.size(); i++) {
            Card card = showDown.get(i);
            card.show(llCenter.getChildAt(i));
        }

        showDown.get(0).flip(1000);
        showDown.get(1).flip(1250);
        showDown.get(2).flip(1500);

        showDown.get(3).flip(2500);

        showDown.get(4).flip(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                opponentCard1.flip();
                opponentCard2.flip();

            }
        }, 5500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Card> winHand = winners.get(0).getHand();
                Player winner = winners.get(0).getPlayer();
                for (int i = 0; i < winHand.size(); i++) {
                    winHand.get(i).highlight();
                }

                if (winners.size() != 1) {

                    toast = Toast.makeText(game.getActivity().getApplicationContext(),"Both players win $"+bet+" with "+ winners.get(0).getRank(), Toast.LENGTH_LONG);
                    Intent intent = new Intent();
                    intent.putExtra("bank", bank+bet);
                    setResult(1, intent);

                } else {
                    toast = Toast.makeText(game.getActivity().getApplicationContext(),winner.getName() + " wins $"+bet*2+" with "+ winners.get(0).getRank(), Toast.LENGTH_LONG);
                    Intent intent = new Intent();
                    if (winner == player) intent.putExtra("bank", bank+bet*2);
                    else intent.putExtra("bank", bank);
                    setResult(1, intent);
                }
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        }, 6000);

    }
}
