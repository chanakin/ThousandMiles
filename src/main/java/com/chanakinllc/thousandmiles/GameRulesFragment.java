package com.chanakinllc.thousandmiles;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanakinllc.thousandmiles.cards.Card;
import com.chanakinllc.thousandmiles.cards.CardPile;

import java.util.ArrayList;

/**
 * Created by chan on 1/25/14.
 * MODEL for the game -- this is a headless fragment
 */
public class GameRulesFragment extends Fragment {
    private int playerTurn;

    //Create an object of the deck class
    private Deck deck = new Deck();

    //Eventually I'd like to make this to be a 4-player game
    private ArrayList<Player> players = new ArrayList<Player>();

    private ArrayList<String> discardPile = new ArrayList<String>();

    private ArrayList<String> handsToDeal = null;

    //Holds the mileage accumulated by each player
    private int player1MileageTotal = 0;
    private int player2MileageTotal = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        //Start out selecting a player at random to begin the game
        playerTurn = getRandomPlayerId();
        super.onCreate(savedInstanceState);
    }

    private int getRandomPlayerId() {
        return players.get(0 + (int) (Math.random() * ((players.size()-1-0) + 1))).getId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    /**
     * Contains all the logic for game play. Receives card that has been played
     * and determines where it is to be placed based on the rules of the game.
     *
     * @param cardId what card was played
     * @param playerId which player played the card
     * @param playedCard the card itself
     * @return String where to place the card
     */
    public boolean validatePlay(Card card, int playerId, CardPile whichPile) {
        if(playerTurn != playerId) {
            return false;
        }

        switch(card.getCardCategory())
        {

        }



            if(cardId.equalsIgnoreCase("Gasoline") || cardId.equalsIgnoreCase("Stop")  || cardId.equalsIgnoreCase("SpareTire") ||
                    cardId.equalsIgnoreCase("Repairs")) {
                if(player1BattlePile.isEmpty()) {
                    discardPile.add(playedCard);
                    whichPile = "DiscardPile";
                }
                else {
                    if(cardId.equalsIgnoreCase("Stop")) {
                        if(player2RightOfWayInPlay == true) {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else {
                            if(player2BattlePile.isEmpty()) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                String topCard = player2BattlePile.get(player2BattlePile.size() - 1);
                                if(!topCard.equalsIgnoreCase("Roll")) {
                                    discardPile.add(playedCard);
                                    whichPile = "DiscardPile";
                                }
                                else {
                                    player2BattlePile.add(cardId);
                                    whichPile = "OppBattlePile";
                                }
                            }
                        }
                    }
                    else {
                        String topCard = player1BattlePile.get(player1BattlePile.size() - 1);
                        if(cardId.equalsIgnoreCase("Gasoline")) {
                            if(!topCard.equalsIgnoreCase("OutOfGas")) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                player1BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                        }
                        else if(cardId.equalsIgnoreCase("Repairs")) {
                            if(!topCard.equalsIgnoreCase("Accident"))                        {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else
                            {
                                player1BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                        }
                        else if(cardId.equalsIgnoreCase("SpareTire")) {
                            if(!topCard.equalsIgnoreCase("FlatTire")) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                player1BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                        }
                    }
                }
            }
            else if(cardId.equalsIgnoreCase("Accident") || cardId.equalsIgnoreCase("FlatTire") || cardId.equalsIgnoreCase("OutOfGas")) {

                if(player2BattlePile.isEmpty()) {
                    if(player2RightOfWayInPlay == false) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        if(cardId.equalsIgnoreCase("Accident") && player2DrivingAceInPlay == true) {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else if(cardId.equalsIgnoreCase("FlatTire") && player2PunctureProofInPlay == true) {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else if(cardId.equalsIgnoreCase("OutOfGas") && player2ExtraTankInPlay == true) {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else {
                            player2BattlePile.add(cardId);
                            whichPile = "OppBattlePile";
                        }
                    }
                }
                else {
                    String topCard = player2BattlePile.get(player2BattlePile.size() - 1);

                    if(player2RightOfWayInPlay == false) {
                        if(!topCard.equalsIgnoreCase("Roll")){
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else {
                            if(cardId.equalsIgnoreCase("Accident") && player2DrivingAceInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else if(cardId.equalsIgnoreCase("FlatTire") && player2PunctureProofInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else if(cardId.equalsIgnoreCase("OutOfGas") && player2ExtraTankInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                player2BattlePile.add(cardId);
                                whichPile = "OppBattlePile";
                            }
                        }
                    }
                    else {
                        if(topCard.equalsIgnoreCase("SpareTire") || topCard.equalsIgnoreCase("Gasoline") || topCard.equalsIgnoreCase("SpareTire") |
                                topCard.equalsIgnoreCase("Roll")) {
                            if(cardId.equalsIgnoreCase("Accident") && player2DrivingAceInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else if(cardId.equalsIgnoreCase("FlatTire") && player2PunctureProofInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else if(cardId.equalsIgnoreCase("OutOfGas") && player2ExtraTankInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                player2BattlePile.add(cardId);
                                whichPile = "OppBattlePile";
                            }
                        }
                        else {
                            if(topCard.equalsIgnoreCase("Accident") && player1DrivingAceInPlay == true) {
                                player1BattlePile.add(cardId);
                                whichPile = "OppBattlePile";
                            }
                            else if(topCard.equalsIgnoreCase("OutOfGas") && player1ExtraTankInPlay == true) {
                                player1BattlePile.add(cardId);
                                whichPile = "OppBattlePile";
                            }
                            else if(topCard.equalsIgnoreCase("FlatTire") && player1PunctureProofInPlay == true) {
                                player1BattlePile.add(cardId);
                                whichPile = "OppBattlePile";
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                    }
                }
            }
            else if(cardId.equalsIgnoreCase("25miles") || cardId.equalsIgnoreCase("50miles")) {
                int endOfMilesSpecified = cardId.indexOf('m');
                String miles = cardId.substring(0,endOfMilesSpecified);
                if(player1BattlePile.isEmpty()) {
                    if(player1RightOfWayInPlay == false) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        int milesRolled = Integer.parseInt(miles);
                        player1MileageTotal = player1MileageTotal + milesRolled;
                        whichPile = (String)cardId.concat("Pile");;
                    }
                }
                else {
                    String topCard = player1BattlePile.get(player1BattlePile.size() - 1);
                    if(player1RightOfWayInPlay == true) {
                        if(topCard.equalsIgnoreCase("SpareTire") || topCard.equalsIgnoreCase("Gasoline") || topCard.equalsIgnoreCase("Repairs") |
                                topCard.equalsIgnoreCase("Roll")) {
                            int milesRolled = Integer.parseInt(miles);
                            player1MileageTotal = player1MileageTotal + milesRolled;
                            whichPile = (String)cardId.concat("Pile");;
                        }
                        else if(topCard.equalsIgnoreCase("Accident")) {
                            if(player1DrivingAceInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player1MileageTotal = player1MileageTotal + milesRolled;
                                whichPile = (String)cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("OutOfGas")) {
                            if(player1ExtraTankInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player1MileageTotal = player1MileageTotal + milesRolled;
                                whichPile = (String)cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("FlatTire")) {
                            if(player1PunctureProofInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player1MileageTotal = player1MileageTotal + milesRolled;
                                whichPile = (String)cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                    }
                    else {
                        if(!topCard.equalsIgnoreCase("Roll")) {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else {
                            int milesRolled = Integer.parseInt(miles);
                            player1MileageTotal = player1MileageTotal + milesRolled;
                            whichPile = (String)cardId.concat("Pile");;
                        }
                    }
                }
            }
            else if(cardId.equalsIgnoreCase("75miles") || cardId.equalsIgnoreCase("100miles") || cardId.equalsIgnoreCase("200miles")) {
                int endOfMilesSpecified = cardId.indexOf('m');
                String miles = cardId.substring(0,endOfMilesSpecified);
                if(player1BattlePile.isEmpty()) {
                    if(player1RightOfWayInPlay == false) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        int milesRolled = Integer.parseInt(miles);
                        player1MileageTotal = player1MileageTotal + milesRolled;
                        whichPile = (String)cardId.concat("Pile");;
                    }
                }
                else {
                    String topCard = player1BattlePile.get(player1BattlePile.size() - 1);
                    if(player1RightOfWayInPlay == true) {
                        if(topCard.equalsIgnoreCase("SpareTire") || topCard.equalsIgnoreCase("Gasoline") || topCard.equalsIgnoreCase("SpareTire") |
                                topCard.equalsIgnoreCase("Roll") || topCard.equalsIgnoreCase("Stop")) {
                            int milesRolled = Integer.parseInt(miles);
                            player1MileageTotal = player1MileageTotal + milesRolled;
                            whichPile = (String)cardId.concat("Pile");;
                        }
                        else if(topCard.equalsIgnoreCase("Accident")) {
                            if(player1DrivingAceInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player1MileageTotal = player1MileageTotal + milesRolled;
                                whichPile = (String)cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("OutOfGas")) {
                            if(player1ExtraTankInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player1MileageTotal = player1MileageTotal + milesRolled;
                                whichPile = (String)cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("FlatTire")) {
                            if(player1PunctureProofInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player1MileageTotal = player1MileageTotal + milesRolled;
                                whichPile = (String)cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                    }
                    else {
                        if(topCard.equalsIgnoreCase("Roll")) {

                            if(player1SpeedPile.isEmpty()) {
                                int milesRolled = Integer.parseInt(miles);
                                player1MileageTotal = player1MileageTotal + milesRolled;
                                whichPile = cardId.concat("Pile");;
                            }
                            else {
                                String topSpeedCard = player1SpeedPile.get(player1SpeedPile.size() - 1);
                                if(topSpeedCard.equalsIgnoreCase("EndOfLimit")) {
                                    int milesRolled = Integer.parseInt(miles);
                                    player1MileageTotal = player1MileageTotal + milesRolled;
                                    whichPile = cardId.concat("Pile");;
                                }
                                else {
                                    discardPile.add(playedCard);
                                    whichPile = "DiscardPile";
                                }
                            }
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                    }
                }
            }
            else if(cardId.equalsIgnoreCase("Roll")) {
                if(player1BattlePile.isEmpty()) {
                    if(player1RightOfWayInPlay == true) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        player1BattlePile.add(cardId);
                        whichPile = "MyBattlePile";
                    }
                }
                else {
                    if(player1RightOfWayInPlay== true) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        String topCard = player1BattlePile.get(player1BattlePile.size() - 1);
                        if(topCard.equalsIgnoreCase("Repairs") || topCard.equalsIgnoreCase("Gasoline") || topCard.equalsIgnoreCase("SpareTire") || topCard.equalsIgnoreCase("Stop")) {
                            player1BattlePile.add(cardId);
                            whichPile = "MyBattlePile";
                        }
                        else if(topCard.equalsIgnoreCase("Accident")) {
                            if(player1DrivingAceInPlay == true) {
                                player1BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("OutOfGas")) {
                            if(player1ExtraTankInPlay == true) {
                                player1BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("FlatTire")) {
                            if(player1PunctureProofInPlay == true) {
                                player1BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                    }
                }
            }
            else if(cardId.equalsIgnoreCase("RightOfWay") || cardId.equalsIgnoreCase("PunctureProof") ||
                    cardId.equalsIgnoreCase("ExtraTank") || cardId.equalsIgnoreCase("DrivingAce")) {
                if(cardId.equalsIgnoreCase("RightOfWay")) {
                    player1RightOfWayInPlay = true;
                }
                else if(cardId.equalsIgnoreCase("PunctureProof")) {
                    player1PunctureProofInPlay = true;
                }
                else if(cardId.equalsIgnoreCase("ExtraTank")) {
                    player1ExtraTankInPlay = true;
                }
                else if(cardId.equalsIgnoreCase("DrivingAce")) {
                    player1DrivingAceInPlay = true;
                }
                whichPile = "SafetyPile";
            }
            else if(cardId.equalsIgnoreCase("EndOfLimit")) {
                if(player1RightOfWayInPlay == true) {
                    discardPile.add(playedCard);
                    whichPile = "DiscardPile";
                }
                else {
                    if(player1SpeedPile.isEmpty()) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        String topCard = player1SpeedPile.get(player1SpeedPile.size() - 1);
                        if(topCard.equalsIgnoreCase("SpeedLimit")) {
                            player1SpeedPile.add(cardId);
                            whichPile = "MySpeedPile";
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }

                    }
                }
            }
            else if(cardId.equalsIgnoreCase("SpeedLimit")) {
                if(player2RightOfWayInPlay == true) {
                    discardPile.add(playedCard);
                    whichPile = "DiscardPile";
                }
                else {
                    if(player2SpeedPile.isEmpty()) {
                        player2SpeedPile.add(cardId);
                        whichPile = "OppSpeedPile";
                    }
                    else {
                        String topCard = player2SpeedPile.get(player2SpeedPile.size() - 1);
                        if(topCard.equalsIgnoreCase("EndOfLimit")) {
                            player2SpeedPile.add(cardId);
                            whichPile = "OppSpeedPile";
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }


                    }
                }
            }
        }
        else if(playerId == 1) {
            if(cardId.equalsIgnoreCase("Gasoline") || cardId.equalsIgnoreCase("Stop")  || cardId.equalsIgnoreCase("SpareTire") ||
                    cardId.equalsIgnoreCase("Repairs")) {
                if(player2BattlePile.isEmpty()) {
                    discardPile.add(playedCard);
                    whichPile = "DiscardPile";
                }
                else {
                    if(cardId.equalsIgnoreCase("Stop")) {
                        if(player1RightOfWayInPlay == true) {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else {
                            if(player1BattlePile.isEmpty()) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                String topCard = player1BattlePile.get(player1BattlePile.size() - 1);
                                if(!topCard.equalsIgnoreCase("Roll")) {
                                    discardPile.add(playedCard);
                                    whichPile = "DiscardPile";
                                }
                                else {
                                    player1BattlePile.add(cardId);
                                    whichPile = "OppBattlePile";
                                }
                            }
                        }
                    }
                    else {
                        String topCard = player2BattlePile.get(player2BattlePile.size() - 1);
                        if(cardId.equalsIgnoreCase("Gasoline")) {
                            if(!topCard.equalsIgnoreCase("OutOfGas")) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                player2BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                        }
                        else if(cardId.equalsIgnoreCase("Repairs")) {
                            if(!topCard.equalsIgnoreCase("Accident")) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                player2BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                        }
                        else if(cardId.equalsIgnoreCase("SpareTire")) {
                            if(!topCard.equalsIgnoreCase("FlatTire")) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                player2BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                        }
                    }
                }
            }
            else if(cardId.equalsIgnoreCase("Accident") || cardId.equalsIgnoreCase("FlatTire") || cardId.equalsIgnoreCase("OutOfGas")) {
                if(player1BattlePile.isEmpty()) {
                    if(player1RightOfWayInPlay == false) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        if(cardId.equalsIgnoreCase("Accident") && player1DrivingAceInPlay == true) {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else if(cardId.equalsIgnoreCase("FlatTire") && player1PunctureProofInPlay == true) {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else if(cardId.equalsIgnoreCase("OutOfGas") && player1ExtraTankInPlay == true) {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else {
                            player1BattlePile.add(cardId);
                            whichPile = "OppBattlePile";
                        }
                    }
                }
                else if(!player1BattlePile.isEmpty()) {
                    String topCard = player1BattlePile.get(player1BattlePile.size() - 1);
                    if(player1RightOfWayInPlay == false) {
                        if(!topCard.equalsIgnoreCase("Roll")){
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else {
                            if(cardId.equalsIgnoreCase("Accident") && player1DrivingAceInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else if(cardId.equalsIgnoreCase("FlatTire") && player1PunctureProofInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else if(cardId.equalsIgnoreCase("OutOfGas") && player1ExtraTankInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                player1BattlePile.add(cardId);
                                whichPile = "OppBattlePile";
                            }
                        }
                    }
                    else {
                        if(topCard.equalsIgnoreCase("SpareTire") || topCard.equalsIgnoreCase("Gasoline") || topCard.equalsIgnoreCase("SpareTire") |
                                topCard.equalsIgnoreCase("Roll")) {
                            if(cardId.equalsIgnoreCase("Accident") && player1DrivingAceInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else if(cardId.equalsIgnoreCase("FlatTire") && player1PunctureProofInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else if(cardId.equalsIgnoreCase("OutOfGas") && player1ExtraTankInPlay == true) {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                            else {
                                player1BattlePile.add(cardId);
                                whichPile = "OppBattlePile";
                            }
                        }
                        else {
                            if(topCard.equalsIgnoreCase("Accident") && player1DrivingAceInPlay == true) {
                                player1BattlePile.add(cardId);
                                whichPile = "OppBattlePile";
                            }
                            else if(topCard.equalsIgnoreCase("OutOfGas") && player1ExtraTankInPlay == true) {
                                player1BattlePile.add(cardId);
                                whichPile = "OppBattlePile";
                            }
                            else if(topCard.equalsIgnoreCase("FlatTire") && player1PunctureProofInPlay == true) {
                                player1BattlePile.add(cardId);
                                whichPile = "OppBattlePile";
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                    }
                }
            }
            else if(cardId.equalsIgnoreCase("25miles") || cardId.equalsIgnoreCase("50miles")) {
                int endOfMilesSpecified = cardId.indexOf('m');
                String miles = cardId.substring(0,endOfMilesSpecified);
                if(player2BattlePile.isEmpty()) {
                    if(player2RightOfWayInPlay == false) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        int milesRolled = Integer.parseInt(miles);
                        player2MileageTotal = player2MileageTotal + milesRolled;
                        whichPile = cardId.concat("Pile");
                    }
                }
                else {
                    String topCard = player2BattlePile.get(player2BattlePile.size() - 1);
                    if(player2RightOfWayInPlay == true) {
                        if(topCard.equalsIgnoreCase("SpareTire") || topCard.equalsIgnoreCase("Gasoline") || topCard.equalsIgnoreCase("SpareTire") |
                                topCard.equalsIgnoreCase("Roll") || topCard.equalsIgnoreCase("Stop")) {
                            int milesRolled = Integer.parseInt(miles);
                            player2MileageTotal = player2MileageTotal + milesRolled;
                            whichPile = cardId.concat("Pile");;
                        }
                        else if(topCard.equalsIgnoreCase("Accident")) {
                            if(player1DrivingAceInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player2MileageTotal = player2MileageTotal + milesRolled;
                                whichPile = cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("OutOfGas")) {
                            if(player1ExtraTankInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player2MileageTotal = player2MileageTotal + milesRolled;
                                whichPile = cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("FlatTire")) {
                            if(player1PunctureProofInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player2MileageTotal = player2MileageTotal + milesRolled;
                                whichPile = cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                    }
                    else {
                        if(!topCard.equalsIgnoreCase("Roll")) {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                        else {
                            int milesRolled = Integer.parseInt(miles);
                            player2MileageTotal = player2MileageTotal + milesRolled;
                            whichPile = cardId.concat("Pile");;
                        }
                    }
                }
            }
            else if(cardId.equalsIgnoreCase("75miles") || cardId.equalsIgnoreCase("100miles") || cardId.equalsIgnoreCase("200miles")) {
                int endOfMilesSpecified = cardId.indexOf('m');
                String miles = cardId.substring(0,endOfMilesSpecified);
                if(player2BattlePile.isEmpty()) {
                    if(player2RightOfWayInPlay == false) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        int milesRolled = Integer.parseInt(miles);
                        player2MileageTotal = player2MileageTotal + milesRolled;
                        whichPile = cardId.concat("Pile");;
                    }
                }
                else {
                    String topCard = player2BattlePile.get(player2BattlePile.size() - 1);
                    if(player2RightOfWayInPlay == true) {
                        if(topCard.equalsIgnoreCase("SpareTire") || topCard.equalsIgnoreCase("Gasoline") || topCard.equalsIgnoreCase("SpareTire") |
                                topCard.equalsIgnoreCase("Roll") || topCard.equalsIgnoreCase("Stop")) {
                            int milesRolled = Integer.parseInt(miles);
                            player2MileageTotal = player2MileageTotal + milesRolled;
                            whichPile = cardId.concat("Pile");;
                        }
                        else if(topCard.equalsIgnoreCase("Accident")) {
                            if(player1DrivingAceInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player2MileageTotal = player2MileageTotal + milesRolled;
                                whichPile = cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("OutOfGas")) {
                            if(player1ExtraTankInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player2MileageTotal = player2MileageTotal + milesRolled;
                                whichPile = cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("FlatTire")) {
                            if(player1PunctureProofInPlay == true) {
                                int milesRolled = Integer.parseInt(miles);
                                player2MileageTotal = player2MileageTotal + milesRolled;
                                whichPile = cardId.concat("Pile");;
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }

                    }
                    else {
                        if(topCard.equalsIgnoreCase("Roll")) {

                            if(player2SpeedPile.isEmpty()) {
                                int milesRolled = Integer.parseInt(miles);
                                player2MileageTotal = player2MileageTotal + milesRolled;
                                whichPile = cardId.concat("Pile");;
                            }
                            else {
                                String topSpeedCard = player2SpeedPile.get(player2SpeedPile.size() - 1);
                                if(topSpeedCard.equalsIgnoreCase("EndOfLimit")) {
                                    int milesRolled = Integer.parseInt(miles);
                                    player2MileageTotal = player2MileageTotal + milesRolled;
                                    whichPile = cardId.concat("Pile");;
                                }
                                else {
                                    discardPile.add(playedCard);
                                    whichPile = "DiscardPile";
                                }
                            }
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                    }
                }

            }
            else if(cardId.equalsIgnoreCase("Roll")) {
                if(player2BattlePile.isEmpty()) {
                    if(player2RightOfWayInPlay == true) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        player2BattlePile.add(cardId);
                        whichPile = "MyBattlePile";
                    }
                }
                else {
                    if(player2RightOfWayInPlay== true) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        String topCard = player2BattlePile.get(player2BattlePile.size() - 1);
                        if(topCard.equalsIgnoreCase("Repairs") || topCard.equalsIgnoreCase("Gasoline") || topCard.equalsIgnoreCase("SpareTire") || topCard.equalsIgnoreCase("Stop")) {
                            player2BattlePile.add(cardId);
                            whichPile = "MyBattlePile";
                        }
                        else if(topCard.equalsIgnoreCase("Accident")) {
                            if(player2DrivingAceInPlay == true) {
                                player2BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("OutOfGas")) {
                            if(player2ExtraTankInPlay == true) {
                                player2BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else if(topCard.equalsIgnoreCase("FlatTire")) {
                            if(player2PunctureProofInPlay == true) {
                                player2BattlePile.add(cardId);
                                whichPile = "MyBattlePile";
                            }
                            else {
                                discardPile.add(playedCard);
                                whichPile = "DiscardPile";
                            }
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                    }
                }
            }
            else if(cardId.equalsIgnoreCase("RightOfWay") || cardId.equalsIgnoreCase("PunctureProof") ||
                    cardId.equalsIgnoreCase("ExtraTank") || cardId.equalsIgnoreCase("DrivingAce")) {
                if(cardId.equalsIgnoreCase("RightOfWay")) {
                    player2RightOfWayInPlay = true;
                }
                else if(cardId.equalsIgnoreCase("PunctureProof")) {
                    player2PunctureProofInPlay = true;
                }
                else if(cardId.equalsIgnoreCase("ExtraTank")) {
                    player2ExtraTankInPlay = true;
                }
                else if(cardId.equalsIgnoreCase("DrivingAce")) {
                    player2DrivingAceInPlay = true;
                }
                whichPile = "SafetyPile";
            }
            else if(cardId.equalsIgnoreCase("EndOfLimit")) {
                if(player2RightOfWayInPlay == true) {
                    discardPile.add(playedCard);
                    whichPile = "DiscardPile";
                }
                else {
                    if(player2SpeedPile.isEmpty()) {
                        discardPile.add(playedCard);
                        whichPile = "DiscardPile";
                    }
                    else {
                        String topCard = player2SpeedPile.get(player2SpeedPile.size() - 1);
                        if(topCard.equalsIgnoreCase("SpeedLimit")) {
                            player2SpeedPile.add(cardId);
                            whichPile = "MySpeedPile";
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }

                    }
                }
            }
            else if(cardId.equalsIgnoreCase("SpeedLimit")) {
                if(player1RightOfWayInPlay == true) {
                    discardPile.add(playedCard);
                    whichPile = "DiscardPile";
                }
                else {
                    if(player1SpeedPile.isEmpty()) {
                        player1SpeedPile.add(cardId);
                        whichPile = "OppSpeedPile";
                    }
                    else {
                        String topCard = player1SpeedPile.get(player1SpeedPile.size() - 1);
                        if(topCard.equalsIgnoreCase("EndOfLimit")) {
                            player1SpeedPile.add(cardId);
                            whichPile = "OppSpeedPile";
                        }
                        else {
                            discardPile.add(playedCard);
                            whichPile = "DiscardPile";
                        }
                    }
                }
            }
        }
        return whichPile;
    }
    /**
     * Shuffles the deck and receives the cards to be dealt.
     *
     * @param playerId the player
     * @return a ArrayList of cards based on which player the server
     * requests the hand for.
     */
    public ArrayList getHand(int playerId) {

        //Shuffle the deck
        deck.shuffle();

        //Receive the cards to be dealt
        handsToDeal = deck.dealHand();

        //Used to alternate cards being dealt so that each player
        //receives every other card
        int cardCount = 0;

        Iterator it = handsToDeal.iterator();

        while(it.hasNext()){
            if(cardCount%2 == 0) {
                player1Hand.add((JRadioButton)it.next());
            }
            else {
                player2Hand.add((JRadioButton)it.next());
            }
            cardCount++;
        }
        handsToDeal.clear();//remove all the cards from the ArrayList

        //Return the hands
        if(playerId == 0) {
            return player1Hand;
        }
        else {
            return player2Hand;
        }
    }
    /**
     * Returns a single card to be dealt to the player
     * at the start of their turn.
     * @return dealt card
     */
    public JRadioButton getDealtCard() {
        return deck.drawCard();
    }
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
