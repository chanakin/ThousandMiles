package com.chanakinllc.thousandmiles.cards.remedies;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class RollCard extends RemedyCard {
    @Override
    public CardType getCardType() {
        return CardType.ROLL;
    }

    @Override
    public CardType [] getCardTypesThatArePlayableOn() {
        return new CardType [] {CardType.STOP, CardType.GASOLINE, CardType.REPAIRS, CardType.SPARE_TIRE  };
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.roll;
    }

    public RollCard() {}
    public RollCard(Parcel in) {

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
        public RollCard createFromParcel(Parcel parcel) {
            return new RollCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new RollCard[i];
        }
    };
}
