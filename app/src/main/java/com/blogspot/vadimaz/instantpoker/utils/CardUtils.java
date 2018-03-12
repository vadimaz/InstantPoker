package com.blogspot.vadimaz.instantpoker.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.vadimaz.instantpoker.MainActivity;
import com.blogspot.vadimaz.instantpoker.game.Card;
import com.blogspot.vadimaz.instantpoker.game.Suit;

/**
 * Created by Vadim on 21.02.2018.
 */

public class CardUtils {

    public static void setCardTextAndColor(ViewGroup ll, Card card, double textSize){
        for (int i = 0; i < ll.getChildCount(); i++) {
            TextView tv = (TextView) ll.getChildAt(i);
            tv.setText(card.toString());
            tv.setTextColor(card.getSuit() == Suit.HEARTS || card.getSuit() == Suit.DIAMONDS? Color.RED : Color.BLACK);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (float) textSize);
        }
    }

    public static void setCardTextAndColor(ViewGroup ll, Card card, ViewGroup container){
        int textSize;
        int height = (int) (container.getLayoutParams().height / Resources.getSystem().getDisplayMetrics().density);
        //Log.d(MainActivity.TAG, "setCardTextAndColor: " + height);
        switch (height){
            case 160:
                textSize = 45;
                break;
            case 130:
                textSize = 35;
                break;
            default:
                textSize = 20;
                break;
        }
        for (int i = 0; i < ll.getChildCount(); i++) {
            TextView tv = (TextView) ll.getChildAt(i);
            tv.setText(card.toString());
            tv.setTextColor(card.getSuit() == Suit.HEARTS || card.getSuit() == Suit.DIAMONDS? Color.RED : Color.BLACK);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        }
    }
}
