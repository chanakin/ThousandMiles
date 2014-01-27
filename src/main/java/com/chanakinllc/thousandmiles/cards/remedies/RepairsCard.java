package com.chanakinllc.thousandmiles.cards.remedies;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class RepairsCard extends RemedyCard {

    @Override
    public CardType getCardType() {
        return CardType.REPAIRS;
    }

    @Override
    public CardType [] getCardTypesThatArePlayableOn() {
        return new CardType[] { CardType.ACCIDENT};
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.repairs;
    }

    public RepairsCard(Parcel in) {

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
        public RepairsCard createFromParcel(Parcel parcel) {
            return new RepairsCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new RepairsCard[i];
        }
    };
}
