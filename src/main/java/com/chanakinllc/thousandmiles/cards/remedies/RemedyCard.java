package com.chanakinllc.thousandmiles.cards.remedies;

import com.chanakinllc.thousandmiles.cards.CardCategory;
import com.chanakinllc.thousandmiles.cards.CardPile;
import com.chanakinllc.thousandmiles.cards.CardType;
import com.chanakinllc.thousandmiles.cards.Card;

/**
 * Created by chan on 1/26/14.
 */
public abstract class RemedyCard extends Card {

    @Override
    public CardPile getPileType() {
        return CardPile.BATTLE;
    }

    @Override
    public CardCategory getCardCategory() {
        return CardCategory.REMEDY;
    }

    public abstract CardType getHazardToRemedy();

}
