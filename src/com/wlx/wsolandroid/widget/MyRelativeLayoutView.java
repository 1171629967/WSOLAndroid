package com.wlx.wsolandroid.widget;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 自定义控件需要继承的父类
 * 
 * @author wb-shenyueshuang
 */
public abstract class MyRelativeLayoutView extends RelativeLayout {

    //    protected final HappyLiveFaceLoader  faceLoader;
    //    protected final HappyLiveImageLoader imageLoader;
    protected Context context;

    public MyRelativeLayoutView(Context context) {
        super(context);
        this.context = context;
        View.inflate(context, initLayout(), this);
        initView();
        //        faceLoader = HappyLiveFaceLoader.getInstance(context);
        //        imageLoader = HappyLiveImageLoader.getInstance(context);
        initDefult();
    }

    /**
     * 初始化默认工作
     */
    protected void initDefult() {
        //        faceLoader = HappyLiveFaceLoader.getInstance(context);
        //        imageLoader = HappyLiveImageLoader.getInstance(context);
    }

    /**
     * 初始化布局文件
     */
    protected abstract int initLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void setData(Object object);

}
