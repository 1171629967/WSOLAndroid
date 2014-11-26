package com.wlx.wsolandroid;

import com.wlx.wsolandroid.widget.ProgressWheel;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    public menuClicklistener mClicklistener;

    public interface menuClicklistener {
        public void menuClick();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mClicklistener = (menuClicklistener) activity;
    }
    
    public void startLoading(ProgressWheel progressWheel){
    	int[] pixels = new int[] { 0xFF2E9121, 0xFF2E9121, 0xFF2E9121, 0xFF2E9121, 0xFF2E9121, 0xFF2E9121, 0xFFFFFFFF, 0xFFFFFFFF };
		Bitmap bm = Bitmap.createBitmap(pixels, 8, 1, Bitmap.Config.ARGB_8888);
		Shader shader = new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		progressWheel.setRimShader(shader);
		progressWheel.spin();
    }

}
