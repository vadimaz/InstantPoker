package com.blogspot.vadimaz.instantpoker.game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Vadim on 16.02.2018.
 */

public class Player implements Serializable, Observer {

    private final String name;
    private Hand hand = new Hand();
    private int bank;
    private transient SharedPreferences sharedPreferences;
    private transient Activity activity;

    public int getBank() {
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        bank = sharedPreferences.getInt(name, 100);
        return bank;
    }

    public Player(String name) {
        this.name = name;
    }

    public void setBank(int bank) {
        if (activity == null) return;
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt(name, bank);
        ed.apply();
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.activity = (Activity) arg;
    }
}
