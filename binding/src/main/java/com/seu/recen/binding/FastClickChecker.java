package com.seu.recen.binding;

/**
 * Created by Recen on 2017/4/16.
 */

public class FastClickChecker {
    //time unit is ms
    private static final long FAST_CLICK_INTERVAL = 1000;
    private long lastClickTime;

    public  boolean isFastClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= FAST_CLICK_INTERVAL) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }
}
