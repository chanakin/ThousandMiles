package com.chanakinllc.thousandmiles.cards.distance;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class TwoHundredMilesDistanceCard extends DistanceCard {
    @Override
    public int getDistance() {
        return 200;
    }

    @Override
    public CardType getCardType() {
        return CardType.TWO_HUNDRED_MILES;
    }

    @Override
    public boolean distanceOkUnderSpeedLimit() {
        return false;
    }
}
