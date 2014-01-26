package com.chanakinllc.thousandmiles;

import android.util.Log;

import com.chanakinllc.thousandmiles.cards.Card;
import com.chanakinllc.thousandmiles.cards.CardType;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by chan on 1/25/14.
 */
public class Deck {

    private ArrayList<Card> deck = new ArrayList<Card>();

    public Deck() {

        for(CardType type: CardType.values() )
        {
            addCardsToDeck(type);
        }

        shuffle();
    }

    // Should only be used to rebuild the deck from the discard pile
    public Deck(ArrayList<Card> cards) {
        this.deck = deck;
        shuffle();
    }

    private void addCardsToDeck(CardType type) {
        for(int i = 0; i < type.getNumCardsPerDeck(); i++) {
            Class<?> cardClass = type.getClassForCardType();

            try {
                if( cardClass.newInstance() instanceof Card) {
                    deck.add( (Card) cardClass.newInstance() );
                }
            }
            catch(Exception iae) {
                Log.i("Exception", iae.getMessage());
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    // Will return up to six cards, pending availability
    public ArrayList dealHand() {
        ArrayList<Card> hand = new ArrayList<Card>();

        for (int i = 0; i < 6; i++) {
            if( deck.isEmpty() )
            {
                break;
            }

            hand.add(deck.get(i));
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

}
