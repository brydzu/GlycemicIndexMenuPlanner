package com.naelteam.glycemicindexmenuplanner.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;


public class GlycemicIndexValueView extends View {

    private String mValue;
    private Paint mCirclePaint = new Paint();
    private Paint mTextPaint = new Paint();
    private int mGradientColor;
    private float mRadius;
    private int mCenterX;
    private int mCenterY;
    private Rect mBounds;

    public GlycemicIndexValueView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mCirclePaint.setStrokeWidth(1.0f);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.RED);
        mGradientColor = Color.RED;

        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(12f);

        mBounds = new Rect();
    }

    private void setTextSizeForWidth(Paint paint, float desiredWidth, String text) {
        final float testTextSize = 24f;

        paint.setTextSize(testTextSize);
        mBounds.setEmpty();
        paint.getTextBounds(text, 0, text.length(), mBounds);

        float desiredTextSize = testTextSize * desiredWidth / mBounds.width();
        paint.setTextSize(desiredTextSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //Log.d("", "onSizeChanged - w="+w+", h=" + h + ", value=" + mValue);

        setTextSizeForWidth(mTextPaint, h/2, "50");

        mCenterX = getWidth()/2;
        mCenterY = getHeight()/2;

        if(mCenterX > mCenterY) {
            mRadius = mCenterY - 10;
        }else {
            mRadius = mCenterX - 10;
        }

        mCirclePaint.setShader(new RadialGradient(getWidth() / 2, getHeight() / 2,
                mRadius/2, mCirclePaint.getColor(), mGradientColor, Shader.TileMode.MIRROR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirclePaint);
        if (mValue!=null && (!mValue.isEmpty())){
            mBounds.setEmpty();
            mTextPaint.getTextBounds(mValue, 0, mValue.length(), mBounds);
            int offsetY = mBounds.height();
            canvas.drawText(mValue, mCenterX, mCenterY + (offsetY/2), mTextPaint);
        }
    }

    public void setValue(String value) {
        this.mValue = value;
    }

    public void setColor(int color) {
        mCirclePaint.setColor(color);
    }

    public void setGradientColor(int color) {
        mGradientColor = color;
    }
}
