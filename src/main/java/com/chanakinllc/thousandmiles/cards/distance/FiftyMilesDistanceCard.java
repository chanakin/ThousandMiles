package com.chanakinllc.thousandmiles.cards.distance;

import com.chanakinllc.thousandmiles.cards.CardType;

/**
 * Created by chan on 1/26/14.
 */
public class FiftyMilesDistanceCard extends DistanceCard {

    @Override
    public int getDistance() {
        return 50;
    }

    @Override
    public CardType getCardType() {
        return CardType.FIFTY_MILES;
    }

    @Override
    public boolean distanceOkUnderSpeedLimit() {
        return true;
    }
}

