
package com.mrlonewolfer.onlinecakeshopping.Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class QtyTypeInfo {

    @Expose
    private List<Qtytype> qtytype;

    public List<Qtytype> getQtytype() {
        return qtytype;
    }

    public void setQtytype(List<Qtytype> qtytype) {
        this.qtytype = qtytype;
    }

    @SuppressWarnings("unused")
    public static class Qtytype {

        @SerializedName("product_qty_id")
        private String productQtyId;
        @SerializedName("product_qty_type")
        private String productQtyType;

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

    }
}
