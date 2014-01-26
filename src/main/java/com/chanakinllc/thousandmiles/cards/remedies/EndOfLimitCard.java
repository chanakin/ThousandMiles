package com.chanakinllc.thousandmiles.cards.remedies;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class EndOfLimitCard extends RemedyCard {
    @Override
    public CardType getCardType() {
        return CardType.END_OF_LIMIT;
    }

    @Override
    public CardType getHazardToRemedy() {
        return CardType.SPEED_LIMIT;
    }
}
