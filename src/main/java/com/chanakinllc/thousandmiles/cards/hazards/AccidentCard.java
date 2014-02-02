package com.chanakinllc.thousandmiles.cards.hazards;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class AccidentCard extends HazardCard {
    @Override
    public CardType getCardType() {
        return CardType.ACCIDENT;
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.accident;
    }

    public AccidentCard() {

    }

    public AccidentCard(Parcel in) {

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
        public AccidentCard createFromParcel(Parcel parcel) {
            return new AccidentCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new AccidentCard[i];
        }
    };
}
