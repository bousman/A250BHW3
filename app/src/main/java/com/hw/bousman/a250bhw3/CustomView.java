package com.hw.bousman.a250bhw3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Homework 3 for UoW Android 250 class, Spring 2016.  A custom view
 * that animates a circle and moves it around the view.  It picks a random
 * speed and direction and has a public function to move the ball to a new
 * location and pick a new random speed/direction.
 *
 * @author  Brian Bousman
 * @since   2016-02-27
 */
public class CustomView extends View {

    private Paint mPaint;

    boolean mInvalidate = false;   // set true when need to invalidate view and redraw

    float xCircle = 0;   // current left-most location of circle
    float yCircle = 0;   // current top-most location of circle

    float xMax;          // maximum xCircle allowed
    float yMax;          // maximum yCircle allowed

    float mSpeed = 5.F;
    float mCurXspeed = mSpeed;  // current speed (always positive)
    float mCurYspeed = mSpeed;
    float xIncrement;    // speed turned into increment value (positive or negative)
    float yIncrement;

    int xCircleSize = 15;
    int yCircleSize = 15;


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();

        xCircle = (float)(Math.random() * xMax);
        yCircle = (float)(Math.random() * yMax);
        randomSpeedDirection();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Log.d("CustomView", String.format("xInc=%f yInc=%f", xIncrement, yIncrement));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff101010);

        canvas.drawRect(0, 0, canvas.getWidth()-1, canvas.getHeight()-1, mPaint);
        xMax = canvas.getWidth() - xCircleSize;
        yMax = canvas.getHeight() - yCircleSize;

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffff0000);

        int xStart = (int)(xCircle);
        int yStart = (int)(yCircle);
        canvas.drawOval(xStart, yStart, xStart + xCircleSize, yStart + yCircleSize, mPaint);

        moveCircle();
        checkEdge();

        if (mInvalidate) {
            mInvalidate = false;
            invalidate();
        }
    }


    /**
     * Move the circle x,y by the current speed.
     *
     * Will mark view to be invalidated.
     */
    private void moveCircle()
    {
        xCircle += xIncrement;
        yCircle += yIncrement;
        mInvalidate = true;
    }


    /**
     * Check to see if circle has hit the edge.
     *
     * If at edge will mark view to be invalidated.
     * At edge will reverse the direction of the circle
     * by flipping xIncrement or yIncrement.
     */
    private void checkEdge()
    {
        boolean change = false;
        if ( xCircle >= xMax )
        {
            xIncrement = -mCurXspeed;
            xCircle = xMax;
            change = true;
        }
        else if ( xCircle <= 0.F )
        {
            xIncrement = mCurXspeed;
            xCircle = 0.F;
            change = true;
        }

        if (yCircle >= yMax)
        {
            yIncrement = -mCurYspeed;
            yCircle = yMax;
            change = true;
        }
        else if (yCircle <= 0.F)
        {
            yIncrement = mCurYspeed;
            yCircle = 0.F;
            change = true;
        }

        if (change) {
            Log.d("CustomView",String.format("xInc=%f yInc=%f", xIncrement, yIncrement));
            mInvalidate = true;
        }
    }


    /**
     * Set circle to have random speed and direction.
     *
     * Will mark view to be invalidated.
     * The current x and y speed are randomly drawn limited to +/-mSpeed.  There is
     * a minimum speed allowed.
     */
    public void randomSpeedDirection() {
        final float minSpeed = 0.1F;
        xIncrement = mCurXspeed = Math.max( minSpeed, (float)Math.random() * mSpeed);
        if (Math.random() < 0.5)
            xIncrement *= -1.F;
        yIncrement = mCurYspeed = Math.max( minSpeed, (float)Math.random() * mSpeed);
        if (Math.random() < 0.5)
            yIncrement *= -1.F;

        mInvalidate = true;
    }


    /**
     * Set a new ball location.
     *
     * Will mark view to be invalidated.
     * Sets a new random speed and direction and also restricts x,y
     * to the view boundary.
     *
     * @param x the new circle x location (center of circle)
     * @param y the new circle y location (center of circle)
     */
    public void setBallLocation(float x, float y) {
        mInvalidate = true;

        // xCircle and yCircle mark the upper-left corner of the circle.  Incoming x,y are
        // for new center of circle so offset by half of circle size to get corner
        xCircle = x - xCircleSize/2;
        yCircle = y - yCircleSize/2;
        randomSpeedDirection();
        checkEdge();
    }
}