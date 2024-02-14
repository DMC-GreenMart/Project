package com.sunbeaminfo.moviereview.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sunbeaminfo.moviereview.fragments.MovieListFragment;
import com.sunbeaminfo.moviereview.fragments.MovieReviewListFragment;
import com.sunbeaminfo.moviereview.fragments.MySharedReviewsFragment;
import com.sunbeaminfo.moviereview.fragments.ProfileFragment;

public class MainFragmentAdapter extends FragmentStateAdapter {
    public MainFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MovieListFragment();
            case 1:
                return new MovieReviewListFragment();
            case 2:
                return new MySharedReviewsFragment();
            case 3:
                return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
