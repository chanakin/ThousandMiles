package com.chanakinllc.thousandmiles.cards.remedies;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class EndOfLimitCard extends RemedyCard {
    @Override
    public CardType getCardType() {
        return CardType.END_OF_LIMIT;
    }

    @Override
    public CardType [] getCardTypesThatArePlayableOn() {
        return new CardType[] { CardType.SPEED_LIMIT };
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.end_of_limit;
    }

    public EndOfLimitCard(Parcel in) {

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
        public EndOfLimitCard createFromParcel(Parcel parcel) {
            return new EndOfLimitCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new EndOfLimitCard[i];
        }
    };
}
