package com.wlx.wsolandroid.widget;

import com.wlx.wsolandroid.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class TesuqianghuaView extends View {
	private Context context;
    private Paint   paint;

    private boolean ifFirst;

    
    private float selfWidth;
    private float selfHeight;
    
 


    /**
     * 
     * @param context
     * @param currentTesuTime 当前已是第几个强化位
     * @param selfWidth 强化view的长度
     * @param selfHeight 自定义view的高度
     */
    public TesuqianghuaView(Context context, int currentTesuTime, float selfWidth, float selfHeight) {
        super(context);
        this.context = context;
        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        
        if (currentTesuTime == 0) {
        	this.ifFirst = true;
		}
        else {
        	this.ifFirst = false;
		}
        
        
        
        this.selfHeight = selfHeight;
        this.selfWidth = selfWidth;    
    }

    public TesuqianghuaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

 
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        //根据type设置画笔颜色
        paint.setColor(context.getResources().getColor(R.color.tesuqianghua));
               
        
        if (ifFirst) {
        	canvas.drawRect(0, 0, selfWidth, selfHeight, paint);
		}
        else {
        	canvas.drawRect(selfWidth / 4, 0, selfWidth + selfWidth / 4, selfHeight, paint);
		}
        
    }

}
