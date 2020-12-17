package com.wma.library.widget.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.wma.library.R;
import com.wma.library.utils.DPUtils;

/**
 * create by wma
 * on 2020/12/2 0002
 */
public class SubmitBtn extends View {
    final String TAG = SubmitBtn.class.getSimpleName();
    /**
     * 状态：正常
     */
    public static final int NORMAL = 0;

    /**
     * 状态：加载
     */
    public static final int LOADING = 1;
    /**
     * 状态：成功
     */
    public static final int SUCCESS = 2;
    /**
     * 状态：失败
     */
    public static final int FAIL = 3;
    private int mCurStatus = NORMAL;// 当前状态
    private String mText;// 文本
    private Context mContext;
    private Paint mPaint, mTextPaint, mLoadingPaint;
    private int mTextWidth, mTextHeight;// 文字宽高
    private int mWidth = -1, mHeight = -1;// 控件宽高
    private int mTextColor;// 文本颜色
    private int mBgColor, mFailColor, mSuccessColor;// 背景颜色
    private int mLoadingColor;//
    private Rect mTextBounds;
    private int mRectLeft = -1, mRectRight = -1;// 按钮左边 和右边坐标
    private int mTextSize;// 字体大小
    private int mRadius = 10;// 按钮圆角
    private int mLoadingStroke = -1;// 进度条宽度
    private int mLoadingStartAngle = -90;
    private int mLoadingSweepAngle = 0;
    private String mSuccessText, mFailText;
    private AnimatorSet mLoadingAnimSet;
    private AnimatorSet mLoadFinishSet;
    private String mCurText;

    public SubmitBtn(Context context) {
        this(context, null);
    }

    public SubmitBtn(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubmitBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SubmitBtn);
        mText = ta.getString(R.styleable.SubmitBtn_Text);
        mTextColor = ta.getColor(R.styleable.SubmitBtn_TextColor, ContextCompat.getColor(mContext, R.color.colorPrimary));
        mBgColor = ta.getColor(R.styleable.SubmitBtn_BackgroundColor, ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        mFailColor = ta.getColor(R.styleable.SubmitBtn_FailColor, ContextCompat.getColor(mContext, R.color.colorLoadBtnFail));
        mSuccessColor = ta.getColor(R.styleable.SubmitBtn_SuccessColor, ContextCompat.getColor(mContext, R.color.colorLoadBtnSuccess));
        mTextSize = ta.getDimensionPixelSize(R.styleable.SubmitBtn_TextSize, DPUtils.sp2px(mContext, 15));
        mSuccessText = ta.getString(R.styleable.SubmitBtn_SuccessHint);
        mSuccessText = TextUtils.isEmpty(mSuccessText) ? "成功" : mSuccessText;
        mFailText = ta.getString(R.styleable.SubmitBtn_FailHint);
        mFailText = TextUtils.isEmpty(mFailText) ? "失败" : mFailText;
        mLoadingColor = ta.getColor(R.styleable.SubmitBtn_TextColor, Color.WHITE);
        mCurText = mText;
        ta.recycle();
        init();
    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBgColor);
        mPaint.setStrokeWidth(DPUtils.dip2px(mContext, 2));
        mLoadingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLoadingPaint.setColor(mLoadingColor);
        mLoadingPaint.setStrokeWidth(mLoadingStroke);
        mLoadingPaint.setStyle(Paint.Style.STROKE);
        mTextBounds = new Rect();

        mLoadingAnimSet = new AnimatorSet();
        mLoadFinishSet = new AnimatorSet();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
        mTextWidth = mTextBounds.right - mTextBounds.left;
        mTextHeight = mTextBounds.bottom - mTextBounds.top;
        int widthMod = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMod) {
            case MeasureSpec.AT_MOST:
                int ts = DPUtils.sp2px(mContext, 15);
                int pd = DPUtils.dip2px(mContext, 40);
                int p = (int) (pd * 1f / ts * mTextSize);
                mWidth = Math.min(widthSize, mTextWidth + p);
                break;
            case MeasureSpec.EXACTLY:
                mWidth = widthSize;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                int ts = DPUtils.sp2px(mContext, 15);
                int pd = DPUtils.dip2px(mContext, 20);
                int p = (int) (pd * 1f / ts * mTextSize);
                mHeight = Math.min(heightSize, mTextHeight + p);
                break;
            case MeasureSpec.EXACTLY:
                mHeight = heightSize;
                break;
        }
        setMeasuredDimension(mWidth, mHeight);
        if (mRectLeft == -1 || mRectRight == -1) {
            mRectLeft = 0;
            mRectRight = mWidth;
        }
        if (mLoadingStroke == -1) {
            mLoadingStroke = mHeight / 20;
            mLoadingPaint.setStrokeWidth(mLoadingStroke);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurStatus == LOADING) {
            canvas.drawRoundRect(mRectLeft, 0, mRectRight, mHeight, mRadius, mRadius, mPaint);
            canvas.drawArc(mRectLeft + mLoadingStroke, 0 + mLoadingStroke, mRectRight - mLoadingStroke, mHeight - mLoadingStroke, mLoadingStartAngle, mLoadingSweepAngle, false, mLoadingPaint);
            canvas.drawText(mText, mWidth / 2, mHeight / 2 + mTextHeight / 2, mTextPaint);
        } else {
            canvas.drawRoundRect(mRectLeft, 0, mRectRight, mHeight, mRadius, mRadius, mPaint);
            canvas.drawText(mCurText, mWidth / 2, mHeight / 2 + mTextHeight / 2, mTextPaint);
        }
    }


    public void setCurStatus(int status) {
        mCurStatus = status;
        if (mCurStatus == LOADING) {
            goLoadingAnim();
            setEnabled(false);
        } else if (mCurStatus == FAIL) {
            loadFinishAnim(false);
            setEnabled(true);
        } else if (mCurStatus == SUCCESS) {
            loadFinishAnim(true);
            setEnabled(true);
        }
    }

    public void setText(String text){
        mText = text;
        invalidate();
    }

    public void setSuccessText(String text){
        mSuccessText = text;
    }
    public void setFailText(String text){
        mFailText = text;

    }

    public int getCurStatus(){
        return mCurStatus;
    }


    /**
     * 开始加载动画
     */
    private void goLoadingAnim() {
        mCurStatus = LOADING;
        if (mLoadingAnimSet.isRunning()) {
            mLoadingAnimSet.cancel();
        }
        if (mLoadFinishSet.isRunning()) {
            mLoadFinishSet.cancel();
        }
        mLoadingStartAngle = -90;

        ValueAnimator textSizeAnim = ValueAnimator.ofInt(mTextSize, 0);
        textSizeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mTextPaint.setTextSize(animatedValue);
            }
        });

        ValueAnimator textAlphaAnim = ValueAnimator.ofInt(255, 0);
        textAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mTextPaint.setAlpha(animatedValue);
            }
        });

        ValueAnimator radiusAnim = ValueAnimator.ofInt(mRadius, 100);
        radiusAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mRadius = animatedValue;
                invalidate();
            }
        });
        ValueAnimator leftAnim = ValueAnimator.ofInt(mRectLeft, mWidth / 2 - mHeight / 2);
        leftAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRectLeft = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator rightAnim = ValueAnimator.ofInt(mRectRight, mWidth / 2 + mHeight / 2);
        rightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRectRight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator loadAnim2 = ValueAnimator.ofInt(mLoadingStartAngle, 270);
        loadAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLoadingStartAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        loadAnim2.setRepeatCount(-1);
        ValueAnimator loadAnim1 = ValueAnimator.ofInt(0, 90);
        loadAnim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLoadingSweepAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mLoadingAnimSet.play(radiusAnim).with(leftAnim).with(rightAnim).with(textAlphaAnim).with(textSizeAnim).before(loadAnim2).before(loadAnim1);
        mLoadingAnimSet.setDuration(500);
        mLoadingAnimSet.start();
    }


    /**
     * 恢复正常动画
     */
    private void loadFinishAnim(boolean isSuccess) {
        if (mLoadingAnimSet.isRunning()) {
            mLoadingAnimSet.cancel();
        }
        if (mLoadFinishSet.isRunning()) {
            mLoadFinishSet.cancel();
        }

        ValueAnimator textSizeAnim = ValueAnimator.ofInt(0, mTextSize);
        textSizeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mTextPaint.setTextSize(animatedValue);
            }
        });
        ValueAnimator textAlpha = ValueAnimator.ofInt(0, 255);
        textAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mTextPaint.setAlpha(animatedValue);
            }
        });
        ValueAnimator radiusAnim = ValueAnimator.ofInt(mRadius, 10);
        radiusAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mRadius = animatedValue;
                invalidate();
            }
        });
        ValueAnimator leftAnim = ValueAnimator.ofInt(mRectLeft, 0);
        leftAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRectLeft = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator rightAnim = ValueAnimator.ofInt(mRectRight, mWidth);
        rightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRectRight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator loadAnim = ValueAnimator.ofInt(90, 0);
        loadAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLoadingSweepAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator colorAnim = null;
        if (isSuccess) {
            mCurText = mSuccessText;
            colorAnim = ValueAnimator.ofArgb(mBgColor, mSuccessColor);
        } else {
            mCurText = mFailText;
            colorAnim = ValueAnimator.ofArgb(mBgColor, mFailColor);
        }
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBgColor = (int) animation.getAnimatedValue();
                mPaint.setColor(mBgColor);
            }
        });
        mLoadFinishSet.play(radiusAnim).with(leftAnim).with(rightAnim).with(loadAnim).with(colorAnim).with(textAlpha).with(textSizeAnim);
        mLoadFinishSet.setDuration(500);
        mLoadFinishSet.start();
        mLoadFinishSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCurStatus = NORMAL;
            }
        });
    }

}
