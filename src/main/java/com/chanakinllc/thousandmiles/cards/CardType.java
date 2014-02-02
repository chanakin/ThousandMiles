package com.chanakinllc.thousandmiles.cards;

import com.chanakinllc.thousandmiles.R;
import com.chanakinllc.thousandmiles.cards.distance.FiftyMilesDistanceCard;
import com.chanakinllc.thousandmiles.cards.distance.OneHundredMilesDistanceCard;
import com.chanakinllc.thousandmiles.cards.distance.SeventyFiveMilesDistanceCard;
import com.chanakinllc.thousandmiles.cards.distance.TwentyFiveMilesDistanceCard;
import com.chanakinllc.thousandmiles.cards.distance.TwoHundredMilesDistanceCard;
import com.chanakinllc.thousandmiles.cards.hazards.AccidentCard;
import com.chanakinllc.thousandmiles.cards.hazards.FlatTireCard;
import com.chanakinllc.thousandmiles.cards.hazards.OutOfGasCard;
import com.chanakinllc.thousandmiles.cards.hazards.SpeedLimitCard;
import com.chanakinllc.thousandmiles.cards.hazards.StopCard;
import com.chanakinllc.thousandmiles.cards.remedies.EndOfLimitCard;
import com.chanakinllc.thousandmiles.cards.remedies.GasolineCard;
import com.chanakinllc.thousandmiles.cards.remedies.RepairsCard;
import com.chanakinllc.thousandmiles.cards.remedies.RollCard;
import com.chanakinllc.thousandmiles.cards.remedies.SpareTireCard;
import com.chanakinllc.thousandmiles.cards.safeties.DrivingAceCard;
import com.chanakinllc.thousandmiles.cards.safeties.ExtraTankCard;
import com.chanakinllc.thousandmiles.cards.safeties.PunctureProofCard;
import com.chanakinllc.thousandmiles.cards.safeties.RightOfWayCard;

/**
 * Created by chan on 1/26/14.
 */
public enum CardType {
    ACCIDENT(3),
    OUT_OF_GAS(3),
    FLAT_TIRE(3),
    STOP(5),
    SPEED_LIMIT(4),
    REPAIRS(6),
    GASOLINE(6),
    SPARE_TIRE(6),
    ROLL(14),
    END_OF_LIMIT(6),
    DRIVING_ACE(1),
    EXTRA_TANK(1),
    PUNCTURE_PROOF(1),
    RIGHT_OF_WAY(1),
    TWENTY_FIVE_MILES(10),
    FIFTY_MILES(10),
    SEVENTY_FIVE_MILES(10),
    ONE_HUNDRED_MILES(12),
    TWO_HUNDRED_MILES(4);

    private int numCardsPerDeck;

    private CardType(int numCardsPerDeck) {
        this.numCardsPerDeck = numCardsPerDeck;
    }

    public int getNumCardsPerDeck() {
        return numCardsPerDeck;
    }

}

