package com.chanakinllc.thousandmiles.cards.remedies;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class SpareTireCard extends RemedyCard {
    @Override
    public CardType getCardType() {
        return CardType.SPARE_TIRE;
    }

    @Override
    public CardType getHazardToRemedy() {
        return CardType.FLAT_TIRE;
    }
}
