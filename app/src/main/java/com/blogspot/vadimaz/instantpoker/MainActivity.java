
package com.blogspot.vadimaz.instantpoker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blogspot.vadimaz.instantpoker.game.Card;
import com.blogspot.vadimaz.instantpoker.game.Game;
import com.blogspot.vadimaz.instantpoker.game.Player;
import com.blogspot.vadimaz.instantpoker.utils.CardUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static String TAG = "cards";
    Game game;
    LinearLayout llMainCard1, llMainCard2;
    Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llMainCard1 = findViewById(R.id.llMainCard1);
        llMainCard2 = findViewById(R.id.llMainCard2);
        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);

        startGame();

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("game", game);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        startGame();

    }

    public void startGame(){
        game = new Game();
        Player player = new Player("Vadim");
        game.getPlayers().add(player);
        game.getDealer().dealCardsToPlayer(player);

        Card mainCard1 = player.getHand().get(0);
        Card mainCard2 = player.getHand().get(1);
        CardUtils.setCardTextAndColor(llMainCard1, mainCard1);
        CardUtils.setCardTextAndColor(llMainCard2, mainCard2);


    }
}
