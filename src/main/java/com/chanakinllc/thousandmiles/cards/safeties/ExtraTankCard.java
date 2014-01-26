package com.chanakinllc.thousandmiles.cards.safeties;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class ExtraTankCard extends SafetyCard {

    @Override
    public CardType getCardType() {
        return CardType.EXTRA_TANK;
    }

    @Override
    public CardType[] getCardTypesWhichCorrespondToThisSafety() {
        CardType [] correspondingCardTypes = { CardType.GASOLINE };
        return correspondingCardTypes;
    }
}
