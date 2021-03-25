package com.routinetracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class LocationModel implements Parcelable {

    public long id = -1, userId, timestamp;
    public double latitude, longitude;

    public LocationModel() {
    }

    protected LocationModel(Parcel in) {
        id = in.readLong();
        userId = in.readLong();
        timestamp = in.readLong();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(userId);
        dest.writeLong(timestamp);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        LocationModel transferModel = ((LocationModel) obj);
        assert transferModel != null;
        return (transferModel.id != -1 && transferModel.id == this.id) &&
                (transferModel.userId == this.userId) &&
                (transferModel.timestamp == this.timestamp);
    }
}
