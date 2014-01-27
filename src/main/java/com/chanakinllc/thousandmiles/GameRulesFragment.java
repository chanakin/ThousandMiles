package com.chanakinllc.thousandmiles;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanakinllc.thousandmiles.cards.Card;
import com.chanakinllc.thousandmiles.cards.CardCategory;
import com.chanakinllc.thousandmiles.cards.CardPile;
import com.chanakinllc.thousandmiles.cards.CardType;
import com.chanakinllc.thousandmiles.cards.safeties.SafetyCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by chan on 1/25/14.
 * MODEL for the game -- this is a headless fragment
 */
public class GameRulesFragment extends Fragment {
    private Player currentPlayer;

    //Create an object of the deck class
    private Deck deck = new Deck();

    //Eventually I'd like to make this to be a 4-player game
    private ArrayList<Player> players = new ArrayList<Player>();

    private ArrayList<String> discardPile = new ArrayList<String>();

    private ArrayList<String> handsToDeal = null;

    //Holds the mileage accumulated by each player
    private int player1MileageTotal = 0;
    private int player2MileageTotal = 0;

    private static final int MAX_PLAYERS = 4;
    private static final String PLAYERS_LIST = "NumPlayers";

    public static GameRulesFragment newInstance( String [] playerNames ) {
        String [] players;

        if( playerNames.length > MAX_PLAYERS ) {
            players = new String[MAX_PLAYERS];

            for( int i = 0; i < MAX_PLAYERS; i++ ) {
                players[i] = playerNames[i];
            }

        }
        else {
            players = playerNames;
        }

        Bundle bundle = new Bundle();
        bundle.putStringArray(PLAYERS_LIST, players);

        GameRulesFragment fragment = new GameRulesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private GameRulesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if( null != savedInstanceState ) {
            String [] playerNames = savedInstanceState.getStringArray(PLAYERS_LIST);

            if( null == playerNames ) {
                Log.i("NO PLAYERS FOUND!", "PANIC PANIC PANIC, no players were found in saved instance state bundle");
                return;
            }

            for( int i = 0; i < playerNames.length; i++ ) {
                players.add(new Player(playerNames[i], i));
            }
        }

        setRetainInstance(true);
        //Start out selecting a player at random to begin the game
        currentPlayer = getRandomPlayer();

        super.onCreate(savedInstanceState);
    }

    private Player getRandomPlayer() {
        return players.get(0 + (int) (Math.random() * ((players.size()-1-0) + 1)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    /**
     * Contains all the logic for game play. Receives card that has been played
     * and determines where it is to be placed based on the rules of the game.
     *
     * @param card what card was played
     * @param playerKey which player the card was played upon
     * @param opposingPlayerKey the card itself
     * @return String where to place the card
     */
    public boolean validatePlay(Card card, int playerKey, int opposingPlayerKey, CardPile whichPile) {
        Player cardPlayer = players.get(playerKey);
        Player opposingPlayer = players.get(opposingPlayerKey);

        if( null == cardPlayer || cardPlayer != currentPlayer ) {
            return false;
        }

        // Can't play a hazard card on yourself
        if( card.getCardCategory() == CardCategory.HAZARD && null == opposingPlayer ) {
            return false;
        }


        //It's always okay to discard any card
        if( whichPile == CardPile.DISCARD ) {
            cardPlayer.setPlayedCard(card);
            return true;
        }
        else if( whichPile != card.getPileType() ) {
            return false;
        }

        switch( card.getCardCategory() ) {

            case HAZARD:
                //First check that no safeties are in play that do not allow this hazard to be played
                for( SafetyCard safetyCard : cardPlayer.getSafetyCardsInPlay().values() ) {
                    for( CardType preventedCardTypes : safetyCard.getCardTypesPrevented() ) {
                        if( preventedCardTypes == card.getCardType() ) {
                            return false;
                        }
                    }
                }

                // Then check if the card on the pile is one that this card can play on
                for( CardType cardTypePlayableUpon : card.getCardTypesThatArePlayableOn() ) {
                    CardType topOfPileCardTypeAfterSafety;
//
//                    if( cardPlayer.peekAtPile( CardPile.BATTLE ).getCardCategory() == CardCategory.REMEDY &&  )
                    if( cardPlayer.peekAtPile( CardPile.BATTLE ).getCardType() == cardTypePlayableUpon ) {
                        return true;
                    }
                }

                break;
            case REMEDY:
                if( CardCategory.HAZARD == cardPlayer.peekAtPile(CardPile.BATTLE).getCardCategory() ) {

                }

                break;
            case DISTANCE:
                break;
            case SAFETY:
                break;
        }

        switch( card.getCardType() ) {

            case ACCIDENT:
            case OUT_OF_GAS:
            case FLAT_TIRE:
            case STOP:

                break;
            case SPEED_LIMIT:
                break;
            case REPAIRS:
                break;
            case GASOLINE:
                break;
            case SPARE_TIRE:
                break;
            case ROLL:
                break;
            case END_OF_LIMIT:
                break;
            case DRIVING_ACE:
                break;
            case EXTRA_TANK:
                break;
            case PUNCTURE_PROOF:
                break;
            case RIGHT_OF_WAY:
                break;
            case TWENTY_FIVE_MILES:
            case FIFTY_MILES:

                switch( cardPlayer.peekAtPile(CardPile.BATTLE).getCardType() ) {
                    case ACCIDENT:
                    case OUT_OF_GAS:
                    case FLAT_TIRE:
                    case STOP:


                        break;
                    case REPAIRS:
                    case GASOLINE:
                    case SPARE_TIRE:
//                        if( )
                        break;
                    case ROLL:
                        cardPlayer.setPlayedCard(card);
                        return true;
                }
                break;
            case SEVENTY_FIVE_MILES:
                break;
            case ONE_HUNDRED_MILES:
                break;
            case TWO_HUNDRED_MILES:
                break;
        }

        Card cardOnPile = cardPlayer.peekAtPile(card.getPileType());

        ArrayList<CardType> playableCardTypes = new ArrayList<CardType>( Arrays.asList(card.getCardTypesThatArePlayableOn()));


//for( CardType playableCardType : card.getCardTypesThatArePlayableOn() ) {
//            if( playableCardType == cardOnPile.getCardType() ) {
//                return true;
//            }
//        }
//
//        if( card.getCardCategory() == CardCategory.HAZARD ) {
//
//        }
        return true;
    }
    /**
     * Shuffles the deck and receives the cards to be dealt.
     *
     * @param playerId the player
     * @return a ArrayList of cards based on which player the server
     * requests the hand for.
     */
//    public ArrayList getHand(int playerId) {
//
//        //Shuffle the deck
//        deck.shuffle();
//
//        //Receive the cards to be dealt
//        handsToDeal = deck.dealHand();
//
//        //Used to alternate cards being dealt so that each player
//        //receives every other card
//        int cardCount = 0;
//
//        Iterator it = handsToDeal.iterator();
//
//        while(it.hasNext()){
//            if(cardCount%2 == 0) {
//                player1Hand.add((JRadioButton)it.next());
//            }
//            else {
//                player2Hand.add((JRadioButton)it.next());
//            }
//            cardCount++;
//        }
//        handsToDeal.clear();//remove all the cards from the ArrayList
//
//        //Return the hands
//        if(playerId == 0) {
//            return player1Hand;
//        }
//        else {
//            return player2Hand;
//        }
//    }
//    /**
//     * Returns a single card to be dealt to the player
//     * at the start of their turn.
//     * @return dealt card
//     */
//    public JRadioButton getDealtCard() {
//        return deck.drawCard();
//    }

    /**
     * Checks if the game has been won after a card has been played.
     *
     * @return boolean that denotes if the game has been won or play should continue (true if won, false if not);
     */
    public boolean checkIfGameWon(int player) {
        if(player == 0) {
            if(player1MileageTotal > 700) {
                return true;
            }
            else return false;
        }
        else {
            if(player2MileageTotal > 700) {
                return true;
            }
            else return false;
        }
    }
    /**
     * Returns the discard pile.
     * @return ArrayList holding all discarded cards.
     */
    public ArrayList getDiscardPile() {
        return discardPile;
    }
}
