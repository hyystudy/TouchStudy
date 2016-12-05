package com.example.sks.touchstudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by sks on 2016/12/5.
 */
public class CustomFramLayout extends FrameLayout {
    private static final String TAG = "MySwitch";
    private View mTrack;
    private int mWidth, mHeight;
    private Scroller mScroller;
    private int mSwitchBgOffColor, mSwitchBgOnColor, mTrackBgOnColor, mTrackBgOffColor;
    private boolean mChecked;
    private Paint mPaint;
    private RectF mTopBounds;
    private RectF mBottomBounds;

    public CustomFramLayout(Context context) {
        super(context);
    }

    public CustomFramLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mScroller = new Scroller(getContext());
    }


    public CustomFramLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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


    private void onSwitchOff() {
        setBackgroundResource(R.drawable.switch_bg_off);
        mTrack.setBackgroundResource(R.drawable.track_drawable_off);
    }

    private void onSwitchOn() {
        setBackgroundResource(R.drawable.switch_bg_on);
        mTrack.setBackgroundResource(R.drawable.track_drawable_on);
    }

    private void smoothScrollTo(int destX, int destY) {
        float deltaY = destY - getScrollY();
        mScroller.startScroll(0, getScrollY(), 0, (int) deltaY);
        invalidate();

    }

    @Override
    public void scrollTo(int x, int y) {
        if (y > 0) y = 0;
        if (y < mTrack.getHeight() - mHeight) y = ( mTrack.getHeight() - mHeight);
        super.scrollTo(x, y);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            int currX = mScroller.getCurrX();
            int currY = mScroller.getCurrY();
            scrollTo(currX, currY);
            postInvalidate();
        }
    }
}
