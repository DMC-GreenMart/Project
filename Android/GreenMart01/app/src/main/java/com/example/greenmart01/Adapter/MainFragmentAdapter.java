package com.example.greenmart01.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.greenmart01.Fragment.CartFragment;
import com.example.greenmart01.Fragment.MyProfile;
import com.example.greenmart01.Fragment.OrdersStatus;
import com.example.greenmart01.Fragment.ProductsListFragments;


public class MainFragmentAdapter  extends FragmentStateAdapter
{


    public MainFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0 :
                return new ProductsListFragments();
            case  1 :
                return  new CartFragment();

            case 2 :
                return  new OrdersStatus();

            case  3 :
                return  new MyProfile();
        }

        return  new ProductsListFragments();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
