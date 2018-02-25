package com.blogspot.vadimaz.instantpoker.game;

import java.io.Serializable;

/**
 * Created by Vadim on 02.02.2018.
 */

public enum Rank implements Serializable {
    TWO("2", 2),
    THREE("3", 4),
    FOUR("4", 8),
    FIVE("5", 16),
    SIX("6", 32),
    SEVEN("7", 64),
    EIGHT("8", 128),
    NINE("9", 256),
    TEN("10", 512),
    JACK("J", 1024),
    QUEEN("Q", 2048),
    KING("K", 4096),
    ACE("A", 8192);
    private final int weight;
    private final String symbol;

    Rank( String symbol, int weight) {
        this.weight = weight;
        this.symbol = symbol;
    }

    public int getWeight() {
        return weight;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
