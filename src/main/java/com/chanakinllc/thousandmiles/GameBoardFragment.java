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
    private ViewGroup speedPile;
    private ViewGroup opponentSpeedPile;
    private ViewGroup opponentBattlePile;
    private ViewGroup opponentDistancePile;
    private ViewGroup safetyPile1;
    private ViewGroup safetyPile2;
    private ViewGroup safetyPile3;
    private ViewGroup safetyPile4;

    private ViewGroup twentyFiveMilesPile;
    private ViewGroup fiftyMilesPile;
    private ViewGroup seventyFiveMilesPile;
    private ViewGroup oneHundredMilesPile;
    private ViewGroup twoHundredMilesPile;

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
        slotsForCardsInOpponentHand = new ViewGroup[7];

        slotsForCardsInOpponentHand[0] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_0);
        slotsForCardsInOpponentHand[1] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_1);
        slotsForCardsInOpponentHand[2] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_2);
        slotsForCardsInOpponentHand[3] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_3);
        slotsForCardsInOpponentHand[4] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_4);
        slotsForCardsInOpponentHand[5] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_5);
        slotsForCardsInOpponentHand[6] = (ViewGroup) opponentHandViewGroup.findViewById(R.id.hand_card_6);
    }

    private void setUpHand(View v) {
        View playerHandView = v.findViewById(R.id.hand);

        slotsForCardsInHand = new ViewGroup[7];

        slotsForCardsInHand[0] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_0);
        slotsForCardsInHand[1] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_1);
        slotsForCardsInHand[2] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_2);
        slotsForCardsInHand[3] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_3);
        slotsForCardsInHand[4] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_4);
        slotsForCardsInHand[5] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_5);
        slotsForCardsInHand[6] = (ViewGroup) playerHandView.findViewById(R.id.hand_card_6);

        // Slot zero is reserved for special occasions when we have 7 cards in the hand (aka, upon the start of a turn)
        // Since this is initialization of the hand, we do start with 7 cards
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
                    cardIntent.putExtra(CARD_ORIGINAL_HAND_POSITION, indexInHand);

                    ClipData data = ClipData.newIntent(CLIP_DATA_DESCRIPTION_TAG, cardIntent);

                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    if(indexInHand == 0 ) {
                        slotsForCardsInHand[0].setVisibility(View.GONE);
                    }
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
        safetyPile1 = getPlayablePile(v.findViewById(R.id.safety_pile), R.id.safety_card_one, CardPile.SAFETY_ONE);
        safetyPile2 = getPlayablePile(v.findViewById(R.id.safety_pile), R.id.safety_card_two, CardPile.SAFETY_TWO);
        safetyPile3 = getPlayablePile(v.findViewById(R.id.safety_pile), R.id.safety_card_three, CardPile.SAFETY_THREE);
        safetyPile4 = getPlayablePile(v.findViewById(R.id.safety_pile), R.id.safety_card_four, CardPile.SAFETY_FOUR);

        speedPile = getPlayablePile(v, R.id.speed_pile, CardPile.SPEED);
        opponentSpeedPile = getPlayablePile(v, R.id.opponent_speed_pile, CardPile.OPPONENT_SPEED);
        opponentBattlePile = getPlayablePile(v, R.id.opponent_battle_pile, CardPile.OPPONENT_BATTLE);
        twentyFiveMilesPile = getPlayablePile(v.findViewById(R.id.distance_piles), R.id.twenty_five_miles_pile, CardPile.TWENTY_FIVE_DISTANCE);
        fiftyMilesPile = getPlayablePile(v.findViewById(R.id.distance_piles), R.id.fifty_miles_pile, CardPile.FIFTY_DISTANCE);
        seventyFiveMilesPile = getPlayablePile(v.findViewById(R.id.distance_piles), R.id.seventy_five_miles_pile, CardPile.SEVENTY_FIVE_DISTANCE);
        oneHundredMilesPile = getPlayablePile(v.findViewById(R.id.distance_piles), R.id.one_hundred_miles_pile, CardPile.ONE_HUNDRED_DISTANCE);
        twoHundredMilesPile = getPlayablePile(v.findViewById(R.id.distance_piles), R.id.two_hundred_miles_pile, CardPile.TWO_HUNDRED_DISTANCE);

        opponentDistancePile = (ViewGroup) v.findViewById(R.id.opponent_distance_piles);

        discardPile = (ViewGroup) v.findViewById(R.id.discard_pile);
        discardPile.setTag(CardPile.DISCARD);
        discardPile.setOnDragListener(new View.OnDragListener() {

            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {

                    case DragEvent.ACTION_DROP: {
                        // Place it inside the targeted pile, then send to activity to verify the play
                        ClipData cardData = dragEvent.getClipData();

                        if (cardData.getItemCount() > 0) {
                            ClipData.Item cardHolder = cardData.getItemAt(0);

                            if (null != cardHolder.getIntent()) {
                                Intent cardIntent = cardHolder.getIntent();

                                if( null != cardIntent ) {
                                    View cardView = (View) dragEvent.getLocalState();
                                    Card card = (Card) cardView.getTag();
                                    int indexOfCardInHand = cardIntent.getIntExtra(CARD_ORIGINAL_HAND_POSITION, -1);

                                    mListener.onCardPlayed(card, (CardPile) view.getTag(), indexOfCardInHand);

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
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        view.setBackgroundResource(R.drawable.card_pile_border_drop_zone);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        view.setBackgroundResource(R.drawable.deck_holder);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED: {
                        //Return the card to the slot in the hand it once occupied.
                        if( !dragEvent.getResult() ) {
                            ClipData cardData = dragEvent.getClipData();

                            if (cardData != null && cardData.getItemCount() > 0) {
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
        });

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
                    // Place it inside the targeted pile, then send to activity to verify the play
                    ClipData cardData = dragEvent.getClipData();

                    if (cardData.getItemCount() > 0) {
                        ClipData.Item cardHolder = cardData.getItemAt(0);

                        if (null != cardHolder.getIntent()) {
                            Intent cardIntent = cardHolder.getIntent();

                            if( null != cardIntent ) {
                                View cardView = (View) dragEvent.getLocalState();
                                Card card = (Card) cardView.getTag();
                                int indexOfCardInHand = cardIntent.getIntExtra(CARD_ORIGINAL_HAND_POSITION, -1);

                                mListener.onCardPlayed(card, (CardPile) view.getTag(), indexOfCardInHand);

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
                    break;
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

                        if (cardData != null && cardData.getItemCount() > 0) {
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

        if(indexInHand == 0 ) {
            slotInHand.setVisibility(View.VISIBLE);
        }

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
            case SAFETY_ONE:
                cardView = safetyPile1.getChildAt(safetyPile1.getChildCount());
                break;
            case SAFETY_TWO:
                cardView = safetyPile2.getChildAt(safetyPile2.getChildCount());
                break;
            case SAFETY_THREE:
                cardView = safetyPile3.getChildAt(safetyPile3.getChildCount());
                break;
            case SAFETY_FOUR:
                cardView = safetyPile4.getChildAt(safetyPile4.getChildCount());
                break;
            case BATTLE:
                cardView = battlePile.getChildAt(battlePile.getChildCount());
                break;
            case TWENTY_FIVE_DISTANCE:
                cardView = twentyFiveMilesPile.getChildAt(twentyFiveMilesPile.getChildCount());
                break;
            case FIFTY_DISTANCE:
                cardView = fiftyMilesPile.getChildAt(fiftyMilesPile.getChildCount());
                break;
            case SEVENTY_FIVE_DISTANCE:
                cardView = seventyFiveMilesPile.getChildAt(seventyFiveMilesPile.getChildCount());
                break;
            case ONE_HUNDRED_DISTANCE:
                cardView = oneHundredMilesPile.getChildAt(oneHundredMilesPile.getChildCount());
                break;
            case TWO_HUNDRED_DISTANCE:
                cardView = twoHundredMilesPile.getChildAt(twoHundredMilesPile.getChildCount());
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
            case SAFETY_ONE:
                pile = (ViewGroup) getView().findViewById(R.id.opponent_safety_pile).findViewById(R.id.opponent_safety_card_one);
                //TODO need to figure out which card precisely this needs to go to
                break;
            case SAFETY_TWO:
                pile = (ViewGroup) getView().findViewById(R.id.opponent_safety_pile).findViewById(R.id.opponent_safety_card_two);
                break;
            case SAFETY_THREE:
                pile = (ViewGroup) getView().findViewById(R.id.opponent_safety_pile).findViewById(R.id.opponent_safety_card_three);
                break;
            case SAFETY_FOUR:
                pile = (ViewGroup) getView().findViewById(R.id.opponent_safety_pile).findViewById(R.id.opponent_safety_card_four);
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

    public void displayEmptyDeck() {
        drawPile.setBackgroundResource(R.drawable.deck_holder);
    }
}
