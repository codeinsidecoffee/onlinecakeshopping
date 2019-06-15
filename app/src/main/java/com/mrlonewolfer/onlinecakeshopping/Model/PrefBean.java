package com.mrlonewolfer.onlinecakeshopping.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PrefBean implements Parcelable {
    String email,pass,status,usertype,userid;


    public PrefBean() {
    }


    protected PrefBean(Parcel in) {
        email = in.readString();
        pass = in.readString();
        status = in.readString();
        usertype = in.readString();
        userid = in.readString();
    }

    public static final Creator<PrefBean> CREATOR = new Creator<PrefBean>() {
        @Override
        public PrefBean createFromParcel(Parcel in) {
            return new PrefBean(in);
        }

        @Override
        public PrefBean[] newArray(int size) {
            return new PrefBean[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(pass);
        dest.writeString(status);
        dest.writeString(usertype);
        dest.writeString(userid);
    }

    @Override
    public String toString() {
        return "PrefBean{" +
                "email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", status='" + status + '\'' +
                ", usertype='" + usertype + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
