package com.chanakinllc.thousandmiles.cards.safeties;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class PunctureProofCard extends SafetyCard {
    @Override
    public CardType getCardType() {
        return CardType.PUNCTURE_PROOF;
    }

    @Override
    public CardType[] getCardTypesWhichCorrespondToThisSafety() {
        CardType [] correspondingCardTypes = { CardType.FLAT_TIRE };
        return correspondingCardTypes;
    }
}
