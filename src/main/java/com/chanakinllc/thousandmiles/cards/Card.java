package com.chanakinllc.thousandmiles.cards;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chan on 1/25/14.
 */
public abstract class Card implements Parcelable {

    public abstract CardCategory getCardCategory();

    public abstract CardPile getPileType();

    public abstract CardType getCardType();

    public abstract CardType [] getCardTypesThatArePlayableOn();

    public abstract int getCardImageResourceId();
}
