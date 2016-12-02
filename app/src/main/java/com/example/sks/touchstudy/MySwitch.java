package com.example.sks.touchstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by sks on 2016/12/2.
 */
public class MySwitch extends LinearLayout {
    private static final String TAG = "MySwitch";
    private float mDownY, mLastY;
    private int mTouchSlop;
    private View mTrack;
    private int mWidth, mHeight;

    public MySwitch(Context context) {
        super(context);
    }

    public MySwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public MySwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTrack = findViewById(R.id.track);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w - getPaddingRight() - getPaddingLeft();
        mHeight = h - getPaddingBottom() - getPaddingTop();
        Log.d(TAG, "onSizeChanged: width" + w);
        Log.d(TAG, "onSizeChanged: height" + h);
        Log.d(TAG, "onSizeChanged: mWidth" + mWidth);
        Log.d(TAG, "onSizeChanged: mHeight" + mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownY = mLastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = eventY - mLastY;
                mLastY = eventY;
                Log.d(TAG, "onTouchEvent: deltaY-->" + deltaY);
                if (getScrollX() < 0)
                scrollBy(0, (int) -deltaY);
                break;
            case MotionEvent.ACTION_UP:
                float disY = eventY - mDownY;
                Log.d(TAG, "onTouchEvent: disY-->" + disY);
                break;
        }

        return true;
    }
}
