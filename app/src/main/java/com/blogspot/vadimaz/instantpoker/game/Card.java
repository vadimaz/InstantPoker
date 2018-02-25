package com.blogspot.vadimaz.instantpoker.game;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Vadim on 02.02.2018.
 */

public class Card implements Comparable<Card>, Serializable {
    private final Rank rank;
    private final Suit suit;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            return this.getRank() == ((Card) obj).getRank() & this.getSuit() == ((Card) obj).getSuit();
        } else return super.equals(obj);

    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getWeight(){
        return rank.getWeight();
    }

    @Override
    public int compareTo(@NonNull Card o) {
        return o.getWeight() - this.getWeight();
    }

    /*
    public static Card createCardByName(String rankName, String suitName) {
        Rank r = null;
        Suit s = null;
        switch (rankName.toLowerCase()) {
            case "two":
                r = Rank.TWO;
                break;
            case "three":
                r = Rank.THREE;
                break;
            case "four":
                r = Rank.FOUR;
                break;
            case "five":
                r = Rank.FIVE;
                break;
            case "six":
                r = Rank.SIX;
                break;
            case "seven":
                r = Rank.SEVEN;
                break;
            case "eight":
                r = Rank.EIGHT;
                break;
            case "nine":
                r = Rank.NINE;
                break;
            case "ten":
                r = Rank.TEN;
                break;
            case "jack":
                r = Rank.JACK;
                break;
            case "queen":
                r = Rank.QUEEN;
                break;
            case "king":
                r = Rank.KING;
                break;
            case "ace":
                r = Rank.ACE;
                break;

        }

        switch (suitName.toLowerCase()){
            case "diamonds":
                s = Suit.DIAMONDS;
                break;
            case "clubs":
                s = Suit.CLUBS;
                break;
            case "hearts":
                s = Suit.HEARTS;
                break;
            case "spades":
                s = Suit.SPADES;
                break;
        }
        if (r != null && s != null) {
            return new Card(r,s);
        } else {
            return null;
        }
    }
    */
}
