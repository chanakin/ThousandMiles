package com.chanakinllc.thousandmiles.cards.hazards;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class FlatTireCard extends HazardCard {

    @Override
    protected CardType getSafety() {
        return CardType.PUNCTURE_PROOF;
    }

    @Override
    protected CardType getRemedy() {
        return CardType.SPARE_TIRE;
    }

    @Override
    public CardType getCardType() {
        return CardType.FLAT_TIRE;
    }
}
