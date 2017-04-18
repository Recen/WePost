package com.seu.recen.wepost;

import android.app.Activity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.seu.recen.wepost.common.BaseActivity;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{
    int lastSelectedPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.main_tab_info,"首页")).setActiveColor(R.color.colorPrimary)
                .addItem(new BottomNavigationItem(R.drawable.main_tab_treasure,"百宝箱")).setActiveColor(R.color.colorPrimary)
                .addItem(new BottomNavigationItem(R.drawable.main_tab_me,"我")).setActiveColor(R.color.colorPrimary)
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
