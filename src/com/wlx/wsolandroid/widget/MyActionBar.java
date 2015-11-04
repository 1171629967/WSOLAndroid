package com.wlx.wsolandroid.widget;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlx.wsolandroid.R;

public class MyActionBar extends MyRelativeLayoutView {

    private LinearLayout   leftLayout;
    private LinearLayout   middleLayout;
    private LinearLayout   rightLayout;

    private TextView       leftText;
    private TextView       middleText;
    private TextView       rightText;

    private ImageView      leftImage;
    private ImageView      rightImage;

    private final Activity mActivity;

    public MyActionBar(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected int initLayout() {
        return R.layout.actionbar_normal;
    }
    
    

    @Override
    protected void initView() {
        leftLayout = (LinearLayout) findViewById(R.id.menu_head_left);
        middleLayout = (LinearLayout) findViewById(R.id.menu_head_middle);
        rightLayout = (LinearLayout) findViewById(R.id.menu_head_right);
        leftText = (TextView) findViewById(R.id.menu_head_left_tv);
        middleText = (TextView) findViewById(R.id.menu_head_middle_tv);
        rightText = (TextView) findViewById(R.id.menu_head_right_tv);
        leftImage = (ImageView) findViewById(R.id.menu_head_left_iv);
        rightImage = (ImageView) findViewById(R.id.menu_head_right_iv);
    }

    @Override
    public void setData(Object object) {

    }

    @Override
    protected void initDefult() {
        super.initDefult();
        defaultLeft();
    }

    private void defaultLeft() {
        leftImage.setImageResource(R.drawable.menu_left);
        leftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mActivity.finish();
                mActivity.overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });
    }

    /**
     * 设置左侧文本
     * 
     * @param text
     */
    public void setLeftText(String text) {
        leftText.setText(text);
        leftImage.setVisibility(View.GONE);
    }

    /**
     * 设置左侧文本
     * 
     * @param resId
     */
    public void setLeftText(int resId) {
        leftText.setText(mActivity.getString(resId));
        leftImage.setVisibility(View.GONE);
    }

    /**
     * 设置左侧图标
     * 
     * @param resId
     */
    public void setLeftIcon(int resId) {
        leftImage.setImageResource(resId);
    }

    /**
     * 设置左侧动作
     * 
     * @param intent
     */
    public void setLeftIntent(final Intent intent) {
        leftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });
    }

    /**
     * 设置右侧事件监听
     * 
     * @param listener id:R.id.menu_head_left 建议使用匿名类实现
     */
    public void setLeftClickListenner(OnClickListener listener) {
        leftLayout.setOnClickListener(listener);
    }

    /**
     * 设置是否显示左侧返回按钮，默认显示
     * 
     * @param enable
     */
    public void setLeftEnable(boolean enable) {
        if (!enable) {
            leftLayout.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置标题
     * 
     * @param title
     */
    public void setTitle(String title) {
        middleText.setText(title);
    }

    /**
     * 设置标题
     * 
     * @param res
     */
    public void setTitle(int resId) {
        middleText.setText(mActivity.getString(resId));
    }

    /**
     * 获取标题
     * 
     * @return
     */
    public String getTitle() {
        return middleText.getText().toString();
    }

    /**
     * 设置右侧标题
     * 
     * @param text
     */
    public void setRightText(String text) {
        rightText.setText(text);
    }

    /**
     * 设置右侧标题
     * 
     * @param resId
     */
    public void setRightText(int resId) {
        rightText.setText(mActivity.getString(resId));
    }

    /**
     * 设置右侧图标
     * 
     * @param resId
     */
    public void setRightIcon(int resId) {
        rightImage.setImageResource(resId);
    }

    /**
     * 设置右侧动作
     * 
     * @param intent
     */
    public void setRightIntent(final Intent intent) {
        rightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
    }

    /**
     * 设置右侧事件监听
     * 
     * @param listener id:R.id.menu_head_right 建议使用匿名类实现
     */
    public void setRightClickListenner(OnClickListener listener) {
        rightLayout.setOnClickListener(listener);
    }
    
    
    public LinearLayout getRightLayout(){
    	return rightLayout;
    }

}
