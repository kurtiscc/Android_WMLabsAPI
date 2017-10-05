package com.walmart.platform.mobile.android.androidwmlabstest.Classes;

import android.nfc.Tag;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;


import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.GetProducts;
import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Product;
import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Remote.APIService;
import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Remote.ApiUtils;
import com.walmart.platform.mobile.android.androidwmlabstest.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kchris6 on 10/2/17.
 */

public class ProductDetailsPagerActivity  extends FragmentActivity {

    private static final String TAG = "ProductDetailPager";


    private ViewPager mPager;
    private Product product;
    private APIService mAPIService;
    private int currPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        mAPIService = ApiUtils.getAPIService();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            product = extras.getParcelable("product-details");
            currPage = Integer.parseInt(extras.getString("current-page"));

        }

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), product);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(mPager.getCurrentItem());
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private Product product;

        private ScreenSlidePagerAdapter(FragmentManager fm, Product data) {
            super(fm);
            this.product = data;
        }

        @Override
        public Fragment getItem(final int position) {
            ProductDetailPageFragment productDetailPageFragment = null;
               //if (position <= 1) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("product-details", product);
                    productDetailPageFragment = new ProductDetailPageFragment();
                    productDetailPageFragment.setArguments(bundle);
//               }
//                else {
//                   mAPIService.postRequest(Constants.API_KEY,currPage,Constants.PAGE_SIZE).enqueue(new Callback<GetProducts>() {
//                       @Override
//                       public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
//                           if (response.isSuccessful()) {
//                               ProductDetailPageFragment productDetailPageFragment = new ProductDetailPageFragment();
//                               ArrayList<Product> productsArray = (ArrayList<Product>) response.body().getProducts();
//                               for (int i = 0; i < productsArray.size(); i++) {
//                                   Bundle bundle = new Bundle();
//                                   bundle.putParcelable("product-details", productsArray.get(i));
//                                   productDetailPageFragment.setArguments(bundle);
//                               }
//                           } else Log.d("failed response", String.valueOf(response.code()));
//                       }
//
//                       @Override
//                       public void onFailure(Call<GetProducts> call, Throwable t) {
//                           Log.i(TAG, "Unable to submit request to API.");
//                       }
//                   });
//                }
            return productDetailPageFragment;
        }

        @Override
        public int getCount() {
            return Constants.TOTAL_ITEMS;
        }

    }
}


