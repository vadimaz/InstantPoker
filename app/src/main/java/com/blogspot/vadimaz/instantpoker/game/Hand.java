package com.blogspot.vadimaz.instantpoker.game;

import java.util.ArrayList;

/**
 * Created by Vadim on 14.02.2018.
 */

public class Hand extends ArrayList<Card> {

    public Hand() {
        super(2);
    }

    @Override
    public String toString() {
        return this.get(0).toString() + " " + this.get(1).toString();
    }
}
