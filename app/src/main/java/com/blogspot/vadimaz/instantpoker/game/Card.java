package com.blogspot.vadimaz.instantpoker.game;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.blogspot.vadimaz.instantpoker.FragmentCardBack;
import com.blogspot.vadimaz.instantpoker.FragmentCardFront;
import com.blogspot.vadimaz.instantpoker.R;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Vadim on 02.02.2018.
 */

public class Card implements  Comparable<Card>, Serializable, Observer {
    private final Rank rank;
    private final Suit suit;
    private transient View container;
    private transient FragmentActivity activity;

    public void show(View container){
        this.container = container;
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(container.getId(), new FragmentCardBack())
                .commitAllowingStateLoss();
    }

    public void flip(){
        FragmentCardFront front = new FragmentCardFront();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_enter,
                        R.animator.card_flip_right_exit,
                        R.animator.card_flip_left_enter,
                        R.animator.card_flip_left_exit)
                .replace(container.getId(), front)
                .commitAllowingStateLoss();
        front.setCard(this);
    }

    public void flip(int delay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flip();
            }
        }, delay);
    }

    public void showFront(View container){
        this.container = container;
        FragmentCardFront front = new FragmentCardFront();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(container.getId(), front)
                .commitAllowingStateLoss();
        front.setCard(this);
    }

    public void highlight(){
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.highlite_card);
        container.startAnimation(animation);
    }

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

    @Override
    public void update(Observable o, Object arg) {
        this.activity = (FragmentActivity) arg;
    }

}
