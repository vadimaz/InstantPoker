package com.blogspot.vadimaz.testapp;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout parentLayout, btnsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentLayout = findViewById(R.id.parentLayout);
        btnsLayout = findViewById(R.id.btnsLayout);
        for (int i = 0; i < btnsLayout.getChildCount(); i++) {
            btnsLayout.getChildAt(i).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        View view;
        switch (v.getId()) {
            case R.id.btn1:
                view = parentLayout.getChildAt(0);
                view.setVisibility(view.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.btn2:
                view = parentLayout.getChildAt(1);
                view.setVisibility(view.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.btn3:
                view = parentLayout.getChildAt(2);
                view.setVisibility(view.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.btn4:
                view = parentLayout.getChildAt(3);
                view.setVisibility(view.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.btn5:
                view = parentLayout.getChildAt(4);
                view.setVisibility(view.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                break;
        }
    }
}
