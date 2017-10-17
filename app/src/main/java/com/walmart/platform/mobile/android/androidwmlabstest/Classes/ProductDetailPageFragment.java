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
import com.walmart.platform.mobile.android.androidwmlabstest.R;

/**
 * Created by kchris6 on 10/2/17.
 */

public class ProductDetailPageFragment extends Fragment {
    //public static final ImageView productImage = null;
    public static final String productName = "name";
    public static final String productPrice = "price";
    public static final String productRating = "rating";
    public static final String productReviewCount = "reviewCount";
    public static final String productImage = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        ((TextView) rootView.findViewById(R.id.product_name)).setText(getArguments().getString(productName));
        ((TextView) rootView.findViewById(R.id.product_price)).setText(getArguments().getString(productPrice));
        ((TextView) rootView.findViewById(R.id.product_rating)).setText(getArguments().getString(productRating));
        ((TextView) rootView.findViewById(R.id.product_rating_count)).setText(getArguments().getString(productReviewCount));
        ImageView img = (ImageView) rootView.findViewById(R.id.imageV);
        Context mContext = getActivity().getApplicationContext();
        Picasso.with(mContext).load(getArguments().getString(productImage)).into(img);

        return rootView;

    }
}
