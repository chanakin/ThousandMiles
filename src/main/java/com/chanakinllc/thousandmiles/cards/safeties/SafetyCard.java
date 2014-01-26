package com.chanakinllc.thousandmiles.cards.safeties;

import com.chanakinllc.thousandmiles.cards.CardCategory;
import com.chanakinllc.thousandmiles.cards.CardPile;
import com.chanakinllc.thousandmiles.cards.CardType;
import com.chanakinllc.thousandmiles.cards.Card;

/**
 * Created by chan on 1/26/14.
 */
public abstract class SafetyCard extends Card {

    public abstract CardType [] getCardTypesWhichCorrespondToThisSafety();

    @Override
    public CardPile getPileType() {
        return CardPile.SAFETY;
    }

    @Override
    public CardCategory getCardCategory() {
        return CardCategory.SAFETY;
    }
}