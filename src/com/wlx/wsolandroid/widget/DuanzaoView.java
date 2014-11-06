package com.wlx.wsolandroid.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DuanzaoView extends View {
    private Paint   paint;

    private boolean needStar;
    private float   totalWidth;
    
    private float selfWidth;
    private float selfHeight;
    
    private float   point;   
    private float totalPoint = 400;
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
    public DuanzaoView(Context context, boolean needStar, float totalWidth, float point, float selfHeight,
                       int type) {
        super(context);
        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        this.needStar = needStar;
        this.totalWidth = totalWidth;
        this.point = point;
        this.selfHeight = selfHeight;
        this.type = type;
        
        //计算出该view的长度
        this.selfWidth = (totalWidth / totalPoint) * this.point;
           
    }

    public DuanzaoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

    }

 
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        //根据type设置画笔颜色
        switch (type) {
            case 1:
                paint.setColor(Color.YELLOW);
                break;
            case 2:
                paint.setColor(Color.BLUE);
                break;
            case 3:
                paint.setColor(Color.GREEN);
                break;
            case 4:
                paint.setColor(Color.CYAN);
                break;
            case 5:
                paint.setColor(Color.RED);
                break;       
        }
        
        canvas.drawRect(0, 0, selfWidth, selfHeight, paint);
    }

}
