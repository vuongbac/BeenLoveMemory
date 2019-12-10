package com.example.beenlovememory.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.beenlovememory.fragment.DetailFragment;
import com.example.beenlovememory.fragment.WaveLoadingFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new WaveLoadingFragment();
        } else {
            return new DetailFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
