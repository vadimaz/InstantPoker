package com.blogspot.vadimaz.instantpoker.game;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vadim on 16.02.2018.
 */

public class Dealer implements Serializable {
    private final static String TAG = "cards";
    private Deck deck;
    private LinkedList<Player> players;

    public Dealer(LinkedList<Player> players) {
        this.deck = new Deck();
        this.players = players;
    }
    public void shuffle(){
        deck.shuffle();
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(LinkedList<Player> players) {
        this.players = players;
    }

    public void dealCardsToPlayer(Player player){
            player.getHand().clear();
            player.getHand().add(deck.drawCard());
            player.getHand().add(deck.drawCard());

            Log.d(TAG, player.getName() + ": " + player.getHand().toString());
    }

    public void dealCardsToPlayers(){
        if (players.size() > 0){
            for (int i = 0; i < 2; i++) {
                for (Player p : players) {
                    p.getHand().add(i, deck.drawCard());
                }
            }
        }
        for (Player p : players){
            Log.d(TAG, p.getName() + ": " + p.getHand().toString());
        }
    }

    public ArrayList<Card> dealShowDown(){
        return deck.drawShowDown();
    }

    public ArrayList<Card> dealOwnShowDown(Card... cards){
        if (cards.length != 5) return dealShowDown();
        else return deck.drawShowDown(cards);

    }
    public void dealOwnCardsToPlayer(Player player, Card card1, Card card2){
        player.getHand().clear();
        player.getHand().add(deck.getSpecificCard(card1));
        player.getHand().add(deck.getSpecificCard(card2));
    }
}
