
package com.mrlonewolfer.onlinecakeshopping.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class UserInfo {

    @Expose
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @SuppressWarnings("unused")
    public static class User implements Parcelable {
        public User() {
        }

        @Expose
        private String address;
        @Expose
        private String city;
        @Expose
        private String country;
        @SerializedName("delivered_pname")
        private String deliveredPname;
        @Expose
        private String email;
        @Expose
        private String fname;
        @Expose
        private String id;
        @Expose
        private String landmark;
        @SerializedName("last_visit")
        private String lastVisit;
        @Expose
        private String lname;
        @Expose
        private String mobile;
        @Expose
        private String pass;
        @Expose
        private String pincode;
        @SerializedName("shipping_address")
        private String shippingAddress;
        @Expose
        private String state;
        @Expose
        private String status;
        @SerializedName("user_image")
        private String userImage;
        @SerializedName("user_type")
        private String userType;

        public User(Parcel in) {
            address = in.readString();
            city = in.readString();
            country = in.readString();
            deliveredPname = in.readString();
            email = in.readString();
            fname = in.readString();
            id = in.readString();
            landmark = in.readString();
            lastVisit = in.readString();
            lname = in.readString();
            mobile = in.readString();
            pass = in.readString();
            pincode = in.readString();
            shippingAddress = in.readString();
            state = in.readString();
            status = in.readString();
            userImage = in.readString();
            userType = in.readString();
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDeliveredPname() {
            return deliveredPname;
        }

        public void setDeliveredPname(String deliveredPname) {
            this.deliveredPname = deliveredPname;
        }

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getLastVisit() {
            return lastVisit;
        }

        public void setLastVisit(String lastVisit) {
            this.lastVisit = lastVisit;
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

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(address);
            dest.writeString(city);
            dest.writeString(country);
            dest.writeString(deliveredPname);
            dest.writeString(email);
            dest.writeString(fname);
            dest.writeString(id);
            dest.writeString(landmark);
            dest.writeString(lastVisit);
            dest.writeString(lname);
            dest.writeString(mobile);
            dest.writeString(pass);
            dest.writeString(pincode);
            dest.writeString(shippingAddress);
            dest.writeString(state);
            dest.writeString(status);
            dest.writeString(userImage);
            dest.writeString(userType);
        }
    }
}
