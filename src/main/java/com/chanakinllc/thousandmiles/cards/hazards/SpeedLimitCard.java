package com.chanakinllc.thousandmiles.cards.hazards;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardPile;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class SpeedLimitCard extends HazardCard {

    @Override
    public CardPile getPileType() {
        return CardPile.OPPONENT_SPEED;
    }

    @Override
    public CardType getCardType() {
        return CardType.SPEED_LIMIT;
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.speed_limit;
    }

    public SpeedLimitCard() {

    }

    public SpeedLimitCard(Parcel in) {

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
        public SpeedLimitCard createFromParcel(Parcel parcel) {
            return new SpeedLimitCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new SpeedLimitCard[i];
        }
    };
}
