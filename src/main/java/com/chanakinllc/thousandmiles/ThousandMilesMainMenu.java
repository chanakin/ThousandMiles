package com.chanakinllc.thousandmiles;

import com.chanakinllc.thousandmiles.cards.Card;
import com.chanakinllc.thousandmiles.cards.CardPile;
import com.chanakinllc.thousandmiles.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.widget.Toast;

public class ThousandMilesMainMenu extends FragmentActivity implements GameRulesEngineFragment.GameRulesEngineListener, GameBoardFragment.GameBoardListener {
    private static final String RULES_FRAGMENT = "rulesFragment";
    private static final String BOARD_FRAGMENT = "boardFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu_layout);

        if( null == savedInstanceState ) {
            Log.i("IN MAIN MENU", "ADDING RULES ENGINE");
            GameRulesEngineFragment fragment = GameRulesEngineFragment.newInstance("Chantell");
            getSupportFragmentManager().beginTransaction().add(fragment, RULES_FRAGMENT).commit();
        }
    }

    @Override
    public void requestedCardFromDrawPile() {
        GameRulesEngineFragment rulesEngine = (GameRulesEngineFragment) getSupportFragmentManager().findFragmentByTag(RULES_FRAGMENT);
        rulesEngine.cardDrawRequested();
    }

    @Override
    public void onCardPlayed(Card card, CardPile whichPile, int handIndexOfPlayedCard) {

    }

    @Override
    public void rulesEngineBuilt() {

        Log.i("IN MAIN MENU", "ADDING GAME BOARD FRAGMENT TO VIEW");
        GameRulesEngineFragment rulesEngine = (GameRulesEngineFragment) getSupportFragmentManager().findFragmentByTag(RULES_FRAGMENT);

        GameBoardFragment boardFragment = GameBoardFragment.newInstance(rulesEngine.getPlayerHand());
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, boardFragment, BOARD_FRAGMENT).commit();

        Log.i("IN MAIN MENU", "GAME BOARD FRAGMENT ADDED TO VIEW");
    }

    @Override
    public void gameOver(Player winningPlayer) {
       //TODO display dialog announcing the end of the game!
    }

    @Override
    public void computerPlayed(Card card, int indexOfCardInComputerHand) {

    }

    @Override
    public void computerDrewCard(int indexOfDrawnCardInComputerHand) {

    }

    @Override
    public void playerMayBeginTurn() {

    }

    @Override
    public void playDeemedInvalid(Card card, CardPile whichPile, int indexOfHandToReturnCard) {

    }

    @Override
    public void placeDrawnCardInHand(Card card, int slotIndexInHand, boolean deckEmptyAfterDraw) {
        GameBoardFragment board = (GameBoardFragment) getSupportFragmentManager().findFragmentByTag(BOARD_FRAGMENT);

        if( null != board ) {
            board.placeDrawnCardInHand(card, slotIndexInHand);

            if( deckEmptyAfterDraw ) {
                board.displayEmptyDeck();
            }
        }
    }

    @Override
    public void drawRequestDenied(String errorMessage) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG);
    }

}
