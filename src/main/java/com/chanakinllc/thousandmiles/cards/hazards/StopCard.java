package com.chanakinllc.thousandmiles.cards.hazards;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class StopCard extends HazardCard {
    @Override
    public CardType getCardType() {
        return CardType.STOP;
    }

    @Override
    protected CardType getRemedy() {
        return CardType.ROLL;
    }

    @Override
    protected CardType getSafety() {
        return CardType.RIGHT_OF_WAY;
    }
}
