package com.chanakinllc.thousandmiles;

import android.util.Log;

import com.chanakinllc.thousandmiles.cards.Card;
import com.chanakinllc.thousandmiles.cards.CardType;
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

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by chan on 1/25/14.
 */
public class Deck {

    private ArrayList<Card> deck = new ArrayList<Card>();

    public Deck() {
        instantiate();
        shuffle();
    }

    // Should only be used to rebuild the deck from the discard pile
    public Deck(ArrayList<Card> cards) {
        this.deck = deck;
        shuffle();
    }

    private void instantiate() {
        //TODO would love to make this cleaner logic
        Log.i("DECK", "INSTANTIATING CARDS FOR DECK");

        for(CardType type: CardType.values() )
        {
            Log.i("DECK", "NUM CARDS PER TYPE=" + type.getNumCardsPerDeck());
            for(int i = 0; i < type.getNumCardsPerDeck(); i++) {
                Card cardForType = null;

                switch(type) {

                    case ACCIDENT:
                        cardForType = new AccidentCard(null);
                        break;
                    case OUT_OF_GAS:
                        cardForType = new OutOfGasCard(null);
                        break;
                    case FLAT_TIRE:
                        cardForType = new FlatTireCard(null);
                        break;
                    case STOP:
                        cardForType = new StopCard(null);
                        break;
                    case SPEED_LIMIT:
                        cardForType = new SpeedLimitCard(null);
                        break;
                    case REPAIRS:
                        cardForType = new RepairsCard(null);
                        break;
                    case GASOLINE:
                        cardForType = new GasolineCard(null);
                        break;
                    case SPARE_TIRE:
                        cardForType = new SpareTireCard(null);
                        break;
                    case ROLL:
                        cardForType = new RollCard(null);
                        break;
                    case END_OF_LIMIT:
                        cardForType = new EndOfLimitCard(null);
                        break;
                    case DRIVING_ACE:
                        cardForType = new DrivingAceCard(null);
                        break;
                    case EXTRA_TANK:
                        cardForType = new ExtraTankCard(null);
                        break;
                    case PUNCTURE_PROOF:
                        cardForType = new PunctureProofCard(null);
                        break;
                    case RIGHT_OF_WAY:
                        cardForType = new RightOfWayCard(null);
                        break;
                    case TWENTY_FIVE_MILES:
                        cardForType = new TwentyFiveMilesDistanceCard(null);
                        break;
                    case FIFTY_MILES:
                        cardForType = new FiftyMilesDistanceCard(null);
                        break;
                    case SEVENTY_FIVE_MILES:
                        cardForType = new SeventyFiveMilesDistanceCard(null);
                        break;
                    case ONE_HUNDRED_MILES:
                        cardForType = new OneHundredMilesDistanceCard(null);
                        break;
                    case TWO_HUNDRED_MILES:
                        cardForType = new TwoHundredMilesDistanceCard(null);
                        break;
                }
                deck.add(cardForType);
                Log.i("DECK", "ADDED CARD=" + cardForType.toString());
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    // Will return up to six cards, pending availability
    public Card [] dealHand() {
        Card [] hand = new Card [7]; //Have to make it 7 since the player can hold 7 at the beginning of a turn, but must end with 6

        for (int i = 0; i < 7; i++) {
            if( deck.isEmpty() )
            {
                break;
            }

            hand[i] = deck.get(i);
            deck.remove(i);//remove each card that was dealt from the deck
        }

        return hand;
    }

    public Card drawCard() {

        Card card = null;

        //As long as the deck is not empty, continue to deal from it
        if (deck.size() != 0) {
            card = deck.get(0);
            deck.remove(0);
        }

        return card;
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }
}
