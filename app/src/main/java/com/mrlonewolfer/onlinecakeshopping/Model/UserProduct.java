
package com.mrlonewolfer.onlinecakeshopping.Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class UserProduct {

    @SerializedName("ProductList")
    private List<ProductList> productList;

    public List<ProductList> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductList> productList) {
        this.productList = productList;
    }

    @SuppressWarnings("unused")
    public static class ProductList {

        @SerializedName("category_image")
        private String categoryImage;
        @SerializedName("category_name")
        private String categoryName;
        @Expose
        private String cid;
        @SerializedName("DetailPrice")
        private List<MasterProduct.Cakerate> detailPrice;
        @SerializedName("product_description")
        private String productDescription;
        @SerializedName("product_id")
        private String productId;
        @SerializedName("product_image")
        private String productImage;
        @SerializedName("product_status")
        private String productStatus;
        @SerializedName("product_title")
        private String productTitle;
        @SerializedName("product_type_name")
        private String productTypeName;

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

        public List<MasterProduct.Cakerate> getDetailPrice() {
            return detailPrice;
        }

        public void setDetailPrice(List<MasterProduct.Cakerate> detailPrice) {
            this.detailPrice = detailPrice;
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

        public String getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
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

    }

    @SuppressWarnings("unused")
    public static class DetailPrice {

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

    }
}
