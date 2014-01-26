package com.chanakinllc.thousandmiles.cards.remedies;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class GasolineCard extends RemedyCard {
    @Override
    public CardType getCardType() {
        return CardType.GASOLINE;
    }

    @Override
    public CardType getHazardToRemedy() {
        return CardType.OUT_OF_GAS;
    }
}
