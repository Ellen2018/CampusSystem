package com.zheng.zhi.campussystem.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;


@SuppressLint("AppCompatCustomView")
public class CalculatorTextView extends TextView {

    private Context context;

    private int upColor = 0;
    private int downColor = Color.GRAY;

    public CalculatorTextView(Context context) {
        super(context);
        this.context = context;
    }

    public int getUpColor() {
        return upColor;
    }

    public void setUpColor(int upColor) {
        this.setBackgroundColor(upColor);
        this.upColor = upColor;
    }

    public void setDownColor(int downColor) {
        this.downColor = downColor;
    }

    public int getDownColor() {
        return downColor;
    }

    public CalculatorTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalculatorTextView);
        int n = typedArray.getIndexCount();
        for(int i=0;i<n;i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CalculatorTextView_up_color:
                    upColor = typedArray.getColor(attr,upColor);
                    break;
                case R.styleable.CalculatorTextView_down_color:
                    downColor = typedArray.getColor(attr,downColor);
                    break;
            }
        }
        this.setBackgroundColor(upColor);
        typedArray.recycle();
    }

    public CalculatorTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.setBackgroundColor(downColor);
                break;
            case MotionEvent.ACTION_UP:
                this.setBackgroundColor(upColor);
                break;
            case MotionEvent.ACTION_CANCEL:
                this.setBackgroundColor(upColor);
                break;
        }

        return super.onTouchEvent(event);
    }
}
