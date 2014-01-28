package com.chanakinllc.thousandmiles;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chanakinllc.thousandmiles.cards.Card;
import com.chanakinllc.thousandmiles.cards.CardPile;

import java.util.ArrayList;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameBoardFragment.GameBoardListener} interface
 * to handle interaction events.
 * Use the {@link GameBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameBoardFragment extends Fragment {
    private static final String PLAYER_HAND = "PlayerHand";

    private static final String CARD_DATA_EXTRA = "CardData";
    protected static final String CARD_DATA_TAG = "CardData";
    private static final int CARD_PILE_TAG = 100;
    private static final int CARD_TAG = 200;

    private Card [] playerHand;

    private GameBoardListener mListener;

    private ViewGroup drawPile;
    private ImageView battlePile;
    private ImageView discardPile;
    private ImageView safetyPile;
    private ImageView speedPile;
    private ImageView opponentSpeedPile;
    private ImageView opponentBattlePile;
    private ImageView lastCardPlayed;

    public static GameBoardFragment newInstance(Card [] playerHand) {
        GameBoardFragment fragment = new GameBoardFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(PLAYER_HAND, playerHand);
        fragment.setArguments(args);
        return fragment;
    }

    public GameBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playerHand = (Card []) getArguments().getParcelableArray(PLAYER_HAND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_board, container, false);

        ViewGroup [] viewsForCardsInHand = new ViewGroup[6];

        //TODO can I wrap this in a nice method?
        viewsForCardsInHand[0] = (ViewGroup) v.findViewById(R.id.hand_card_1);
        viewsForCardsInHand[1] = (ViewGroup) v.findViewById(R.id.hand_card_2);
        viewsForCardsInHand[2] = (ViewGroup) v.findViewById(R.id.hand_card_3);
        viewsForCardsInHand[3] = (ViewGroup) v.findViewById(R.id.hand_card_4);
        viewsForCardsInHand[4] = (ViewGroup) v.findViewById(R.id.hand_card_5);
        viewsForCardsInHand[5] = (ViewGroup) v.findViewById(R.id.hand_card_6);

        // For each of the "Pile" views, if they are a pile that can receive a drop event,
        // then set the listener and the tag that will define their pile type
        battlePile = getPlayablePile(v, R.id.battle_pile, CardPile.BATTLE);
        discardPile = getPlayablePile(v, R.id.discard_pile, CardPile.DISCARD);
        safetyPile = getPlayablePile(v, R.id.safety_pile, CardPile.SAFETY);
        speedPile = getPlayablePile(v, R.id.speed_pile, CardPile.SPEED);
        opponentSpeedPile = getPlayablePile(v, R.id.opponent_speed_pile, CardPile.OPPONENT_SPEED);
        opponentBattlePile = getPlayablePile(v, R.id.opponent_battle_pile, CardPile.OPPONENT_BATTLE);

        // Draw pile is not a drop target, however we do want to request a card
        // if the pile is clicked on
        // This way drawing is not "automated"--so if you forget, just like in real life,
        // Tough luck!
        drawPile = (ViewGroup) v.findViewById(R.id.draw_pile);
        drawPile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.requestedCardFromDrawPile();
            }
        });

        for (int i = 0; i < viewsForCardsInHand.length; i++) {
            if( i >= playerHand.length ) {
                Log.i("GameBoard", "Hand passed in does not contain 6 cards");
                break;
            }

            final Card card = playerHand[i];
            ViewGroup handCardViewGroup = viewsForCardsInHand[i];

            ImageView cardView = new ImageView(getActivity());
            cardView.setTag(CARD_TAG, card);
            cardView.setBackgroundResource(card.getCardImageResourceId());

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent cardIntent = new Intent();
                    cardIntent.putExtra(CARD_DATA_EXTRA, card);

                    //TODO see if ClipData.newIntent can replace all this logic
                    ClipData.Item item = new ClipData.Item(cardIntent);

                    ClipData data = new ClipData(new ClipDescription(CARD_DATA_TAG, new String[]{ClipDescription.MIMETYPE_TEXT_INTENT}), item);

                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    lastCardPlayed = (ImageView) view;
                    return true;
                }
            });

            handCardViewGroup.addView(cardView);
        }

        return v;
    }

    private ImageView getPlayablePile( View rootView, int pileViewId, CardPile pileType ) {
        ImageView pileView = (ImageView) rootView.findViewById(pileViewId);
        pileView.setTag(CARD_PILE_TAG, pileType);
        pileView.setOnDragListener(cardPileDragListener);
        return pileView;
    }

    View.OnDragListener cardPileDragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch (dragEvent.getAction()) {

                case DragEvent.ACTION_DROP:
                    ClipData cardData = dragEvent.getClipData();

                    if (cardData.getItemCount() > 0) {
                        ClipData.Item cardHolder = cardData.getItemAt(0);

                        if (null != cardHolder.getIntent()) {
                            Card card = (Card) cardHolder.getIntent().getParcelableExtra(CARD_DATA_EXTRA);
                            mListener.onCardPlayed(card, (CardPile) view.getTag(CARD_PILE_TAG));
                            //TODO do I want to set the background resource, or just add the card to a layout instead?
                            view.setBackgroundResource(card.getCardImageResourceId());
                            view.setTag(CARD_TAG, card);
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_STARTED:
                case DragEvent.ACTION_DRAG_ENTERED:
                    view.setBackgroundResource(R.drawable.card_pile_border_drop_zone);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    view.setBackgroundResource(R.drawable.card_pile_border);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //TODO Send view back to its original position...but how? Need to research more
                    if( !dragEvent.getResult() ) {

                    }
                default:
                    break;


            }
            return true;
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (GameBoardListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement GameBoardListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface GameBoardListener {
        //This should check the rules and will receive a call that it's invalid (return the card back to its original state)
        //Or if it's valid, nothing, so it will stay
        public void onCardPlayed(Card card, CardPile whichPile);

        //This should either trigger an error (from the rules) or a call to place the drawn card in the player's hand
        public void requestedCardFromDrawPile();
    }

    public void placeDrawnCardInHand(Card drawnCard) {
        if( getActivity() != null ) {
            ImageView cardView = new ImageView(getActivity());
            cardView.setTag(CARD_TAG, drawnCard);
            cardView.setBackgroundResource(drawnCard.getCardImageResourceId());
            cardView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            drawPile.addView(cardView);

            View lastSlotInHand = getView().findViewById(R.id.hand_card_6);

            //TODO I don't know that this will actually position it properly, so verify :)
            TranslateAnimation translateAnimation = new TranslateAnimation(cardView.getX(), lastSlotInHand.getX(), cardView.getY(), lastSlotInHand.getY());
            translateAnimation.setDuration(1000);

            translateAnimation.startNow();
        }

    }

    //TODO complete this
    public void onPlayDeemedInvalid(Card lastCardInPile, CardPile incorrectPile) {
        if( lastCardInPile == null ) {
            switch (incorrectPile) {

                case SPEED:
                    break;
                case SAFETY:
                    break;
                case BATTLE:
                    break;
                case DISTANCE:
                    break;
                case DISCARD:
                    break;
                case DRAW:
                    break;
                case OPPONENT_SPEED:
                    break;
                case OPPONENT_BATTLE:
                    break;
            }
        }
    }
}
