package com.chanakinllc.thousandmiles.cards.hazards;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class StopCard extends HazardCard {
    @Override
    public CardType getCardType() {
        return CardType.STOP;
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.stop;
    }

    public StopCard() {}

    public StopCard(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public StopCard createFromParcel(Parcel parcel) {
            return new StopCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new StopCard[i];
        }
    };
}
