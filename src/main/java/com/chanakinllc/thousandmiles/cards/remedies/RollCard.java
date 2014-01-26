package com.chanakinllc.thousandmiles.cards.remedies;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class RollCard extends RemedyCard {
    @Override
    public CardType getCardType() {
        return CardType.ROLL;
    }

    @Override
    public CardType getHazardToRemedy() {
        return CardType.STOP;
    }
}
