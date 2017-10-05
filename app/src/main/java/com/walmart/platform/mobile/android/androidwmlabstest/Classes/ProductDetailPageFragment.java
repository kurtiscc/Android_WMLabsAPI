package com.walmart.platform.mobile.android.androidwmlabstest.Classes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Product;
import com.walmart.platform.mobile.android.androidwmlabstest.R;

/**
 * Created by kchris6 on 10/2/17.
 */

public class ProductDetailPageFragment extends Fragment {

    private Product product;

    public ProductDetailPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        product = bundle.getParcelable("product-details");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);
        setHasOptionsMenu(true);
        ImageView img = (ImageView) rootView.findViewById(R.id.imageV);
            Context mContext = getActivity().getApplicationContext();

            ((TextView) rootView.findViewById(R.id.product_name)).setText(product.getProductName());
            Picasso.with(mContext).load(product.getProductImage()).into(img);
            ((TextView) rootView.findViewById(R.id.product_rating)).setText(String.valueOf(product.getReviewRating()));
            ((TextView) rootView.findViewById(R.id.product_price)).setText(product.getPrice());
            ((TextView) rootView.findViewById(R.id.product_rating_count)).setText(String.valueOf(product.getReviewCount()));

        return rootView;
    }
}
