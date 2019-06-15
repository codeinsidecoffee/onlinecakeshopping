
package com.mrlonewolfer.onlinecakeshopping.Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class OrderInfo {

    @SerializedName("OrderDetails")
    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @SuppressWarnings("unused")
    public static class OrderDetail {

        @Expose
        private String email;
        @Expose
        private String fname;
        @Expose
        private String lname;
        @Expose
        private String mobile;
        @SerializedName("order_amount")
        private String orderAmount;
        @SerializedName("order_date")
        private String orderDate;
        @SerializedName("order_id")
        private String orderId;
        @SerializedName("order_status")
        private String orderStatus;
        @SerializedName("product_details")
        private List<ProductDetail> productDetails;
        @SerializedName("total_product")
        private String totalProduct;
        @SerializedName("user_id")
        private String userId;
        @SerializedName("user_image")
        private String userImage;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public List<ProductDetail> getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(List<ProductDetail> productDetails) {
            this.productDetails = productDetails;
        }

        public String getTotalProduct() {
            return totalProduct;
        }

        public void setTotalProduct(String totalProduct) {
            this.totalProduct = totalProduct;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

    }

    @SuppressWarnings("unused")
    public static class ProductDetail {

        @SerializedName("category_name")
        private String categoryName;
        @Expose
        private String cid;
        @SerializedName("order_details_id")
        private String orderDetailsId;
        @SerializedName("product_id")
        private String productId;
        @SerializedName("product_image")
        private String productImage;
        @SerializedName("product_qty_id")
        private String productQtyId;
        @SerializedName("product_qty_type")
        private String productQtyType;
        @SerializedName("product_status")
        private String productStatus;
        @SerializedName("product_title")
        private String productTitle;
        @SerializedName("product_type_name")
        private String productTypeName;
        @SerializedName("purchase_amount")
        private String purchaseAmount;
        @SerializedName("receive_date")
        private String receiveDate;
        @SerializedName("receive_time")
        private String receiveTime;
        @SerializedName("total_unit")
        private String totalUnit;
        @SerializedName("unit_rate")
        private String unitRate;

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

        public String getOrderDetailsId() {
            return orderDetailsId;
        }

        public void setOrderDetailsId(String orderDetailsId) {
            this.orderDetailsId = orderDetailsId;
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

        public String getProductQtyId() {
            return productQtyId;
        }

        public void setProductQtyId(String productQtyId) {
            this.productQtyId = productQtyId;
        }

        public String getProductQtyType() {
            return productQtyType;
        }

        public void setProductQtyType(String productQtyType) {
            this.productQtyType = productQtyType;
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

        public String getPurchaseAmount() {
            return purchaseAmount;
        }

        public void setPurchaseAmount(String purchaseAmount) {
            this.purchaseAmount = purchaseAmount;
        }

        public String getReceiveDate() {
            return receiveDate;
        }

        public void setReceiveDate(String receiveDate) {
            this.receiveDate = receiveDate;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getTotalUnit() {
            return totalUnit;
        }

        public void setTotalUnit(String totalUnit) {
            this.totalUnit = totalUnit;
        }

        public String getUnitRate() {
            return unitRate;
        }

        public void setUnitRate(String unitRate) {
            this.unitRate = unitRate;
        }

    }
}
