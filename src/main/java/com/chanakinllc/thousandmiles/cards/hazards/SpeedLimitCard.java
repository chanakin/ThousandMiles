package com.chanakinllc.thousandmiles.cards.hazards;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class SpeedLimitCard extends HazardCard {
    @Override
    public CardType getCardType() {
        return CardType.SPEED_LIMIT;
    }

    @Override
    protected CardType getSafety() {
        return CardType.RIGHT_OF_WAY;
    }

    @Override
    protected CardType getRemedy() {
        return CardType.END_OF_LIMIT;
    }
}
