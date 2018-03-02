
package com.blogspot.vadimaz.instantpoker;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.blogspot.vadimaz.instantpoker.game.Card;
import com.blogspot.vadimaz.instantpoker.game.Game;
import com.blogspot.vadimaz.instantpoker.game.Player;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static String TAG = "cards";
    public static FragmentManager fragmentManager;
    Game game;
    LinearLayout frMainCard1, frMainCard2;
    FragmentCardFront cardFront1, cardFront2;
    Button btnPlay, btnBet1, btnBet5, btnBet10;
    SeekBar seekBetBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        frMainCard1 = findViewById(R.id.frMainCard1);
        frMainCard2 = findViewById(R.id.frMainCard2);
        btnPlay = findViewById(R.id.btnPlay);
        btnBet1 = findViewById(R.id.btnBet1);
        btnBet5 = findViewById(R.id.btnBet5);
        btnBet10 = findViewById(R.id.btnBet10);
        seekBetBar = findViewById(R.id.seekBetBar);

        startGame();
        final Card playerCard1 = game.getPlayers().get(0).getHand().get(0);
        final Card playerCard2 = game.getPlayers().get(0).getHand().get(1);
        playerCard1.show(frMainCard1.getId());
        playerCard2.show(frMainCard2.getId());

        btnPlay.setOnClickListener(this);
        btnBet1.setOnClickListener(this);
        btnBet5.setOnClickListener(this);
        btnBet10.setOnClickListener(this);

        seekBetBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                btnPlay.setText("Bet: $"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                btnPlay.setText("Bet: $"+ seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playerCard1.flip();
                playerCard2.flip();
            }
        }, 1000);


    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnPlay:
                /*
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("game", game);
                startActivityForResult(intent, 1);
                */
                break;
            case R.id.btnBet1:
                btnPlay.setText("Bet: $1");
                seekBetBar.setProgress(1);
                break;
            case R.id.btnBet5:
                btnPlay.setText("Bet: $5");
                seekBetBar.setProgress(5);
                break;
            case R.id.btnBet10:
                btnPlay.setText("Bet: $10");
                seekBetBar.setProgress(10);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //startGame();

    }

    public void startGame(){
        game = new Game();
        Player player = new Player("Vadim");
        game.getPlayers().add(player);
        game.getDealer().dealCardsToPlayer(player);

        Card mainCard1 = player.getHand().get(0);
        Card mainCard2 = player.getHand().get(1);


    }

}
