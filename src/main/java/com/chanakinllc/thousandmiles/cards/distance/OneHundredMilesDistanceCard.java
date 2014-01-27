package com.chanakinllc.thousandmiles.cards.distance;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class OneHundredMilesDistanceCard extends DistanceCard {
    @Override
    public int getDistance() {
        return 100;
    }

    @Override
    public CardType getCardType() {
        return CardType.ONE_HUNDRED_MILES;
    }

    @Override
    public boolean distanceOkUnderSpeedLimit() {
        return false;
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.one_hundred_miles;
    }

    public OneHundredMilesDistanceCard(Parcel in) {

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
        public OneHundredMilesDistanceCard createFromParcel(Parcel parcel) {
            return new OneHundredMilesDistanceCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new OneHundredMilesDistanceCard[i];
        }
    };
}
