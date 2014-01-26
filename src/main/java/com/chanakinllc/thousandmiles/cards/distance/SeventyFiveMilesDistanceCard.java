package com.chanakinllc.thousandmiles.cards.distance;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class SeventyFiveMilesDistanceCard extends DistanceCard {
    @Override
    public int getDistance() {
        return 75;
    }

    @Override
    public CardType getCardType() {
        return CardType.SEVENTY_FIVE_MILES;
    }

    @Override
    public boolean distanceOkUnderSpeedLimit() {
        return false;
    }
}
