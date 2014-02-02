package com.chanakinllc.thousandmiles;

import android.util.Log;

import com.chanakinllc.thousandmiles.cards.Card;
import com.chanakinllc.thousandmiles.cards.CardType;
import com.chanakinllc.thousandmiles.cards.CardCategory;
import com.chanakinllc.thousandmiles.cards.CardPile;
import com.chanakinllc.thousandmiles.cards.distance.DistanceCard;
import com.chanakinllc.thousandmiles.cards.safeties.SafetyCard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chan on 1/25/14.
 */
public class Player {

    private Card [] hand;
    private ArrayList<Card> battlePile = new ArrayList<Card>();

    private HashMap<CardType, SafetyCard> safetyPile = new HashMap<CardType, SafetyCard>();
    private ArrayList<Card> speedPile = new ArrayList<Card>();
    private ArrayList<DistanceCard> distancePile = new ArrayList<DistanceCard>();

    private int distanceTraveled = 0;

    private String name;

    private int uniqueId;

    public Player(String name, int uniqueId)
    {
        this.name = name;
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setPlayedCard(Card card, CardPile pile)
    {
        CardCategory type = card.getCardCategory();

        switch(pile) {

            case SPEED:
                speedPile.add(card);
                break;
            case SAFETY:
                try {
                    safetyPile.put(card.getCardType(), (SafetyCard) card);
                }
                catch (ClassCastException cce ) {
                    Log.e( "SET_PLAYED_CARD", "Unable to cast card to safety card!", cce);
                }

                break;
            case BATTLE:

                battlePile.add(card);
                break;

            case DISTANCE:

                try {
                    distanceTraveled += ((DistanceCard) card).getDistance();
                    distancePile.add((DistanceCard) card);
                }
                catch( ClassCastException cce ) {

                    Log.e( "SET_PLAYED_CARD", "Unable to cast card to distance card!", cce);
                }
                break;
            default:
                Log.i( "SET_PLAYED_CARD", "Unable to place card in pile as Player does not have knowledge of that pile");
        }
    }

    public Card peekAtPile(CardPile pile)
    {
        switch(pile)
        {
            case BATTLE:
                return battlePile.isEmpty() ? null : battlePile.get(battlePile.size()-1);
            case SPEED:
                return speedPile.isEmpty() ? null : speedPile.get(speedPile.size()-1);
            case DISTANCE:
                return distancePile.isEmpty() ? null : distancePile.get(distancePile.size()-1);
            case SAFETY:
                return safetyPile.isEmpty() ? null : safetyPile.get(safetyPile.size()-1);
            default:
                Log.i("ERROR!", "Panic! We received a request to peek at a card pile we don't have!");
                return null;
        }
    }

    public boolean hasSafetyCardInPlay(CardType safetyCardType) {
        return safetyPile.containsKey(safetyCardType);
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public Card [] getHand() {
        return hand;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public boolean isAbleToPlayDistanceCards() {
        Card battlePileTopCard = peekAtPile(CardPile.BATTLE);
        return (battlePileTopCard != null && (CardType.ROLL == battlePileTopCard.getCardType() || (CardCategory.REMEDY == battlePileTopCard.getCardCategory() && hasSafetyCardInPlay(CardType.RIGHT_OF_WAY)) ));
    }

    public HashMap<CardType, SafetyCard> getSafetyPile() {
        return safetyPile;
    }
}