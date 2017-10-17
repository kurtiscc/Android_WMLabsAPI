package com.walmart.platform.mobile.android.androidwmlabstest;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.walmart.platform.mobile.android.androidwmlabstest.Adapters.ProductsAdapter;
import com.walmart.platform.mobile.android.androidwmlabstest.Classes.Constants;
import com.walmart.platform.mobile.android.androidwmlabstest.Classes.ProductDetailsPagerActivity;
import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.GetProducts;
import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Product;
import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Remote.APIService;
import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Remote.ApiUtils;
import com.walmart.platform.mobile.android.androidwmlabstest.utils.PaginationAdapterCallback;
import com.walmart.platform.mobile.android.androidwmlabstest.utils.ScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class MainActivity extends FragmentActivity implements PaginationAdapterCallback{

    private static final String TAG = "MainActivity";

    private ProductsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private APIService mAPIService;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public static ArrayList<Product> products;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currPage = Constants.PAGE_START;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progrss_bar);
        mAPIService = ApiUtils.getAPIService();


        mAdapter = new ProductsAdapter(this, new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product, int position) {
                ArrayList<Product> productResultsList = mAdapter.getmProducts();
                ProductDetailsPagerActivity pagerActivity = new ProductDetailsPagerActivity();
                Bundle extras = new Bundle();
                extras.putParcelableArrayList("product-details", productResultsList );
                extras.putInt("curr-position", position);
                pagerActivity.setArguments(extras);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.container, pagerActivity, "swipe_view_fragment").commit();
//                Toast.makeText(MainActivity.this, "Item Clicked", Toast.LENGTH_LONG).show();
            }
        });

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new ScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currPage += 1;

                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return Constants.TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;}
            });
        callApi();
    }

    public void callApi() {
        mAPIService.postRequest(Constants.API_KEY,currPage,Constants.PAGE_SIZE).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {

               if(response.isSuccessful()) {
                   products = (ArrayList<Product>) response.body().getProducts();
                   progressBar.setVisibility(View.GONE);
                   mAdapter.updateProducts(products);

                   if (currPage <= Constants.TOTAL_PAGES) {
                       mAdapter.addLoadingFooter();
                   }
                   else {
                       isLastPage = true;
                   }
               }
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Unable to submit get request to API.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }

    private void loadNextPage() {

        mAPIService.postRequest(Constants.API_KEY,currPage,Constants.PAGE_SIZE).enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts> response) {
                mAdapter.removeLoadingFooter();
                isLoading = false;

                ArrayList<Product> products = (ArrayList<Product>) response.body().getProducts();
                mAdapter.updateProducts(products);

                if (currPage != Constants.TOTAL_PAGES) mAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Log.e(TAG, "Unable to submit get request to API.");
            }
        });
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentByTag("swipe_view_fragment");
        if(f!=null){
            fm.beginTransaction().remove(f).commit();
        }else{
            super.onBackPressed();
        }
    }

}
