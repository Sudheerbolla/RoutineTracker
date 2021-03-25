package com.routinetracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class UsersModel implements Parcelable {

    public String name = "", emailAddress = "", userType = "";
    public long id = -1;

    public UsersModel() {
    }

    protected UsersModel(Parcel in) {
        name = in.readString();
        emailAddress = in.readString();
        userType = in.readString();
        id = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(emailAddress);
        dest.writeString(userType);
        dest.writeLong(id);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<UsersModel> CREATOR = new Creator<UsersModel>() {
        @Override
        public UsersModel createFromParcel(Parcel in) {
            return new UsersModel(in);
        }

        @Override
        public UsersModel[] newArray(int size) {
            return new UsersModel[size];
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        UsersModel transferModel = ((UsersModel) obj);
        assert transferModel != null;
        return (transferModel.id != -1 && transferModel.id == this.id) && (transferModel.emailAddress.equalsIgnoreCase(this.emailAddress));
    }
}
