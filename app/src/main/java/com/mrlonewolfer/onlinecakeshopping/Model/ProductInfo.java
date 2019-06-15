
package com.mrlonewolfer.onlinecakeshopping.Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ProductInfo {

    @Expose
    private List<Product> product;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    @SuppressWarnings("unused")
    public static class Product {

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
        @SerializedName("set_price_status")
        private String setPriceStatus;

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


        public String getSetPriceStatus() {
            return setPriceStatus;
        }

        public void setSetPriceStatus(String setPriceStatus) {
            this.setPriceStatus = setPriceStatus;
        }

    }
}
