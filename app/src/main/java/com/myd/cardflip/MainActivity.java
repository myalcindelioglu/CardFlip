package com.myd.cardflip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    boolean showingBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.container);
        frameLayout.setOnClickListener(this);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment())
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.container:
                flipCard();
                break;
            default:
                Log.e(TAG, "Unhandled onclick event for view id: " + v.getId());
                break;
        }
    }

    private void flipCard() {
        if (showingBack) {
            showingBack = false;
            getFragmentManager().popBackStack();
        } else {
            showingBack = true;
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_enter,
                            R.animator.card_flip_right_exit,
                            R.animator.card_flip_left_enter,
                            R.animator.card_flip_left_exit)
                    .addToBackStack(null)
                    .replace(R.id.container, new CardBackFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().popBackStackImmediate()) {
            showingBack = false;
        } else {
            super.onBackPressed();
        }
    }
}
