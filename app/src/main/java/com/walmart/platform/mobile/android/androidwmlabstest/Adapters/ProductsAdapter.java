package com.walmart.platform.mobile.android.androidwmlabstest.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Product;
import com.walmart.platform.mobile.android.androidwmlabstest.R;
import com.walmart.platform.mobile.android.androidwmlabstest.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kchris6 on 9/29/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Product product, int position);
    }

    private ArrayList<Product> productResults;
    private final OnItemClickListener listener;
    // private PostProductListener mProductListener;
    private Context mContext;

    private boolean isLoadingAdded = false;

    private PaginationAdapterCallback mCallback;


    public ProductsAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        this.mCallback = (PaginationAdapterCallback) context;
        this.listener = listener;
        productResults = new ArrayList<>();
    }

    public ArrayList<Product> getmProducts() {
        return productResults;
    }

    public void setmProducts(ArrayList<Product> mProducts) {
        this.productResults = mProducts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.card_row, parent, false);
        viewHolder = new ProductVH(viewItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Product product = productResults.get(position); // Product
        final ProductVH productVH = (ProductVH) holder;

        productVH.bind(productVH, product, listener, position);
    }

    private DrawableRequestBuilder<String> loadImage(@NonNull String posterPath) {
        return Glide
                .with(mContext)
                .load(posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .crossFade();
    }

    @Override
    public int getItemCount() {
        return productResults == null ? 0 : productResults.size();
    }


    public void add(Product p) {
        productResults.add(p);
        notifyItemInserted(productResults.size() - 1);
    }

    public void updateProducts(List<Product> products) {
        for (Product product : products) {
            add(product);
        }
    }

    private Product getProduct(int adapterPosition) {
        return productResults.get(adapterPosition);
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Product());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = productResults.size() - 1;
        Product product = getProduct(position);

        if (product != null) {
            productResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    protected class ProductVH extends RecyclerView.ViewHolder {
        private TextView mProductNameTV;
        //private TextView mProductDescTV;
        private TextView mProductPriceTV;
        private ImageView mProductImg;
        private ProgressBar mProgress;

        public ProductVH(View itemView) {
            super(itemView);

            mProductNameTV = (TextView) itemView.findViewById(R.id.product_name_tv);
            //mProductDescTV = (TextView) itemView.findViewById(R.id.product_desc_tv);
            mProductPriceTV = (TextView) itemView.findViewById(R.id.product_price_tv);
            mProductImg = (ImageView) itemView.findViewById(R.id.product_image_iv);
            mProgress = (ProgressBar) itemView.findViewById(R.id.product_progress);
        }

        public void bind(final ProductVH productVH, final Product product, final OnItemClickListener listener, final int position) {

            productVH.mProductNameTV.setText(product.getProductName());
            //productVH.mProductDescTV.setText(product.getShortDescription());
            productVH.mProductPriceTV.setText(product.getPrice());
            // load product thumbnail
            loadImage(product.getProductImage())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            productVH.mProgress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            productVH.mProgress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(productVH.mProductImg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(product, position);
                }
            });

        }
    }

}

