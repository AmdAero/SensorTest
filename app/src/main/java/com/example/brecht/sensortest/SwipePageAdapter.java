package com.example.brecht.sensortest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Brecht on 3/03/2015.
 */
public class SwipePageAdapter extends FragmentPagerAdapter {
    public SwipePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new Stopwatch();
            case 1:
                return new RootLoginFragment();
            case 2:
                return new RotationFragment();
            default:
                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
