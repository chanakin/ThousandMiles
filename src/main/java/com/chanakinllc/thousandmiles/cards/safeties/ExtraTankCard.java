package com.chanakinllc.thousandmiles.cards.safeties;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class ExtraTankCard extends SafetyCard {

    @Override
    public CardType getCardType() {
        return CardType.EXTRA_TANK;
    }

    @Override
    public CardType[] getCardTypesPrevented() {
        return new CardType[] { CardType.GASOLINE };
    }

    @Override
    public CardType[] getCardTypesWithSameFunction() {
        return new CardType[] { CardType.GASOLINE };
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.extra_tank;
    }

    public ExtraTankCard() {}

    public ExtraTankCard(Parcel in) {

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
        public ExtraTankCard createFromParcel(Parcel parcel) {
            return new ExtraTankCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new ExtraTankCard[i];
        }
    };
}
