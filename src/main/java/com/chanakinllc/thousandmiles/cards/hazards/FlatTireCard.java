package com.chanakinllc.thousandmiles.cards.hazards;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class FlatTireCard extends HazardCard {
    @Override
    public CardType getCardType() {
        return CardType.FLAT_TIRE;
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.flat_tire;
    }

    public FlatTireCard() {

    }

    public FlatTireCard(Parcel in) {

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
        public FlatTireCard createFromParcel(Parcel parcel) {
            return new FlatTireCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new FlatTireCard[i];
        }
    };
}
