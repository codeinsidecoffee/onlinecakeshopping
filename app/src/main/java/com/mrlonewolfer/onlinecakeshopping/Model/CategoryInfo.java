
package com.mrlonewolfer.onlinecakeshopping.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;


@SuppressWarnings("unused")
public class CategoryInfo {

    @Expose
    private List<Category> category;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    @SuppressWarnings("unused")
    public static class Category implements Parcelable {

        @SerializedName("category_image")
        private String categoryImage;
        @SerializedName("category_name")
        private String categoryName;
        @Expose
        private String cid;

        public Category(Parcel in) {
            categoryImage = in.readString();
            categoryName = in.readString();
            cid = in.readString();
        }

        public static final Creator<Category> CREATOR = new Creator<Category>() {
            @Override
            public Category createFromParcel(Parcel in) {
                return new Category(in);
            }

            @Override
            public Category[] newArray(int size) {
                return new Category[size];
            }
        };

        public Category() {

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(categoryImage);
            dest.writeString(categoryName);
            dest.writeString(cid);
        }

        @Override
        public String toString() {
            return "Category{" +
                    "categoryImage='" + categoryImage + '\'' +
                    ", categoryName='" + categoryName + '\'' +
                    ", cid='" + cid + '\'' +
                    '}';
        }
    }
}
