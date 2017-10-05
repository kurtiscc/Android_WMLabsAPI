package com.walmart.platform.mobile.android.androidwmlabstest.utils;

/**
 * Created by kchris6 on 10/1/17.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class ScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;

    public ScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int i, int j) {
        super.onScrolled(recyclerView, i, j);

        int visibleProductCount = layoutManager.getChildCount();
        int totalProductCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleProductCount + firstVisibleItemPosition) >= totalProductCount
                    && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }

    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
