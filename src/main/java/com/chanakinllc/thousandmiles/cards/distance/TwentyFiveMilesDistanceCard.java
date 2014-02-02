package com.chanakinllc.thousandmiles.cards.distance;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardPile;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class TwentyFiveMilesDistanceCard extends DistanceCard {
    @Override
    public int getDistance() {
        return 25;
    }

    @Override
    public CardPile getPileType() {
        return CardPile.TWENTY_FIVE_DISTANCE;
    }

    @Override
    public CardType getCardType() {
        return CardType.TWENTY_FIVE_MILES;
    }

    @Override
    public boolean distanceOkUnderSpeedLimit() {
        return true;
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.twenty_five_miles;
    }

    public TwentyFiveMilesDistanceCard(Parcel in) {

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
        public TwentyFiveMilesDistanceCard createFromParcel(Parcel parcel) {
            return new TwentyFiveMilesDistanceCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new TwentyFiveMilesDistanceCard[i];
        }
    };
}
