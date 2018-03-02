package com.blogspot.vadimaz.testapp;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        tv1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Animator animation = AnimatorInflater.loadAnimator(this, R.animator.animation1);
        animation.setTarget(tv1);
        animation.start();
    }
}
