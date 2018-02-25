package com.blogspot.vadimaz.instantpoker.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Vadim on 08.02.2018.
 */

public enum Combinations {
    ROYAL_FLUSH(9),
    STRAIGHT_FLUSH(8),
    FOUR_OF_KIND(7),
    FULL_HOUSE(6),
    FLUSH(5),
    STRAIGHT(4),
    THREE_OF_KIND(3),
    TWO_PAIRS(2),
    ONE_PAIR(1),
    HIGH_HAND(0);

    private final int rank;
    final static String TAG = "cards";

    Combinations(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public static Combination getCombination(Player player, ArrayList<Card> showDown, Hand playersHand){
        ArrayList<Card> hand = new ArrayList<>(7);
        hand.addAll(showDown);
        hand.addAll(playersHand);
        Collections.sort(hand);
        ArrayList<Card> winHand;
        if ((winHand = checkStraightFlush(hand, true)) != null) return new Combination(player, winHand, ROYAL_FLUSH);
        if ((winHand = checkStraightFlush(hand, false)) != null) return new Combination(player, winHand, STRAIGHT_FLUSH);
        if ((winHand = checkFourOfKind(hand)) != null) return new Combination(player, winHand, FOUR_OF_KIND);
        if ((winHand = checkFullHouse(hand)) != null) return new Combination(player, winHand, FULL_HOUSE);
        if ((winHand = checkFlush(hand)) != null) return new Combination(player, winHand, FLUSH);
        if ((winHand = checkStraight(hand)) != null) return new Combination(player, winHand, STRAIGHT);
        if ((winHand = checkThreeOfKind(hand)) != null) return new Combination(player, winHand, THREE_OF_KIND);
        if ((winHand = checkTwoPairs(hand)) != null) return new Combination(player, winHand, TWO_PAIRS);
        if ((winHand = checkOnePair(hand)) != null) return new Combination(player, winHand, ONE_PAIR);
        return new Combination(player, checkHighHand(hand), HIGH_HAND);
    }

    public static ArrayList<Card> checkFlush(ArrayList<Card> hand){
        ArrayList<Card> result = new ArrayList<>(5);
        for (int i = 0; i < hand.size(); i++) {
            result.clear();
            Card card = hand.get(i);
            result.add(card);
            for (int j = 0; j < hand.size(); j++) {
                if (card == hand.get(j)) continue;
                if (card.getSuit() == hand.get(j).getSuit()) {
                    result.add(hand.get(j));
                }
            }
            if (result.size() == 5) {
                Log.d(TAG, "checkFlush: " + result);
                return result;
            }
        }
        //Log.d(TAG, "checkFlush: null");
        return null;
    }

    public static ArrayList<Card> checkFourOfKind(ArrayList<Card> hand){
        ArrayList<Card> handCopy = new ArrayList<>(hand);
        ArrayList<Card> three = getThreeOfKindFromHand(handCopy);
        if (three != null) {
            handCopy.removeAll(three);
            Card fourth;
            for (int i = 0; i < handCopy.size(); i++) {
                if ((fourth = handCopy.get(i)).getRank() == three.get(0).getRank()) {
                    handCopy.remove(fourth);
                    ArrayList<Card> result = new ArrayList<>(5);
                    result.addAll(three);
                    result.add(fourth);
                    result.add(handCopy.get(0));
                    Log.d(TAG, "checkFourOfKind: " + result);
                    return result;
                }
            }
        }

        //Log.d(TAG, "checkFourOfKind: null");
        return null;
    }

    public static ArrayList<Card> checkFullHouse(ArrayList<Card> hand) {
        ArrayList<Card> handCopy = new ArrayList<>(hand);
        ArrayList<Card> three = getThreeOfKindFromHand(handCopy);
        if (three == null) {
            //Log.d(TAG, "checkFullHouse: null");
            return null;
        }
        handCopy.removeAll(three);
        ArrayList<Card> pair = getOnePairFromHand(handCopy);
        if (pair == null) {
            //Log.d(TAG, "checkFullHouse: null");
            return null;
        }
        ArrayList<Card> result = new ArrayList<>(5);
        result.addAll(three);
        result.addAll(pair);
        Log.d(TAG, "checkFullHouse: " + result);
        return result;
    }

    public static ArrayList<Card> checkStraight(ArrayList<Card> hand){
        ArrayList<Card> handCopy;
        for (int i = 0; i < 4; i++) {
            ArrayList<Card> result = new ArrayList<>(5);
            handCopy = new ArrayList<>(hand);
            int count = 1;
            Card card = handCopy.get(i);
            result.add(card);
            handCopy.remove(card);
            for (int j = 0; j < handCopy.size(); ) {
                Card lowerCard = getLowerCard(card, handCopy);
                if (lowerCard != null) {
                    result.add(lowerCard);
                    card = lowerCard;
                    handCopy.remove(card);
                    count++;
                    if (count == 4 && card.getWeight() == 2) {
                        lowerCard = getAce(handCopy);
                        if (lowerCard != null) {
                            result.add(lowerCard);
                            card = lowerCard;
                            handCopy.remove(card);
                            count++;
                        }
                    }
                    if (count == 5) {
                        Log.d(TAG, "checkStraight: " + result);
                        return result;
                    }
                }
                else {
                    j++;
                }
            }

        }
        //Log.d(TAG, "checkStraight: null");
        return null;
    }

    public static ArrayList<Card> checkStraightFlush(ArrayList<Card> hand, boolean shouldCheckRoyalFlush) {
        ArrayList<Card> handCopy;
        for (int i = 0; i < 4; i++) {
            ArrayList<Card> result = new ArrayList<>(5);
            handCopy = new ArrayList<>(hand);
            int count = 1;
            Card card = handCopy.get(i);
            result.add(card);
            handCopy.remove(card);
            for (int j = 0; j < handCopy.size(); ) {
                Card lowerCard = getLowerCard(card, handCopy);
                if (lowerCard != null && lowerCard.getSuit() == card.getSuit()) {
                    result.add(lowerCard);
                    card = lowerCard;
                    handCopy.remove(card);
                    count++;
                    if (count == 4 && card.getWeight() == 2) {
                        lowerCard = getAce(handCopy);
                        if (lowerCard != null && lowerCard.getSuit() == card.getSuit()) {
                            result.add(lowerCard);
                            card = lowerCard;
                            handCopy.remove(card);
                            count++;
                        }
                    }
                    if (count == 5) {
                        if (shouldCheckRoyalFlush && result.get(0).getRank() != Rank.ACE) {
                            //Log.d(TAG, "checkRoyalFlush: null");
                            return null;
                        }
                        Log.d(TAG, "checkStraightFlushOrRoyal: " + result);
                        return result;
                    }
                }
                else {
                    j++;
                }
            }

        }
        //Log.d(TAG, "checkStraightFlush: null");
        return null;
    }

    public static ArrayList<Card> checkThreeOfKind(ArrayList<Card> hand){
        ArrayList<Card> handCopy = new ArrayList<>(hand);
        ArrayList<Card> three = getThreeOfKindFromHand(handCopy);
        if (three == null) {
            //Log.d(TAG, "checkThreeOfKind: null");
            return null;
        }
        handCopy.removeAll(three);
        ArrayList<Card> result = new ArrayList<>(5);
        result.addAll(three);
        for (int i = 0; i < 2; i++) {
            result.add(handCopy.get(i));
        }
        Log.d(TAG, "checkThreeOfKind: "+ result);
        return result;

    }

    public static ArrayList<Card> getThreeOfKindFromHand(ArrayList<Card> hand) {
        ArrayList<Card> result = new ArrayList<>(3);
        for (int i = 0; i < hand.size(); i++) {
            Card card1 = hand.get(i);
            for (int j = 0; j < hand.size(); j++) {
                Card card2 = hand.get(j);
                if (card1 == card2) continue;
                if (card1.getRank().getWeight() == card2.getRank().getWeight()){
                    for (int k = 0; k < hand.size(); k++) {
                        Card card3 = hand.get(k);
                        if (card3 == card2 | card3 == card1) continue;
                        if (card2.getRank().getWeight() == card3.getRank().getWeight()) {
                            result.add(card1);
                            result.add(card2);
                            result.add(card3);
                            return result;
                        }
                    }
                }
            }

        }
        return null;
    }

    public static ArrayList<Card> checkTwoPairs(ArrayList<Card> hand){
        ArrayList<Card> handCopy = new ArrayList<>(hand);
        ArrayList<Card> pairOne = getOnePairFromHand(handCopy);
        if (pairOne == null) {
            //Log.d(TAG, "checkTwoPairs: null");
            return null;
        }
        handCopy.removeAll(pairOne);

        ArrayList<Card> pairTwo = getOnePairFromHand(handCopy);
        if (pairTwo == null) {
            //Log.d(TAG, "checkTwoPairs: null");
            return null;
        }
        handCopy.removeAll(pairTwo);

        ArrayList<Card> result = new ArrayList<>(5);
        result.addAll(pairOne);
        result.addAll(pairTwo);
        result.add(handCopy.get(0));

        Log.d(TAG, "checkTwoPairs: " + result);
        return result;


    }

    public static ArrayList<Card> checkOnePair(ArrayList<Card> hand){
        ArrayList<Card> handCopy = new ArrayList<>(hand);
        ArrayList<Card> pair = getOnePairFromHand(handCopy);
        if (pair == null) {
            //Log.d(TAG, "checkOnePair: null");
            return null;
        }
        ArrayList<Card> result = new ArrayList<>(5);
        result.addAll(pair);
        handCopy.removeAll(pair);
        for (int i = 0; i < 3; i++) {
            result.add(handCopy.get(i));
        }
        Log.d(TAG, "checkOnePair: " + result);
        return result;

    }

    public static ArrayList<Card> getOnePairFromHand(ArrayList<Card> hand){
        ArrayList<Card> result = new ArrayList<>(2);
        for (int i = 0; i < hand.size()-1; i++) {
            Card card = hand.get(i);
            for (int j = i+1; j < hand.size(); j++) {
                if (card.getRank().getWeight() == hand.get(j).getRank().getWeight()) {
                    result.add(card);
                    result.add(hand.get(j));
                    return result;
                }
            }
        }
        return null;
    }

    public static ArrayList<Card> checkHighHand(ArrayList<Card> hand){
        ArrayList<Card> result = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            result.add(hand.get(i));
        }
        Log.d(TAG, "checkHighHand: "+ result);
        return result;
    }

    public static Card getAce(ArrayList<Card> hand){
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRank() == Rank.ACE) {
                return hand.get(i);
            }
        }
        return null;
    }

    public static Card getLowerCard(Card card, ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getWeight() == card.getWeight()/2) {
                return hand.get(i);
            }

        }
        return null;
    }

    public static void getWinner(ArrayList<Combination> combinations){
        ArrayList<Combination> winnerCombinations = new ArrayList<>(combinations.size());
        Combination winner = Collections.max(combinations);
        winnerCombinations.add(winner);
        for (Combination c: combinations){
            if (c != winner && c.equals(winner)) winnerCombinations.add(c);
        }
        if (winnerCombinations.size() == 1) {
            Log.d(TAG, "winner: " + winnerCombinations.get(0).getPlayer().getName());
        } else {
            StringBuilder sb = new StringBuilder();
            for (Combination c : winnerCombinations){
                sb.append(c.getPlayer().getName()).append(" ");
            }
            Log.d(TAG, "winners: "+ sb.toString());
        }
    }

}
