package com.jayshreegopalapps.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<NewsModel> arrayList;
    public ViewPagerAdapter(FragmentManager fm, ArrayList<NewsModel> arrayList) {
        super(fm);
        this.arrayList = arrayList;
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ChildFragment childFragment= new ChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("parent", "hello");
        childFragment.setArguments(bundle);
        return childFragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
