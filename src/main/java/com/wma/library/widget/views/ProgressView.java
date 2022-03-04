package com.wma.library.widget.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.wma.library.R;

/**
 * Created by WMA on 2022/2/14.
 */
public class ProgressView extends View {

    private static final int Circle = 1;
    private static final int Line = 2;

    private Paint mPaint;
    private Context mContext;

    private int mWidth, mHeight;

    private RectF mBGRectF, mFGRectF;

    private int mBGColor, mFGColor;

    private int mProcess;

    private int mStrokeWidth;

    private long mMax,mCur;

    private int mCurType;

    private int mPadding = 20;

    private int mCurProcess = 0;


    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mBGColor = ta.getColor(R.styleable.ProgressView_bgColor, ContextCompat.getColor(mContext, R.color.progressBGColor));
        mFGColor = ta.getColor(R.styleable.ProgressView_bgColor, ContextCompat.getColor(mContext, R.color.progressFGColor));
        mCurType = ta.getColor(R.styleable.ProgressView_type, Line);
        mStrokeWidth = (int) ta.getDimension(R.styleable.ProgressView_paintWidth, 10);
        init();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.blue_btn_bg_color));
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mBGRectF = new RectF(0, 0, 0, 0);
        mFGRectF = new RectF(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                mWidth = widthSize;
                break;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                mHeight = heightSize;
                break;
        }
        setMeasuredDimension(mWidth, mHeight);

        if (mCurType == Circle) {
            mBGRectF.set(mStrokeWidth / 2 + mPadding, mStrokeWidth / 2 + mPadding, mWidth - mStrokeWidth / 2 - mPadding, mHeight - mStrokeWidth / 2 - mPadding);
            mFGRectF.set(mStrokeWidth / 2 + mPadding, mStrokeWidth / 2 + mPadding, mWidth - mStrokeWidth / 2 - mPadding, mHeight - mStrokeWidth / 2 - mPadding);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurType == Line) {
            drawLine(canvas);
        } else if (mCurType == Circle) {
            drawCircle(canvas);
        }

    }

    /**
     * 画圈
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        mPaint.setColor(mBGColor);
        mPaint.setStrokeWidth(mStrokeWidth / 2);
        mPaint.setMaskFilter(null);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mBGRectF, 0, 360, false, mPaint);

        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mFGColor);
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
        canvas.drawArc(mFGRectF, 180, mCurProcess, false, mPaint);
    }

    /**
     * 画横向
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        mPaint.setColor(mBGColor);
        mPaint.setMaskFilter(null);
        mPaint.setStrokeWidth(mStrokeWidth / 2);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(0 + mPadding, mHeight / 2, mWidth - mPadding, mHeight / 2, mPaint);

        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mFGColor);
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
        mPaint.setStyle(Paint.Style.FILL);
        if (mCurProcess - mPadding < mPadding) {
            canvas.drawLine(0 + mPadding, mHeight / 2, mPadding, mHeight / 2, mPaint);
        } else {
            canvas.drawLine(0 + mPadding, mHeight / 2, mCurProcess - mPadding, mHeight / 2, mPaint);
        }
    }


    public void setMax(long max) {
        this.mMax = max;
    }


    public void setCur(long cur) {
        this.mCur = cur;
        if (mCurType == Line) {
            mProcess = (int) (cur * mWidth / this.mMax);
        } else if (mCurType == Circle) {
            mProcess = (int) (cur * 360 / this.mMax);
        }
        startProcessAnim();
    }

    private void startProcessAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mProcess);
        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            mCurProcess = (int) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

    public String getProcessStr() {
        return (this.mCur * 100 / this.mMax ) + "%";
    }


}
