
package com.blogspot.vadimaz.instantpoker;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.vadimaz.instantpoker.game.Card;
import com.blogspot.vadimaz.instantpoker.game.Game;
import com.blogspot.vadimaz.instantpoker.game.Player;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String TAG = "cards";
    Game game;
    int bet;
    Player player;
    LinearLayout linearLayout;
    TextView tvBank, tvBet;
    Button btnPlay, btnBet1, btnBet5, btnBet10, btnBetMinus, btnBetPlus;
    SeekBar seekBetBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linearLayout);
        btnPlay = findViewById(R.id.btnPlay);
        btnBet1 = findViewById(R.id.btnBet1);
        btnBet5 = findViewById(R.id.btnBet5);
        btnBet10 = findViewById(R.id.btnBet10);
        btnBetMinus = findViewById(R.id.btnBetMinus);
        btnBetPlus = findViewById(R.id.btnBetPlus);
        seekBetBar = findViewById(R.id.seekBetBar);
        tvBank = findViewById(R.id.tvBank);
        tvBet = findViewById(R.id.tvBet);

        btnPlay.setOnClickListener(this);
        btnBet1.setOnClickListener(this);
        btnBet5.setOnClickListener(this);
        btnBet10.setOnClickListener(this);
        btnBetPlus.setOnClickListener(this);
        btnBetMinus.setOnClickListener(this);

        seekBetBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvBet.setText("Bet: $"+progress);
                bet = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tvBet.setText("Bet: $"+ seekBar.getProgress());
                bet = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        startGame();

    }



    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btnPlay:
                if (bet > player.getBank()) {
                    Toast.makeText(this, "Not enough money!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bet == 0) {
                    Toast.makeText(this, "Choose your bet!", Toast.LENGTH_SHORT).show();
                    return;
                }
                player.setBank(player.getBank() - bet);
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("game", game);
                intent.putExtra("bet", bet);
                intent.putExtra("bank", player.getBank());
                startActivityForResult(intent, 1);

                break;
            case R.id.btnBet1:
                bet = 5;
                break;
            case R.id.btnBet5:
                bet = 10;
                break;
            case R.id.btnBet10:
                bet = 50;
                break;
            case R.id.btnBetMinus:
                //seekBetBar.setProgress(seekBetBar.getProgress() - 1);
                bet = seekBetBar.getProgress()-1;
                if (bet < 0) bet = 0;
                break;
            case R.id.btnBetPlus:
                //seekBetBar.setProgress(seekBetBar.getProgress() + 1);
                bet = seekBetBar.getProgress()+1;
                if (bet > player.getBank()) bet = player.getBank();
                break;
        }
        tvBet.setText("Bet: $"+bet);
        seekBetBar.setProgress(bet);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        player.setBank(data.getIntExtra("bank", 0));
        bet = 0;
        seekBetBar.setProgress(bet);
        startGame();

    }

    public void startGame(){
        game = new Game();

        player = new Player("Player");
        game.addObserver(player);
        game.getPlayers().add(player);
        game.getDealer().dealCardsToPlayer(player);
        final Card playerCard1 = player.getHand().get(0);
        final Card playerCard2 = player.getHand().get(1);
        game.addObserver(playerCard1);
        game.addObserver(playerCard2);

        game.setActivity(this);
        if (player.getBank() <= 0) player.setBank(100);
        tvBank.setText("Bank: $" + player.getBank());
        seekBetBar.setMax(player.getBank());
        //btnBet1.setEnabled(false);

        playerCard1.show(linearLayout.getChildAt(0));
        playerCard2.show(linearLayout.getChildAt(1));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playerCard1.flip();
                playerCard2.flip();
            }
        }, 500);

        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.highlite_card);
                linearLayout.getChildAt(0).startAnimation(animation);
                linearLayout.getChildAt(1).startAnimation(animation);
            }
        }, 2500);*/
    }

}
