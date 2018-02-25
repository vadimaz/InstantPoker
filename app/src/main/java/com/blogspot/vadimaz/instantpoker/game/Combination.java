package com.blogspot.vadimaz.instantpoker.game;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

/**
 * Created by Vadim on 08.02.2018.
 */

public class Combination implements Comparable<Combination> {
    private final Player player;
    private final List<Card> hand;
    private final Combinations rank;
    private final int weight;

    final static String TAG = "cards";

    public Combination(Player player, List<Card> hand, Combinations rank) {
        this.player = player;
        this.hand = hand;
        this.rank = rank;
        this.weight = calculateWeight();
    }

    public Player getPlayer() {
        return player;
    }

    public Combinations getRank() {
        return rank;
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getWeight() {
        return weight;
    }


    @Override
    public int compareTo(@NonNull Combination o) {
        if (this.rank.getRank() != o.rank.getRank()) return this.rank.getRank() - o.rank.getRank();
        return this.weight - o.weight;
    }

    private int calculateWeight(){
        int result = 0;
        int start = 0;
        if (rank == Combinations.ONE_PAIR || rank == Combinations.TWO_PAIRS || rank == Combinations.THREE_OF_KIND || rank == Combinations.FULL_HOUSE || rank == Combinations.FOUR_OF_KIND) {
            result += (hand.get(0).getWeight() + hand.get(1).getWeight() * 8192);
            start = 2;
        }
        if (rank == Combinations.TWO_PAIRS){
            result += (hand.get(2).getWeight() + hand.get(3).getWeight() * 8192);
            start = 4;
        }
        for (int i = start; i < 5; i++) {
            if (i == 4 && hand.get(i).getRank() == Rank.ACE) {
                if (rank == Combinations.STRAIGHT_FLUSH || rank == Combinations.STRAIGHT) {
                    return result;
                }
            }
            result += hand.get(i).getWeight();
        }
        return result;
    }

    public void getLog(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(hand.get(i));
            stringBuilder.append(" ");
        }
        Log.d(TAG, stringBuilder.toString());
    }

    @Override
    public String toString() {
        return "Combination{" +
                "player=" + player.getName() +
                ", hand=" + hand +
                ", rank=" + rank +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Combination){
            return this.rank == ((Combination) obj).getRank() && this.weight == ((Combination) obj).getWeight();
        } else return super.equals(obj);
    }
}
