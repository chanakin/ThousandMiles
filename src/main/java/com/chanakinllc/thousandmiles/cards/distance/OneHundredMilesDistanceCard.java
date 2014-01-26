package com.chanakinllc.thousandmiles.cards.distance;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class OneHundredMilesDistanceCard extends DistanceCard {
    @Override
    public int getDistance() {
        return 100;
    }

    @Override
    public CardType getCardType() {
        return CardType.ONE_HUNDRED_MILES;
    }

    @Override
    public boolean distanceOkUnderSpeedLimit() {
        return false;
    }
}
