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
    ACCIDENT(R.integer.num_accident_cards_per_deck, AccidentCard.class),
    OUT_OF_GAS(R.integer.num_out_of_gas_cards_per_deck, OutOfGasCard.class),
    FLAT_TIRE(R.integer.num_flat_tire_cards_per_deck, FlatTireCard.class),
    STOP(R.integer.num_stop_cards_per_deck, StopCard.class),
    SPEED_LIMIT(R.integer.num_speed_limit_cards_per_deck, SpeedLimitCard.class),
    REPAIRS(R.integer.num_repairs_cards_per_deck, RepairsCard.class),
    GASOLINE(R.integer.num_gasoline_cards_per_deck, GasolineCard.class),
    SPARE_TIRE(R.integer.num_spare_tire_cards_per_deck, SpareTireCard.class),
    ROLL(R.integer.num_roll_cards_per_deck, RollCard.class),
    END_OF_LIMIT(R.integer.num_end_of_limit_cards_per_deck, EndOfLimitCard.class),
    DRIVING_ACE(R.integer.num_driving_ace_cards_per_deck, DrivingAceCard.class),
    EXTRA_TANK(R.integer.num_extra_tank_cards_per_deck, ExtraTankCard.class),
    PUNCTURE_PROOF(R.integer.num_puncture_proof_cards_per_deck, PunctureProofCard.class),
    RIGHT_OF_WAY(R.integer.num_right_of_way_cards_per_deck, RightOfWayCard.class),
    TWENTY_FIVE_MILES(R.integer.num_twenty_five_mile_cards_per_deck, TwentyFiveMilesDistanceCard.class),
    FIFTY_MILES(R.integer.num_fifty_mile_cards_per_deck, FiftyMilesDistanceCard.class),
    SEVENTY_FIVE_MILES(R.integer.num_seventy_five_mile_cards_per_deck, SeventyFiveMilesDistanceCard.class),
    ONE_HUNDRED_MILES(R.integer.num_one_hundred_mile_cards_per_deck, OneHundredMilesDistanceCard.class),
    TWO_HUNDRED_MILES(R.integer.num_two_hundred_mile_cards_per_deck, TwoHundredMilesDistanceCard.class);

    private int numCardsPerDeck = 0;
    private Class<?> cardClass;

    private CardType(int numCardsPerDeck, Class<?> cardClass) {
        this.numCardsPerDeck = numCardsPerDeck;
        this.cardClass = cardClass;
    }

    public int getNumCardsPerDeck() {
        return numCardsPerDeck;
    }

    public Class<?> getClassForCardType() {
        return cardClass;
    }
}

