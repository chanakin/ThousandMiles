package com.chanakinllc.thousandmiles.cards.safeties;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class RightOfWayCard extends SafetyCard {
    @Override
    public CardType getCardType() {
        return CardType.RIGHT_OF_WAY;
    }


    @Override
    public CardType[] getCardTypesWhichCorrespondToThisSafety() {
        CardType [] correspondingCardTypes = { CardType.ROLL, CardType.END_OF_LIMIT };
        return correspondingCardTypes;
    }
}
