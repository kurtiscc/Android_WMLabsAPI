package com.walmart.platform.mobile.android.androidwmlabstest.Data.Model;

/**
 * Created by kchris6 on 9/28/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product implements Parcelable{

    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("productImage")
    @Expose
    private String productImage;
    @SerializedName("reviewRating")
    @Expose
    private Double reviewRating;
    @SerializedName("reviewCount")
    @Expose
    private Integer reviewCount;
    @SerializedName("inStock")
    @Expose
    private Boolean inStock;

    public Product() {
    }

    public Product(List<Product> products) {
    }

    public Product(Parcel source) {
//        this.setInStock((Boolean) source.readValue(null));
        this.setLongDescription(source.readString());
        this.setPrice(source.readString());
        this.setProductId(source.readString());
        this.setProductImage(source.readString());
        this.setProductName(source.readString());
        this.setShortDescription(source.readString());
        this.setReviewCount(source.readInt());
        this.setReviewRating(source.readDouble());

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Double getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(Double reviewRating) {
        this.reviewRating = reviewRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getLongDescription());
        dest.writeString(this.getPrice());
        dest.writeString(this.getProductId());
        dest.writeString(this.getProductImage());
        dest.writeString(this.getProductName());
        dest.writeString(this.getShortDescription());
        dest.writeInt(this.getReviewCount());
        dest.writeDouble(this.getReviewRating());
       // dest.writeValue(this.getInStock());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}