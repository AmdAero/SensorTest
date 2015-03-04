package com.example.brecht.sensortest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Brecht on 3/03/2015.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {
    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new Stopwatch();
            case 1:
                return new Login();
            //case 2:
                //return new Rotation();
            default:
                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
