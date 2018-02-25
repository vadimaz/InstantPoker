package com.blogspot.vadimaz.instantpoker.game;

import java.io.Serializable;

/**
 * Created by Vadim on 02.02.2018.
 */

public enum Suit implements Serializable {
    DIAMONDS("♦"),
    CLUBS("♣"),
    HEARTS("♥"),
    SPADES("♠");
    private String symbol;
    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
