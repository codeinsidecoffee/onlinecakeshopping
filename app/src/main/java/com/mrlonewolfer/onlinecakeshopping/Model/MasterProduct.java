
package com.mrlonewolfer.onlinecakeshopping.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;


@SuppressWarnings("unused")
public class MasterProduct {

    @Expose
    private List<Cakerate> cakerate;
    @SerializedName("category_image")
    private String categoryImage;
    @SerializedName("category_name")
    private String categoryName;
    @Expose
    private String cid;
    @SerializedName("product_description")
    private String productDescription;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("product_title")
    private String productTitle;
    @SerializedName("product_type_name")
    private String productTypeName;

    public List<Cakerate> getCakerate() {
        return cakerate;
    }

    public void setCakerate(List<Cakerate> cakerate) {
        this.cakerate = cakerate;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    @SuppressWarnings("unused")
    public static class Cakerate {

        @Expose
        private String prate;
        @Expose
        private String pweight;


        public String getPrate() {
            return prate;
        }

        public void setPrate(String prate) {
            this.prate = prate;
        }

        public String getPweight() {
            return pweight;
        }

        public void setPweight(String pweight) {
            this.pweight = pweight;
        }

        @Override
        public String toString() {
            return "Cakerate{" +
                    "prate='" + prate + '\'' +
                    ", pweight='" + pweight + '\'' +
                    '}';
        }
    }
}
