package com.chanakinllc.thousandmiles;

import android.app.Activity;
import android.app.Fragment;
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
 */
public class GameRulesFragment extends Fragment {
    private Player currentPlayer;

    //Create an object of the deck class
    private Deck deck;

    //Eventually I'd like to make this to be a 4-player game
    private ArrayList<Player> players = new ArrayList<Player>();

    private ArrayList<Card> discardPile = new ArrayList<Card>();

    private static final String PLAYERS_LIST = "PlayersList";
    private static final int NUM_PLAYERS = 2;

    private GameRulesFragmentListener listener;

    public interface GameRulesFragmentListener {

        public void gameOver(Player winningPlayer);

        public void computerPlayed(Card card);

        public void computerDrewCard(Card card);

        public void playerMayBeginTurn();
    }

    public static GameRulesFragment newInstance( String playerName ) {
        String [] players = new String[NUM_PLAYERS];

        if( TextUtils.isEmpty(playerName) ){
            playerName = "Player 1";
        }

        players[0] = playerName;
        players[1] = "Computer";

        Bundle bundle = new Bundle();
        bundle.putStringArray(PLAYERS_LIST, players);

        GameRulesFragment fragment = new GameRulesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public GameRulesFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (GameRulesFragmentListener) activity;
        }
        catch(ClassCastException cce) {
            throw new ClassCastException(activity.toString() + " must implement GameRulesFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if( null != savedInstanceState ) {
            String [] playerNames = savedInstanceState.getStringArray(PLAYERS_LIST);

            if( null == playerNames ) {
                Log.i("NO PLAYERS FOUND!", "PANIC PANIC PANIC, no players were found in saved instance state bundle");
                return;
            }

            // Create a new player and give them a hand to start with
            for( int i = 0; i < playerNames.length; i++ ) {
                Player newPlayer = new Player(playerNames[i], i);
                newPlayer.setHand(deck.dealHand());
                players.add(newPlayer);
            }
        }

        deck = new Deck();
        //Start out selecting a player at random to begin the game
        currentPlayer = getRandomPlayer();

        setRetainInstance(true);
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
    public boolean cardPlayed(Card card, int playerKey, int opposingPlayerKey, CardPile whichPile) {
        Player cardPlayer = players.get(playerKey);
        Player opponent = players.get(opposingPlayerKey);

        Player playerWhoseAreaIsBeingPlayedOn = cardPlayer;

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
                Log.i("GameRulesFragment", "Unexpected category, unable to determine validity of play");
                return false;
        }

        playerWhoseAreaIsBeingPlayedOn.setPlayedCard(card, whichPile);
        currentPlayer.getHand().remove(card); //Remove the card from their hand
        if( card.getCardCategory() == CardCategory.DISTANCE ) {
            checkIfGameWon();
        }

        return true;
    }

    private void beginNextPlayersTurn() {
        switch( currentPlayer.getUniqueId() ) {
            case 0:
                currentPlayer = players.get(1); //Computer player, let the computer take their turn.
                handleComputerAI();
            case 1:
                currentPlayer = players.get(0);
                listener.playerMayBeginTurn();
            default:
                Log.i("GameRulesFragment", "Unable to switch to the other player.");
        }
    }

    private void handleComputerAI() {
        for(Card cardInHand : currentPlayer.getHand()) {
            // Play the first card we come across that is valid to play
            if( cardPlayed(cardInHand, currentPlayer.getUniqueId(), 0, cardInHand.getPileType()) ) {
                listener.computerPlayed(cardInHand);
                listener.computerDrewCard(drawCard());
            }
        }
    }

    /**
     * Returns the hand for the current player ONLY
     * @return a ArrayList of cards based on which player the server
     * requests the hand for.
     */
    public ArrayList<Card> getHand() {
        return currentPlayer.getHand();
    }

    /**
     * Returns a single card when a player tries to draw a card
     * @return dealt card
     */
    public Card drawCard() {
        Card drawnCard = deck.drawCard();
        currentPlayer.getHand().add(drawnCard);
        return drawnCard;
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

    /**
     * Returns the discard pile.
     * @return ArrayList holding all discarded cards.
     */
    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }
}
