package com.walmart.platform.mobile.android.androidwmlabstest.Classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Product;
import com.walmart.platform.mobile.android.androidwmlabstest.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kchris6 on 10/2/17.
 */

public class ProductDetailsPagerActivity  extends Fragment {

    private int currPosition;
    private ArrayList<Product> products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_screen_slide, container, false);
        ViewPager mViewPager = (ViewPager) v.findViewById(R.id.pager);
        currPosition = getArguments().getInt("curr-position");
        products = getArguments().getParcelableArrayList("product-details");

        ScreenSlidePagerAdapter screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager(), products);
        mViewPager.setAdapter(screenSlidePagerAdapter);
        mViewPager.setCurrentItem(currPosition);
        return v;
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<Product> products;

        private ScreenSlidePagerAdapter(FragmentManager fm, List<Product> products) {
            super(fm);
            this.products = products;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ProductDetailPageFragment();
            Bundle args = new Bundle();
            args.putString(ProductDetailPageFragment.productName, products.get(position).getProductName());
            args.putString(ProductDetailPageFragment.productPrice, products.get(position).getPrice());
            args.putString(ProductDetailPageFragment.productRating, String.valueOf(products.get(position).getReviewRating()));
            args.putString(ProductDetailPageFragment.productReviewCount, String.valueOf(products.get(position).getReviewCount()));
            args.putString(ProductDetailPageFragment.productImage, products.get(position).getProductImage());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return products.size();
        }
    }
}


