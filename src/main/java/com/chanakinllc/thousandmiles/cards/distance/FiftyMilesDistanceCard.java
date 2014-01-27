package com.chanakinllc.thousandmiles.cards.distance;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class FiftyMilesDistanceCard extends DistanceCard {

    @Override
    public int getDistance() {
        return 50;
    }

    @Override
    public CardType getCardType() {
        return CardType.FIFTY_MILES;
    }

    @Override
    public boolean distanceOkUnderSpeedLimit() {
        return true;
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.fifty_miles;
    }

    public FiftyMilesDistanceCard(Parcel in) {

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
        public FiftyMilesDistanceCard createFromParcel(Parcel parcel) {
            return new FiftyMilesDistanceCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new FiftyMilesDistanceCard[i];
        }
    };
}

