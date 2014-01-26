package com.chanakinllc.thousandmiles.cards.safeties;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class DrivingAceCard extends SafetyCard {
    @Override
    public CardType getCardType() {
        return CardType.DRIVING_ACE;
    }

    @Override
    public CardType[] getCardTypesWhichCorrespondToThisSafety() {
        CardType [] correspondingCardTypes = {CardType.REPAIRS};
        return correspondingCardTypes;
    }
}
