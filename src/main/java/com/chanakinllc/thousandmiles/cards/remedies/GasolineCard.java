package com.chanakinllc.thousandmiles.cards.remedies;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class GasolineCard extends RemedyCard {
    @Override
    public CardType getCardType() {
        return CardType.GASOLINE;
    }

    @Override
    public CardType [] getCardTypesThatArePlayableOn() {
        return new CardType[] { CardType.OUT_OF_GAS };
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.gasoline;
    }

    public GasolineCard(Parcel in) {

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
        public GasolineCard createFromParcel(Parcel parcel) {
            return new GasolineCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new GasolineCard[i];
        }
    };
}
