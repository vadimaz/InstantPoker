package com.blogspot.vadimaz.instantpoker.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vadim on 08.02.2018.
 */

public class Deck extends LinkedList<Card> {

    private final static String TAG = "cards";

    public Deck() {
        shuffle();
    }
    public Card drawCard() {
        return this.poll();
    }

    public ArrayList<Card> drawShowDown(){
        ArrayList<Card> showDown = new ArrayList<>(5);
        this.drawCard();
        for (int i = 0; i < 3; i++) {
            showDown.add(drawCard());
        }
        this.drawCard();
        showDown.add(drawCard());
        this.drawCard();
        showDown.add(drawCard());
        Log.d(TAG, "ShowDown: " + showDown);
        return showDown;
    }

    public ArrayList<Card> drawShowDown(Card... cards){
        ArrayList<Card> showDown = new ArrayList<>(5);
        showDown.addAll(Arrays.asList(cards));
        return showDown;
    }

    public Card getSpecificCard(Card card){
        for (Card c : this) {
            if (c.equals(card)) {
                this.remove(c);
                return c;
            }
        }
        return null;
    }

    public void shuffle(){
        List<Card> deck = new ArrayList<>(52);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deck.add(new Card(Rank.values()[j], Suit.values()[i]));
            }
        }
        Collections.shuffle(deck);

        this.clear();
        this.addAll(deck);
    }
}
