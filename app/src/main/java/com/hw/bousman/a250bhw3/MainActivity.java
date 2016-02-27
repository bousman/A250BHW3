package com.hw.bousman.a250bhw3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View myView = findViewById(R.id.uxView);

        myView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d("view touch", String.format("x=%f y=%f", event.getX(), event.getY()));
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    CustomView vv = (CustomView) v;
                    if (vv != null) {
                        vv.setBallLocation(event.getX(), event.getY());
                    }
                    return true;
                }
                return false;
            }
        });
    }
}