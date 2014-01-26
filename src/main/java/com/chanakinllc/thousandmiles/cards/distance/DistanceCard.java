package com.chanakinllc.thousandmiles.cards.distance;

import com.chanakinllc.thousandmiles.cards.CardCategory;
import com.chanakinllc.thousandmiles.cards.CardPile;
import com.chanakinllc.thousandmiles.cards.Card;

/**
 * Created by chan on 1/25/14.
 */
public abstract class DistanceCard extends Card {

    public abstract int getDistance();

    public abstract boolean distanceOkUnderSpeedLimit();

    @Override
    public CardCategory getCardCategory() {
        return CardCategory.DISTANCE;
    }

    @Override
    public CardPile getPileType() {
        return CardPile.DISTANCE;
    }

}
