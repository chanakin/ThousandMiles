package com.chanakinllc.thousandmiles.cards.hazards;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class OutOfGasCard extends HazardCard {
    @Override
    public CardType getCardType() {
        return CardType.OUT_OF_GAS;
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.out_of_gas;
    }

    public OutOfGasCard() {

    }

    public OutOfGasCard(Parcel in) {

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
        public OutOfGasCard createFromParcel(Parcel parcel) {
            return new OutOfGasCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new OutOfGasCard[i];
        }
    };
}
