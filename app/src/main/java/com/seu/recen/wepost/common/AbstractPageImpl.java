package com.seu.recen.wepost.common;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by Recen on 2017/4/16.
 */

public abstract class AbstractPageImpl implements IPage{
    protected WeakReference<BaseActivity> wrActivity;
    protected WeakReference<Fragment> wrFragment;

    public AbstractPageImpl(BaseActivity activity) {
        if (null == activity) {
            throw new IllegalArgumentException("activity can not be null.");
        }

        wrActivity = new WeakReference<>(activity);
    }

    public AbstractPageImpl(Fragment fragment) {
        this((BaseActivity) fragment.getActivity());
        wrFragment = new WeakReference<Fragment>(fragment);
    }

    @Override
    public void finish() {
        Activity activity = wrActivity.get();
//        if (!Safeguard.ignorable(activity)) {
            activity.finish();
//        }
    }

    private void replaceFragment(Fragment fragment) {
        BaseActivity activity = wrActivity.get();
//        if (!Safeguard.ignorable(activity)) {
            activity.replaceFragment(android.R.id.content, fragment, true);
//        }
    }

    @Override
    public BaseActivity getActivity() {
        return wrActivity.get();
    }
}
