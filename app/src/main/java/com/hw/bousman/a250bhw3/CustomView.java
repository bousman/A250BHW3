package com.hw.bousman.a250bhw3;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class CustomView extends View {

    private Paint mPaint;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
    }

    float xCircle = 0;
    float yCircle = 5;

    float xMax;
    float yMax;

    float xIncrement = 1.1F;

    int xCircleSize = 10;
    int yCircleSize = 10;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff101010);

        canvas.drawRect(0, 0, canvas.getWidth()-1, canvas.getHeight()-1, mPaint);
        xMax = canvas.getWidth() - xCircleSize;
        yMax = canvas.getHeight() - yCircleSize;

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffff0000);

        int xStart = (int)xCircle;
        int yStart = (int)yCircle;
        canvas.drawOval(xStart, yStart, xStart+xCircleSize, yStart+yCircleSize, mPaint);

        moveCircle();
        checkEdge();
    }

    private void moveCircle()
    {
        xCircle += xIncrement;
        invalidate();
    }

    private void checkEdge()
    {
        if ( xCircle >= xMax )
        {
            xIncrement *= -1.0F;
            xCircle = xMax;
        }
        else if ( xCircle <= 0.F )
        {
            xIncrement *= -1.0F;
            xCircle = 0.F;
        }
    }
}