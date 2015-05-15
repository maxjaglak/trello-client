package org.max.trello.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.max.trello.entities.Card;
import org.max.trello.fragments.CardListFragment;
import org.max.trello.views.CardView;

import java.util.List;


public class CardsAdaper extends BaseAdapter {

    private final List<Card> cards;
    private final CardListFragment cardListFragment;
    private final Context context;
    private final LayoutInflater layoutInflater;

    public CardsAdaper(Context context, List<Card> cards, CardListFragment cardListFragment) {
        this.context = context;
        this.cards = cards;
        this.cardListFragment = cardListFragment;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardView view;
        if(convertView == null) {
            view = new CardView(context);
        } else {
            view = (CardView) convertView;
        }
        view.setCardListFragment(cardListFragment);
        view.setCard((Card) getItem(position));
        return view;
    }
}
