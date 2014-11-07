package com.wlx.wsolandroid.widget;

import com.wlx.wsolandroid.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DuanzaoView extends View {
	private Context context;
    private Paint   paint;
    //private Paint   transparentPaint;
    /** 画透明部分左间距为2 */
    private float transparentWidth = 2;

    private boolean needStar;

    
    private float selfWidth;
    private float selfHeight;
    
 
    public static final float totalPoint = 400;
    //1攻击 2破坏 3防御 4体力 5无双
    private int     type;

    /**
     * 
     * @param context
     * @param needStar 是否需要显示星星
     * @param totalWidth 整个属性条的像素长度
     * @param point 其中一种属性的数值，比如说攻击值120，防御230
     * @param selfHeight 自定义view的高度
     * @param type 武器五种属性的其中一种，用不同颜色条区分
     */
    public DuanzaoView(Context context, boolean needStar, float selfWidth, float selfHeight,
                       int type) {
        super(context);
        this.context = context;
        paint = new Paint();
        //transparentPaint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        //transparentPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        //transparentPaint.setColor(Color.TRANSPARENT);
        
        this.needStar = needStar;
        this.selfHeight = selfHeight;
        this.selfWidth = selfWidth;
        this.type = type;     
    }

    public DuanzaoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        //transparentPaint = new Paint();
        //transparentPaint.setColor(Color.TRANSPARENT);
    }

 
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        //根据type设置画笔颜色
        switch (type) {
            case 1:
                paint.setColor(context.getResources().getColor(R.color.gongjicao));
                break;
            case 2:
                paint.setColor(context.getResources().getColor(R.color.pohuaicao));
                break;
            case 3:
                paint.setColor(context.getResources().getColor(R.color.fangyucao));
                break;
            case 4:
                paint.setColor(context.getResources().getColor(R.color.tilicao));
                break;
            case 5:
                paint.setColor(context.getResources().getColor(R.color.wushuangcao));
                break;       
        }
        
        if (needStar) {
        	//先画左边的透明部分
        	//canvas.drawRect(0, 0, transparentWidth, selfHeight, transparentPaint);
        	//再画中间有颜色部分
        	canvas.drawRect(transparentWidth, 0, selfWidth, selfHeight, paint);
		}
        else {
        	canvas.drawRect(0, 0, selfWidth, selfHeight, paint);
		}
        
    }

}
