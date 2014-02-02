package com.chanakinllc.thousandmiles;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
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

/**
 * Created by chan on 1/25/14.
 * MODEL for the game -- this is a headless fragment
 * TODO this may not need to be a fragment, but instead a Parcelable object that
 * can be saved in onSavedInstance...come back to this
 */
public class GameRulesEngineFragment extends Fragment {
    public static final int NO_CARDS_REMAINING_IN_DECK = -2;
    public static final int EXCEEDED_MAX_NUMBER_OF_CARDS_ALLOWED_IN_HAND = -1;
    private Player currentPlayer;

    //Create an object of the deck class
    private Deck deck;

    //Eventually I'd like to make this to be a 4-player game
    private ArrayList<Player> players = new ArrayList<Player>();

    private ArrayList<Card> discardPile = new ArrayList<Card>();

    private static final String PLAYERS_LIST = "PlayersList";
    private static final int NUM_PLAYERS = 2;
    private static final int PLAYER_INDEX = 0; //The actual player (not computer) will always be the first in our list
    private static final int COMPUTER_INDEX = 1; //The computer's ID will always be the second in our list of players

    private GameRulesEngineListener listener;

    private String [] playerNames;

    public interface GameRulesEngineListener {
        public void rulesEngineBuilt();

        public void gameOver(Player winningPlayer);

        public void computerPlayed(Card card, int slotInOpponentHandCardOriginatedFrom);

        public void computerDrewCard(int indexOfDrawnCardInHand);

        public void playerMayBeginTurn();

        public void playDeemedInvalid(Card card, CardPile whichPile, int indexOfHandToReturnCard);

        public void placeDrawnCardInHand(Card card, int slotIndexInHand, boolean deckEmptyAfterDraw);

        public void drawRequestDenied(String errorMessage);
    }

    public static GameRulesEngineFragment newInstance( String playerName ) {
        String [] names = new String[NUM_PLAYERS];

        if( TextUtils.isEmpty(playerName) ){
            playerName = "Player 1";
        }

        names[PLAYER_INDEX] = playerName;
        names[COMPUTER_INDEX] = "Computer";

        Bundle bundle = new Bundle();
        bundle.putStringArray(PLAYERS_LIST, names);

        GameRulesEngineFragment fragment = new GameRulesEngineFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public GameRulesEngineFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (GameRulesEngineListener) activity;
        }
        catch(ClassCastException cce) {
            throw new ClassCastException(activity.toString() + " must implement GameRulesEngineListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Log.i("RULES ENGINE CREATION", "IN ON CREATE");

        if( null != getArguments() ) {
            Log.i("RULES ENGINE CREATION", "ARGUMENTS WERE NOT NULL");
            deck = new Deck();
            Log.i("RULES ENGINE CREATION", "DECK HAS BEEN BUILT");
            String [] playerNames = getArguments().getStringArray(PLAYERS_LIST);

            if( null == playerNames || playerNames.length == 0 ) {
                Log.e("NO PLAYERS FOUND!", "PANIC PANIC PANIC, no players were found in getArguments");
                return;
            }

            // Create a new player and give them a hand to start with
            for( int i = 0; i < playerNames.length; i++ ) {
                Player newPlayer = new Player(playerNames[i], i);
                newPlayer.setHand(deck.dealHand());
                newPlayer.setHasDrawnCardThisTurn(true);
                players.add(newPlayer);
                Log.i("RULES ENGINE", "ADDED NEW PLAYER!");
            }


            //Start out selecting a player at random to begin the game
            currentPlayer = getRandomPlayer();
            listener.rulesEngineBuilt();
        }

        Log.i("RULES ENGINE CREATION", "HAVE NOTIFIED ACTIVITY THAT THE RULES ENGINE IS BUILT");
    }

    private Player getRandomPlayer() {
        return players.get(0 + (int) (Math.random() * ((players.size()-1-0) + 1)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    public void cardPlayed(Card card, CardPile whichPile, int indexOfHand) {
        if( !isCardPlayValid(card, 0, 1, whichPile, indexOfHand) ) {
            listener.playDeemedInvalid(card, whichPile, indexOfHand);
        }
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
    private boolean isCardPlayValid(Card card, int playerKey, int opposingPlayerKey, CardPile whichPile, int indexOfHand) {
        Player cardPlayer = players.get(playerKey);
        Player opponent = players.get(opposingPlayerKey);

        Player playerWhoseAreaIsBeingPlayedOn = cardPlayer;

        //TODO pass back some way of notifying the player what mistake they made, probably an enum
        if( null == cardPlayer || cardPlayer != currentPlayer ) {
            return false;
        }

        //It's always okay to discard any card
        if( whichPile == CardPile.DISCARD ) {
            discardPile.add(card);
            return true;
        }

        // It's not meant for the discard pile, check if it's being placed in a pile it doesn't belong
        if( whichPile != card.getPileType() ) {
            return false;
        }

        switch( card.getCardCategory() ) {

            case HAZARD:
                // Can't play a hazard on yourself
                if( null == opponent ) {
                    return false;
                }

                playerWhoseAreaIsBeingPlayedOn = opponent;

                //First, see if a safety card is protecting the opponent from this card type
                for( SafetyCard safetyCard : opponent.getSafetyPile().values() ) {
                    for( CardType preventedCardTypes : safetyCard.getCardTypesPrevented() ) {
                        if( preventedCardTypes == card.getCardType() ) {
                            return false;
                        }
                    }
                }

                // Quick check for our special case Speed Limit, which can be played if the speed limit pile is null
                if( null == opponent.peekAtPile(whichPile) && card.getCardType() == CardType.SPEED_LIMIT ) {
                    break;
                }

                // If we've made it this far, time to check that the opponent is rolling and able to be hit with a hazard
                if( !opponent.isAbleToPlayDistanceCards() ) {
                    return false;
                }


                // Then check if the card on the pile is one that this card can play on
                for( CardType cardTypePlayableUpon : card.getCardTypesThatArePlayableOn() ) {
                    if( opponent.peekAtPile( whichPile ).getCardType() == cardTypePlayableUpon ) {
                        break;
                    }
                }

                return false;

            case REMEDY:

                // First, check that we don't already have a safety which removes the need for this remedy
                for( SafetyCard safetyCard : currentPlayer.getSafetyPile().values() ) {
                    for( CardType cardTypeCoveredBySafety : safetyCard.getCardTypesWithSameFunction() ) {
                        if( cardTypeCoveredBySafety == card.getCardType() ) {
                            return false;
                        }
                    }
                }

                Card topCard = currentPlayer.peekAtPile(whichPile);

                // Can't play this card if there's nothing on the pile
                if( null == topCard ) {
                    return false;
                }

                // Last, check if the card on the pile is one that this card can play on
                for( CardType cardTypePlayableUpon : card.getCardTypesThatArePlayableOn() ) {
                    if( topCard.getCardType() == cardTypePlayableUpon ) {
                        break;
                    }
                }

                return false;

            case DISTANCE:

                if( currentPlayer.isAbleToPlayDistanceCards() ) {
                    // If we're rolling twenty five or fifty miles, we're clear.
                    if(  CardType.TWENTY_FIVE_MILES == card.getCardType() || CardType.FIFTY_MILES == card.getCardType() ) {
                        break;
                    }

                    // Card played that is over the "speed limit" that could be imposed, so we need to check if a speed limit is in place
                    Card speedPileTopCard = currentPlayer.peekAtPile(CardPile.SPEED);

                    if( null == speedPileTopCard || speedPileTopCard.getCardType() == CardType.END_OF_LIMIT ) {
                        break;
                    }
                }

                return false;

            case SAFETY: // Can always play a safety
                break;

            default:
                Log.i("GameRulesEngineFragment", "Unexpected category, unable to determine validity of play");
                return false;
        }

        playerWhoseAreaIsBeingPlayedOn.setPlayedCard(card, whichPile);
        currentPlayer.getHand()[indexOfHand] = null; //Remove the card from their hand

        if( card.getCardCategory() == CardCategory.DISTANCE ) {
            checkIfGameWon();
        }

        return true;
    }

    private void beginNextPlayersTurn() {
        currentPlayer.setHasDrawnCardThisTurn(false);

        switch( currentPlayer.getUniqueId() ) {
            case 0:
                currentPlayer = players.get(1); //Computer player, let the computer take their turn.
                handleComputerAI();
            case 1:
                currentPlayer = players.get(0);
                listener.playerMayBeginTurn();
            default:
                Log.i("GameRulesEngineFragment", "Unable to switch to the other player.");
        }
    }

    //TODO make the AI a little smarter than this :)
    private void handleComputerAI() {
        //First draw a card
        //The first time this won't work as the computer's hand will be full
        int indexOfDrawnCard = drawCard();

        if( indexOfDrawnCard >= 0 ) {
            listener.computerDrewCard(indexOfDrawnCard);
        }

        Card [] computerHand = players.get(1).getHand();
        for(int i = 0; i < computerHand.length; i++ ) {
            // Play the first card we come across that is valid to play
            Card cardToPlay = computerHand[i];

            if( isCardPlayValid(cardToPlay, 1, 0, cardToPlay.getPileType(), i) ) {
                listener.computerPlayed(cardToPlay, i);
            }
        }
    }

    /**
     * Returns the hand for the current player ONLY
     * @return a ArrayList of cards based on which player the server
     * requests the hand for.
     */
    public Card [] getPlayerHand() {
        return players.get(PLAYER_INDEX).getHand();
    }

    public void cardDrawRequested() {
        if( currentPlayer != players.get(PLAYER_INDEX) ) {
            listener.drawRequestDenied(getResources().getString(R.string.not_player_turn_error));
        }
        else if( currentPlayer.hasDrawnCardThisTurn()) {
            listener.drawRequestDenied(getResources().getString(R.string.already_drew_card_this_turn));
        }
        else {
            int indexOfDrawnCard = drawCard();

            switch(indexOfDrawnCard) {
                case EXCEEDED_MAX_NUMBER_OF_CARDS_ALLOWED_IN_HAND:
                    listener.drawRequestDenied(getResources().getString(R.string.max_cards_in_hand_error));
                    break;
                case NO_CARDS_REMAINING_IN_DECK:
                    listener.drawRequestDenied(getResources().getString(R.string.deck_empty));
                    break;
                default:
                    listener.placeDrawnCardInHand(currentPlayer.getHand()[indexOfDrawnCard], indexOfDrawnCard, deck.isEmpty());
                    currentPlayer.setHasDrawnCardThisTurn(true);
                    break;
            }
        }

    }

    /**
     * Returns a single card when a player tries to draw a card
     * @return dealt card
     */
    private int drawCard() {
        Card drawnCard = deck.drawCard();

        if( null == drawnCard ) {
            return NO_CARDS_REMAINING_IN_DECK;
        }

        Card [] currentPlayerHand = currentPlayer.getHand();

        for( int i = 1; i < currentPlayerHand.length; i++ ) {
            // First try to avoid 0
            if( currentPlayerHand[i] == null ) {
                currentPlayerHand[i] = drawnCard;
                return i;
            }
        }

        //Only if all other six slots are filled do we want to add to our hand in the zero position
        if( currentPlayerHand[0] == null ) {
            return 0;
        }

        return EXCEEDED_MAX_NUMBER_OF_CARDS_ALLOWED_IN_HAND;
    }

    /**
     * Checks if the game has been won after a card has been played.
     *
     * @return boolean that denotes if the game has been won or play should continue (true if won, false if not);
     */
    private void checkIfGameWon() {
        if(currentPlayer.getDistanceTraveled() > 700 ) {
            listener.gameOver(currentPlayer);
        }

        beginNextPlayersTurn();
    }

    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }
}
