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
 * Created by sks on 2016/12/2.
 */
public class VerticalSwitch extends FrameLayout {
    private static final String TAG = "MySwitch";
    private float mDownY, mLastY;
    private int mTouchSlop;
    private View mTrack;
    private int mWidth, mHeight;
    private Scroller mScroller;
    private Path mPath;
    private int mSwitchBgOffColor, mSwitchBgOnColor, mTrackBgOnColor, mTrackBgOffColor;
    private boolean mChecked;
    private Paint mPaint;
    private RectF mTopBounds;
    private RectF mBottomBounds;

    public VerticalSwitch(Context context) {
        super(context);
    }

    public VerticalSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VerticalSwitch);
        mSwitchBgOffColor = typedArray.getColor(R.styleable.VerticalSwitch_switch_bg_off, Color.parseColor("#70ffffff"));
        mSwitchBgOnColor = typedArray.getColor(R.styleable.VerticalSwitch_switch_bg_on, Color.parseColor("#794ca5"));
        mTrackBgOnColor = typedArray.getColor(R.styleable.VerticalSwitch_track_bg_on, Color.parseColor("#ff00ff"));
        mTrackBgOffColor = typedArray.getColor(R.styleable.VerticalSwitch_track_bg_off, Color.parseColor("#ffffff"));
        mChecked = typedArray.getBoolean(R.styleable.VerticalSwitch_checked, true);
        typedArray.recycle();


        mPath = new Path();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        if (mChecked){
            mPaint.setColor(mSwitchBgOnColor);
        } else {
            mPaint.setColor(mSwitchBgOffColor);
        }

        mTopBounds = new RectF();
        mBottomBounds = new RectF();
    }

    public VerticalSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
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

        caculateBottomBounds();
        caculateTopBounds();
    }

    private void caculateTopBounds() {
        mTopBounds.left = 0;
        mTopBounds.top = 0;
        mTopBounds.right = mWidth;
        mTopBounds.bottom = mWidth/2;
    }

    private void caculateBottomBounds() {
        mBottomBounds.left = 0;
        mBottomBounds.top = mHeight - mWidth/2;
        mBottomBounds.right = mWidth;
        mBottomBounds.bottom = mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       /* mPath.moveTo(0, mWidth/2);
        mPath.lineTo(0, mHeight - mWidth/2);
        mPath.arcTo(mBottomBounds, 180f, -180, false);
        mPath.lineTo(mWidth, mWidth/2);
        mPath.arcTo(mTopBounds, 0, -180, false);
        canvas.drawPath(mPath, mPaint);*/
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
                float disY = eventY - mDownY;
                mLastY = eventY;
                Log.d(TAG, ": deltaY-->" + deltaY);
                if (Math.abs(disY) > mTouchSlop) {
                    scrollBy(0, (int) -deltaY);
                }
                if (getScrollY() >= (mTrack.getHeight() - mHeight)/2) {
                    //smoothScrollTo(0, 0);
                    onSwitchOn();
                } else {
                    //smoothScrollTo(0, (mTrack.getHeight() - mHeight));
                    onSwitchOff();
                }
                break;
            case MotionEvent.ACTION_UP:
                float finalDisY = eventY - mDownY;
                if (getScrollY() >= (mTrack.getHeight() - mHeight)/2) {
                    smoothScrollTo(0, 0);
                    onSwitchOn();
                } else {
                    smoothScrollTo(0, (mTrack.getHeight() - mHeight));
                    onSwitchOff();
                }
                Log.d(TAG, "onTouchEvent: disY-->" + finalDisY);
                break;
        }

        return true;
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
