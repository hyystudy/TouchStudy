package com.example.sks.touchstudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by sks on 2016/12/5.
 */
public class VerticalTrack extends View {
    private static final String TAG = "VerticalTrack";
    private float mDownY, mLastY;
    private int mTouchSlop;
    private Scroller mScroller;
    private FrameLayout.LayoutParams mLayoutParams;

    public VerticalTrack(Context context) {
        super(context);
    }

    public VerticalTrack(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public VerticalTrack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs) {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mLayoutParams = new FrameLayout.LayoutParams(w, h);
        mLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
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
                float disY = eventY - mDownY;
                mLastY = eventY;
                Log.d(TAG, ": deltaY-->" + deltaY);
                if (Math.abs(disY) > mTouchSlop) {
                    FrameLayout parent = (FrameLayout) getParent();
                    parent.scrollBy(0, (int) -deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
                float finalDisY = eventY - mDownY;
                Log.d(TAG, "onTouchEvent: disY-->" + finalDisY);
                break;
        }
        return true;
    }
}
