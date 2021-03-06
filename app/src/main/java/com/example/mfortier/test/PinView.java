package com.example.mfortier.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class PinView extends View {

    Pin pin;

    public PinView(Context c, Pin p) {
        super(c);

        pin = p;

    }

    public void setPin(Pin pPin){
        pin = pPin;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = 100;
        int desiredHeight = 100;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 100, 100, pin.toPaint());

        Paint mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setShader(new RadialGradient(canvas.getWidth()/3, canvas.getHeight()/3, canvas.getWidth(), Color.TRANSPARENT, Color.BLACK, Shader.TileMode.MIRROR));

        canvas.drawRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 100, 100, pin.toPaint());
        canvas.drawRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 100, 100, mPaint);
    }
}