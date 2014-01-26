package com.chanakinllc.thousandmiles;

import com.chanakinllc.thousandmiles.cards.Card;
import com.chanakinllc.thousandmiles.cards.CardCategory;
import com.chanakinllc.thousandmiles.cards.CardPile;
import com.chanakinllc.thousandmiles.cards.distance.DistanceCard;

import java.util.ArrayList;

/**
 * Created by chan on 1/25/14.
 */
public class Player {


    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<Card> battlePile = new ArrayList<Card>();
    private ArrayList<Card> safetyPile = new ArrayList<Card>();
    private ArrayList<Card> speedPile = new ArrayList<Card>();
    private ArrayList<Card> mileagePile = new ArrayList<Card>();

    private int id;

    private int distanceTraveled = 0;

    public Player(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setPlayedCard(Card card)
    {
        CardCategory type = card.getCardCategory();

        switch(type)
        {
            case HAZARD:
                battlePile.add(card);
                break;
            case REMEDY:
                battlePile.add(card);
                break;
            case DISTANCE:
                distanceTraveled += (DistanceCard) card.getDistance();
                mileagePile.add(card);
                break;
            case SAFETY:
                safetyPile.add(card);
                break;
            case SPEED:
                speedPile.add(card);
                break;
            default:
                Log.i("ERROR", "Panic! We received a card type we can't handle!");
        }
    }

    public Card peekAtPile(CardPile pile)
    {
        switch(pile)
        {
            case BATTLE:
                return battlePile.isEmpty() ? null : battlePile.get(battlePile.size()-1);
                break;
            case SPEED:
                return speedPile.isEmpty() ? null : speedPile.get(speedPile.size()-1);
                break;
            case MILEAGE:
                return mileagePile.isEmpty() ? null : mileagePile.get(mileagePile.size()-1);
                break;
            case HAND:
                return hand.isEmpty() ? null : hand.get(hand.size()-1);
                break;
            case SAFETY:
                return hand.isEmpty() ? null : safetyPile.get(safetyPile.size()-1);
                break;
            default:
                Log.i("ERROR!", "Panic! We received a request to peek at a card pile we don't have!");
                return null;
        }

    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(int distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

}
