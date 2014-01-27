package com.chanakinllc.thousandmiles.cards.safeties;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class RightOfWayCard extends SafetyCard {
    @Override
    public CardType getCardType() {
        return CardType.RIGHT_OF_WAY;
    }


    @Override
    public CardType[] getCardTypesPrevented() {
        return new CardType[] { CardType.STOP, CardType.SPEED_LIMIT };
    }

    @Override
    public CardType[] getCardTypesWithSameFunction() {
        return new CardType[] { CardType.ROLL, CardType.END_OF_LIMIT };
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.right_of_way;
    }

    public RightOfWayCard(Parcel in) {

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
        public RightOfWayCard createFromParcel(Parcel parcel) {
            return new RightOfWayCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new RightOfWayCard[i];
        }
    };
}
