package com.chanakinllc.thousandmiles;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanakinllc.thousandmiles.cards.Card;

import java.util.ArrayList;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameBoardFragment.GameBoardListener} interface
 * to handle interaction events.
 * Use the {@link GameBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class GameBoardFragment extends Fragment {
    private static final String PLAYER_HAND = "PlayerHand";
    private static final String OPPONENT_HAND = "OpponentHand";

    private static final String CARD_DATA_EXTRA = "CardData";

    private ArrayList<Card> playerHand;
    private ArrayList<Card> opponentHand;
    private ArrayList<String> tagsForCardsInHand;

    private GameBoardListener mListener;

    public static GameBoardFragment newInstance(ArrayList<Card> playerHand, ArrayList<Card> opponentHand) {
        GameBoardFragment fragment = new GameBoardFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PLAYER_HAND, playerHand);
        args.putParcelableArrayList(OPPONENT_HAND, opponentHand);
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
            playerHand = getArguments().getParcelableArrayList(PLAYER_HAND);
            opponentHand = getArguments().getParcelableArrayList(OPPONENT_HAND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_board, container, false);

        ArrayList<ViewGroup> viewsForCardsInHand = new ArrayList<ViewGroup>();

        viewsForCardsInHand.add((ViewGroup) v.findViewById(R.id.hand_card_1));
        viewsForCardsInHand.add((ViewGroup) v.findViewById(R.id.hand_card_2));
        viewsForCardsInHand.add((ViewGroup) v.findViewById(R.id.hand_card_3);
        viewsForCardsInHand.add((ViewGroup) v.findViewById(R.id.hand_card_4));
        viewsForCardsInHand.add((ViewGroup) v.findViewById(R.id.hand_card_5));
        viewsForCardsInHand.add((ViewGroup) v.findViewById(R.id.hand_card_6));

        tagsForCardsInHand = new ArrayList<String>();

        for( int i = 0; i < viewsForCardsInHand.size() && i < playerHand.size(); i++ ) {
            ViewGroup viewGroup = viewsForCardsInHand.get(i);

            final Card card = playerHand.get(i);

            viewGroup.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent cardIntent = new Intent();
                    cardIntent.putExtra(CARD_DATA_EXTRA, card);
                    ClipData data = ClipData.newIntent("CardIntent", cardIntent);

                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    return true;
                }
            });

            viewGroup.setOnDragListener( dragListener );
            CardFragment cardFragment = CardFragment.newInstance(playerHand.get(i));
            String tag = "handCard" + i;
            tagsForCardsInHand.add(tag);
            getActivity().getSupportFragmentManager().beginTransaction().add(viewsForCardsInHand.get(i).getId(), cardFragment, tag).commit();
        }

        return v;
    }

    View.OnDragListener dragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent dragEvent) {
            switch( dragEvent.getAction() ){

                case DragEvent.ACTION_DROP:
                    View view = (View) dragEvent.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);

                case DragEvent.ACTION_DRAG_STARTED:
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_EXITED:
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;


            }
            return true;
        }
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface GameBoardListener {
        public void onFragmentInteraction(Uri uri);
    }

}
