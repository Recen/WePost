package com.seu.recen.wepost.common;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Recen on 2017/4/16.
 */

public class BaseActivity extends ActionBarActivity {
    public void replaceFragment(int layoutId, Fragment frag, boolean enableBack) {
        if (frag != null) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(layoutId, frag);
            if (enableBack) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
