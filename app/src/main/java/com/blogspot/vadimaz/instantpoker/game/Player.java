package com.blogspot.vadimaz.instantpoker.game;

import java.io.Serializable;

/**
 * Created by Vadim on 16.02.2018.
 */

public class Player implements Serializable {

    private final String name;
    private Hand hand = new Hand();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }
}
