package com.chanakinllc.thousandmiles.cards.hazards;

import com.chanakinllc.thousandmiles.cards.CardCategory;
import com.chanakinllc.thousandmiles.cards.CardPile;
import com.chanakinllc.thousandmiles.cards.CardType;
import com.chanakinllc.thousandmiles.cards.Card;

/**
 * Created by chan on 1/25/14.
 */
public abstract class HazardCard extends Card
{
    @Override
    public CardPile getPileType() {
        return CardPile.BATTLE;
    }

    @Override
    public CardCategory getCardCategory() {
        return CardCategory.HAZARD;
    }

    protected abstract CardType getRemedy();

    protected abstract CardType getSafety();
}
