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
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chanakinllc.thousandmiles.cards.Card;
import com.chanakinllc.thousandmiles.cards.CardPile;


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
    protected static final String CLIP_DATA_DESCRIPTION_TAG = "ClipDataDescription";
    public static final String CARD_ORIGINAL_HAND_POSITION = "cardOriginalHandPosition";

    private ViewGroup [] slotsForCardsInHand;
    private ViewGroup [] slotsForCardsInOpponentHand;
    private Card [] playerHand;

    private GameBoardListener mListener;

    private ViewGroup drawPile;
    private ViewGroup battlePile;
    private ViewGroup discardPile;
    private ViewGroup safetyPile;
    private ViewGroup speedPile;
    private ViewGroup distancePile;
    private ViewGroup opponentSpeedPile;
    private ViewGroup opponentBattlePile;
    private ViewGroup opponentDistancePile;
    private ViewGroup opponentSafetyPile;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface GameBoardListener {
        //This should check the rules and will receive a call that it's invalid (return the card back to its original state)
        //Or if it's valid, nothing, so it will stay
        public void onCardPlayed(Card card, CardPile whichPile, int handIndexOfPlayedCard);

        //This should either trigger an error (from the rules) or a call to place the drawn card in the player's hand
        public void requestedCardFromDrawPile();
    }

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

        setUpCardPiles(v);
        setUpHand(v);
        setUpOpponentHand(v);

        return v;
    }

    private void setUpOpponentHand(View v) {
        View opponentHandViewGroup = v.findViewById(R.id.opponent_hand);
        slotsForCardsInOpponentHand = new ViewGroup[6];

        slotsForCardsInOpponentHand[0] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_1);
        slotsForCardsInOpponentHand[1] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_2);
        slotsForCardsInOpponentHand[2] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_3);
        slotsForCardsInOpponentHand[3] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_4);
        slotsForCardsInOpponentHand[4] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_5);
        slotsForCardsInOpponentHand[5] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_6);
    }

    private void setUpHand(View v) {
        View playerHandView = v.findViewById(R.id.hand);

        slotsForCardsInHand = new ViewGroup[6];

        slotsForCardsInHand[0] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_1);
        slotsForCardsInHand[1] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_2);
        slotsForCardsInHand[2] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_3);
        slotsForCardsInHand[3] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_4);
        slotsForCardsInHand[4] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_5);
        slotsForCardsInHand[5] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_6);

        for ( int i = 0; i < slotsForCardsInHand.length; i++) {
            if( i >= playerHand.length ) {
                Log.i("GameBoard", "Hand passed in does not contain 6 cards");
                break;
            }

            final Card card = playerHand[i];
            ViewGroup handCardViewGroup = slotsForCardsInHand[i];

            ImageView cardView = new ImageView(getActivity());
            cardView.setTag(card);
            cardView.setBackgroundResource(card.getCardImageResourceId());

            final int indexInHand = i;

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent cardIntent = new Intent();
                    cardIntent.putExtra(CARD_DATA_EXTRA, card);
                    cardIntent.putExtra(CARD_ORIGINAL_HAND_POSITION, indexInHand);

                    //TODO see if ClipData.newIntent can replace all this logic
                    ClipData.Item item = new ClipData.Item(cardIntent);

                    ClipData data = new ClipData(new ClipDescription(CLIP_DATA_DESCRIPTION_TAG, new String[]{ClipDescription.MIMETYPE_TEXT_INTENT}), item);

                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    return true;
                }
            });

            handCardViewGroup.addView(cardView);
        }
    }

    private void setUpCardPiles(View v) {
        // For each of the "Pile" views, if they are a pile that can receive a drop event,
        // then set the listener and the tag that will define their pile type
        battlePile = getPlayablePile(v, R.id.battle_pile, CardPile.BATTLE);
        discardPile = getPlayablePile(v, R.id.discard_pile, CardPile.DISCARD);
        safetyPile = getPlayablePile(v, R.id.safety_pile, CardPile.SAFETY);
        speedPile = getPlayablePile(v, R.id.speed_pile, CardPile.SPEED);
        opponentSpeedPile = getPlayablePile(v, R.id.opponent_speed_pile, CardPile.OPPONENT_SPEED);
        opponentBattlePile = getPlayablePile(v, R.id.opponent_battle_pile, CardPile.OPPONENT_BATTLE);
        distancePile = getPlayablePile(v, R.id.distance_piles, CardPile.DISTANCE);
        opponentDistancePile = (ViewGroup) v.findViewById(R.id.opponent_distance_piles);
        opponentSafetyPile = (ViewGroup) v.findViewById(R.id.opponent_safety_pile);

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
    }


    private ViewGroup getPlayablePile( View rootView, int pileViewId, CardPile pileType ) {
        ViewGroup pileView = (ViewGroup) rootView.findViewById(pileViewId);
        pileView.setTag(pileType);
        pileView.setOnDragListener(cardPileDragListener);
        return pileView;
    }

    View.OnDragListener cardPileDragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch (dragEvent.getAction()) {

                case DragEvent.ACTION_DROP: {
                    // Place it inside the targeted pile, then send to activity to verify the place
                    ClipData cardData = dragEvent.getClipData();

                    if (cardData.getItemCount() > 0) {
                        ClipData.Item cardHolder = cardData.getItemAt(0);

                        if (null != cardHolder.getIntent()) {
                            Intent cardIntent = cardHolder.getIntent();

                            if( null != cardIntent ) {
                                Card card = (Card) cardIntent.getParcelableExtra(CARD_DATA_EXTRA);
                                int indexOfCardInHand = cardIntent.getIntExtra(CARD_ORIGINAL_HAND_POSITION, -1);

                                mListener.onCardPlayed(card, (CardPile) view.getTag(), indexOfCardInHand);

                                View cardView = (View) dragEvent.getLocalState();
                                ViewGroup owner = (ViewGroup) cardView.getParent();
                                owner.removeView(cardView);
                                ((ViewGroup) view).addView(cardView);
                                cardView.setOnLongClickListener(null);//Can't drag this fellow anymore
                            }
                        }

                    }
                    break;
                }
                case DragEvent.ACTION_DRAG_STARTED:
                case DragEvent.ACTION_DRAG_ENTERED:
                    view.setBackgroundResource(R.drawable.card_pile_border_drop_zone);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    view.setBackgroundResource(R.drawable.card_pile_border);
                    break;
                case DragEvent.ACTION_DRAG_ENDED: {
                    //Return the card to the slot in the hand it once occupied.
                    if( !dragEvent.getResult() ) {
                        ClipData cardData = dragEvent.getClipData();

                        if (cardData.getItemCount() > 0) {
                            ClipData.Item cardHolder = cardData.getItemAt(0);

                            if (null != cardHolder.getIntent()) {
                                Intent cardIntent = cardHolder.getIntent();

                                if( null != cardIntent ) {
                                    placeCardInHand((View) dragEvent.getLocalState(),  cardIntent.getIntExtra(CARD_ORIGINAL_HAND_POSITION, -1));
                                }
                            }

                        }
                    }

                    break;
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

    public void placeDrawnCardInHand(final Card drawnCard, final int indexInHand) {
        if( getActivity() != null ) {
            ImageView cardView = new ImageView(getActivity());
            cardView.setTag(drawnCard);
            cardView.setBackgroundResource(drawnCard.getCardImageResourceId());
            cardView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent cardIntent = new Intent();
                    cardIntent.putExtra(CARD_DATA_EXTRA, drawnCard);
                    cardIntent.putExtra(CARD_ORIGINAL_HAND_POSITION, indexInHand);

                    //TODO see if ClipData.newIntent can replace all this logic
                    ClipData.Item item = new ClipData.Item(cardIntent);

                    ClipData data = new ClipData(new ClipDescription(CLIP_DATA_DESCRIPTION_TAG, new String[]{ClipDescription.MIMETYPE_TEXT_INTENT}), item);

                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    return true;
                }
            });

            drawPile.addView(cardView); //Start it in the draw pile

            placeCardInHand(cardView, indexInHand); //Should animate the move over to the hand
        }
    }

    private void placeCardInHand(View cardView, int indexInHand) {
        if( indexInHand < 0 ) {
            Log.e("CARD PLACEMENT IN HAND REQUEST", "Index was not valid for the slot in hand; card will not be placed.");
            return;
        }

        if(null != cardView.getParent()) {
            ViewGroup parentView = (ViewGroup) cardView.getParent();
            parentView.removeView(cardView);
        }

        ViewGroup slotInHand = slotsForCardsInHand[indexInHand];

        //TODO I don't know that this will actually position it properly, so verify :)
        TranslateAnimation translateAnimation = new TranslateAnimation(cardView.getX(), slotInHand.getX(), cardView.getY(), slotInHand.getY());
        translateAnimation.setDuration(1000);
        translateAnimation.startNow();

        slotInHand.addView(cardView);
    }

    public void onPlayDeemedInvalid(CardPile incorrectPile, int indexInHandToReturnCardTo) {
        View cardView = null;

        switch (incorrectPile) {
            case SPEED:
                cardView = speedPile.getChildAt(speedPile.getChildCount());
                break;
            case SAFETY:
                cardView = safetyPile.getChildAt(safetyPile.getChildCount());
                break;
            case BATTLE:
                cardView = battlePile.getChildAt(battlePile.getChildCount());
                break;
            case DISTANCE:
                cardView = distancePile.getChildAt(distancePile.getChildCount());
                break;
            case OPPONENT_SPEED:
                cardView = opponentSpeedPile.getChildAt(opponentSpeedPile.getChildCount());
                break;
            case OPPONENT_BATTLE:
                cardView = opponentBattlePile.getChildAt(opponentBattlePile.getChildCount());
                break;
        }

        if( null != cardView ) {
            placeCardInHand(cardView, indexInHandToReturnCardTo);
        }
    }

    public void displayComputerPlay(Card card, int indexOfCardInComputerHand) {

        ImageView cardView = new ImageView(getActivity());
        cardView.setTag(card);
        cardView.setBackgroundResource(card.getCardImageResourceId());
        cardView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ViewGroup originatingView = slotsForCardsInOpponentHand[indexOfCardInComputerHand];

        originatingView.addView(cardView);


        ViewGroup pile = null;

        switch(card.getPileType()) {

            case SPEED:
                pile = opponentSpeedPile;
                break;
            case SAFETY:
                pile = opponentSafetyPile;
                //TODO need to figure out which card precisely this needs to go to
                break;
            case BATTLE:
                pile = opponentBattlePile;
                break;
            case DISTANCE:
                pile = opponentDistancePile;
                //TODO need to figure out which card precisely this needs to go to
                break;
            case DISCARD:
                pile = discardPile;
                break;
            case OPPONENT_SPEED:
                pile = speedPile;
                break;
            case OPPONENT_BATTLE:
                pile = battlePile;
                break;
        }

        TranslateAnimation translateAnimation = new TranslateAnimation(cardView.getX(), pile.getX(), cardView.getY(), pile.getY());
        translateAnimation.setDuration(1000);
        translateAnimation.startNow();

        originatingView.removeView(cardView);
        pile.addView(cardView);
    }
}
