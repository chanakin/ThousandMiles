package com.chanakinllc.thousandmiles.cards.safeties;

import android.os.Parcel;
import android.os.Parcelable;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class PunctureProofCard extends SafetyCard {
    @Override
    public CardType getCardType() {
        return CardType.PUNCTURE_PROOF;
    }

    @Override
    public CardType[] getCardTypesPrevented() {
        return new CardType[] { CardType.FLAT_TIRE };
    }

    @Override
    public CardType[] getCardTypesWithSameFunction() {
        return new CardType[] { CardType.SPARE_TIRE };
    }

    @Override
    public int getCardImageResourceId() {
        return R.drawable.puncture_proof;
    }

    public PunctureProofCard() {}

    public PunctureProofCard(Parcel in) {

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
        public PunctureProofCard createFromParcel(Parcel parcel) {
            return new PunctureProofCard(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new PunctureProofCard[i];
        }
    };

}
