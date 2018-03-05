package com.blogspot.vadimaz.instantpoker.game;

import android.app.Activity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Vadim on 19.02.2018.
 */

public class Game extends Observable implements Serializable {

    private Dealer dealer;
    private LinkedList<Player> players;
    private transient Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
        setChanged();
        notifyObservers(activity);
    }

    public Game() {
        this.players = new LinkedList<>();
        this.dealer = new Dealer(players);
    }

    public Dealer getDealer() {
        return dealer;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
