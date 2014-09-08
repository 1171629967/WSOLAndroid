package com.wlx.wsolandroid;

import android.app.Activity;
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

}
