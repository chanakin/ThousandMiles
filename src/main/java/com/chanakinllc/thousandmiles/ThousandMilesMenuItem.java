package com.chanakinllc.thousandmiles;

import android.app.Fragment;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chan on 1/26/14.
 */
public class ThousandMilesMenuItem implements Parcelable {

    private String itemText;

    public ThousandMilesMenuItem(String itemText) {
        this.itemText = itemText;
    }

    public String getItemText() {
        return itemText;
    }

    public ThousandMilesMenuItem(Parcel in) {
        itemText = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemText);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public ThousandMilesMenuItem createFromParcel(Parcel parcel) {
            return new ThousandMilesMenuItem(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new ThousandMilesMenuItem[i];
        }
    };

    @Override
    public String toString() {
        return itemText;
    }
}
