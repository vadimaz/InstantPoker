package com.blogspot.vadimaz.instantpoker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.vadimaz.instantpoker.game.Card;
import com.blogspot.vadimaz.instantpoker.utils.CardUtils;

public class FragmentCardFront extends Fragment {

    Card card;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.card_front_layout, container, false);
        LinearLayout ll = (LinearLayout) view;
        CardUtils.setCardTextAndColor(ll, card);
        return view;
    }

    public void setCard(Card card){
        this.card = card;
    }

}
