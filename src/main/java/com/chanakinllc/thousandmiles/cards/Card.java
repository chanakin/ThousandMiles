package com.chanakinllc.thousandmiles.cards;

/**
 * Created by chan on 1/25/14.
 */
public abstract class Card {
    public abstract CardCategory getCardCategory();

    public abstract CardPile getPileType();

    public abstract CardType getCardType();
}
