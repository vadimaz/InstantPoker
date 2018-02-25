package com.blogspot.vadimaz.instantpoker.game;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vadim on 19.02.2018.
 */

public class Game implements Serializable {

    private Dealer dealer;
    private LinkedList<Player> players;

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
