package com.chanakinllc.thousandmiles.cards.hazards;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class AccidentHazardCard extends HazardCard {

    @Override
    protected CardType getRemedy() {
        return CardType.REPAIRS;
    }

    @Override
    public CardType getCardType() {
        return CardType.ACCIDENT;
    }

    @Override
    protected CardType getSafety() {
        return CardType.DRIVING_ACE;
    }
}
