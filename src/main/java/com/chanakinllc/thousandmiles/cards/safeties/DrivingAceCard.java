package com.chanakinllc.thousandmiles.cards.safeties;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class DrivingAceCard extends SafetyCard {

    @Override
    public CardType getCardType() {
        return CardType.DRIVING_ACE;
    }

    @Override
    public CardType[] getCardTypesPrevented() {
        return new CardType[] {CardType.ACCIDENT};
    }

    @Override
    public CardType[] getCardTypesWithSameFunction() {
        return new CardType[] {CardType.REPAIRS};
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.driving_ace;
    }

    public DrivingAceCard() {}

    public DrivingAceCard(Parcel in) {

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
        public DrivingAceCard createFromParcel(Parcel parcel) {
            return new DrivingAceCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new DrivingAceCard[i];
        }
    };
}
