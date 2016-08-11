package me.fallblank.easygraph.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by fallb on 2016/8/10.
 */
public class PieGraph extends Graph {

    private RectF mArea;
    private double mTouchAngle;
    private int mDistance = 25;
    private int mActiveAlpha = 255;
    private int mPassiveAlpha = 180;

    private boolean mClicked = false;

    private PieClickedListener mListener;


    public interface PieClickedListener {
        public void onPieClicked(int index);
    }


    @Override protected void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPandingTop = getPaddingTop();
        mPandingBottom = getPaddingBottom();
        mPandingRight = getPaddingRight();
        mPandingLeft = getPaddingLeft();
    }


    public void setPieClickedListener(PieClickedListener listener) {
        mListener = listener;
    }


    public PieGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public PieGraph(Context context) {
        super(context);
        init();
    }


    public PieGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public PieGraph(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {
            int min = Math.min(widthSize, heightSize);
            setMeasuredDimension(min, min);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        int min = Math.min(w, h);
        mArea = new RectF(mDistance - min / 2, mDistance - min / 2, min / 2 - mDistance,
            min / 2 - mDistance);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAdapter == null) return;
        canvas.translate(mWidth / 2, mHeight / 2);
        int angle = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            int color = mAdapter.getColor(i);
            double percent = mAdapter.getPercent(i);
            mPaint.setColor(color);
            mPaint.setAlpha(mPassiveAlpha);
            if (mClicked && angle <= mTouchAngle && angle + 360 * percent >= mTouchAngle) {
                mPaint.setAlpha(mActiveAlpha);
                double centerAngle = angle + 360 * percent / 2;
                float moveX = (float) (mDistance * Math.cos(centerAngle / 360 * Math.PI * 2));
                float moveY = (float) (mDistance * Math.sin(centerAngle / 360 * Math.PI * 2));
                float left = mArea.left + moveX;
                float top = mArea.top + moveY;
                float right = mArea.right + moveX;
                float bottom = mArea.bottom + moveY;
                mArea.set(left, top, right, bottom);
                canvas.drawArc(mArea, angle, (float) (360 * percent), true, mPaint);
                mArea.set(left - moveX, top - moveY, right - moveX, bottom - moveY);
                if (mListener != null) {
                    mListener.onPieClicked(i);
                }
            } else {
                canvas.drawArc(mArea, angle, (float) (360 * percent), true, mPaint);
            }
            angle += percent * 360;
        }
    }


    @Override public boolean onTouchEvent(MotionEvent event) {
        if (!checkClickValid(event)) return true;
        mTouchAngle = getTouchAngle(event);
        mClicked = true;
        invalidate();
        return super.onTouchEvent(event);
    }


    private boolean checkClickValid(MotionEvent event) {
        float radius = mArea.width() / 2;
        float centerX = mWidth / 2;
        float centerY = mHeight / 2;
        float x = event.getX();
        float y = event.getY();
        double sqrtDistance = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
        if (sqrtDistance < Math.pow(radius, 2)) {
            return true;
        } else {
            return false;
        }
    }


    private double getTouchAngle(MotionEvent event) {
        float centerX = mWidth / 2;
        float centerY = mHeight / 2;
        float x = event.getX();
        float y = event.getY();

        float xToCenter = x - centerX;
        float yToCenter = y - centerY;
        double angle = Math.atan(yToCenter / xToCenter) / (Math.PI * 2) * 360;
        double result = 0;
        if (xToCenter >= 0 && yToCenter >= 0) {
            result = angle;
        } else if (xToCenter <= 0 && yToCenter >= 0) {
            result = 180 + angle;
        } else if (xToCenter <= 0 && yToCenter <= 0) {
            result = 180 + angle;
        } else if (xToCenter >= 0 && yToCenter <= 0) {
            result = 360 + angle;
        }
        return result;
    }


    public int getPassiveAlpha() {
        return mPassiveAlpha;
    }


    public void setPassiveAlpha(int passiveAlpha) {
        mPassiveAlpha = passiveAlpha;
        invalidate();
    }


    public int getActiveAlpha() {
        return mActiveAlpha;
    }


    public void setActiveAlpha(int activeAlpha) {
        mActiveAlpha = activeAlpha;
        invalidate();
    }


    public int getDistance() {
        return mDistance;
    }


    public void setDistance(int distance) {
        mDistance = distance;
        invalidate();
    }
}
