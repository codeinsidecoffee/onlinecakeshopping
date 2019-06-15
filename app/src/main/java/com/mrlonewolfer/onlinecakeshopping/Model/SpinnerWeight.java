
package com.mrlonewolfer.onlinecakeshopping.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SpinnerWeight {

    @Expose
    private ListWeight listWeight;

    public ListWeight getListWeight() {
        return listWeight;
    }

    public void setListWeight(ListWeight listWeight) {
        this.listWeight = listWeight;
    }

    @SuppressWarnings("unused")
    public static class ListWeight {

        @SerializedName("product_qty_id")
        private String productQtyId;
        @SerializedName("product_qty_type")
        private String productQtyType;
        @SerializedName("product_rate")
        private String productRate;

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

        public String getProductRate() {
            return productRate;
        }

        public void setProductRate(String productRate) {
            this.productRate = productRate;
        }

    }
}
