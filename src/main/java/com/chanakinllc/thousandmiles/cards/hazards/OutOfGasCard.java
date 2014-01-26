package com.chanakinllc.thousandmiles.cards.hazards;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class OutOfGasCard extends HazardCard {
    @Override
    public CardType getCardType() {
        return CardType.OUT_OF_GAS;
    }

    @Override
    protected CardType getRemedy() {
        return CardType.GASOLINE;
    }

    @Override
    protected CardType getSafety() {
        return CardType.EXTRA_TANK;
    }
}
