package com.example.courseapp.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    String  email, password, uId;



    public User(String email, String phoneNumber, String guardName) {
    }



    protected User(Parcel in) {

        email = in.readString();
        password = in.readString();
        uId = in.readString();

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




    public User() {
    }


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(uId);

    }
}