package org.max.trello.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.max.trello.R;
import org.max.trello.entities.Card;
import org.max.trello.fragments.CardListFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import lombok.Setter;


public class CardView extends LinearLayout {

    @InjectView(R.id.name)
    TextView nameText;

    @InjectView(R.id.description)
    TextView descriptionText;

    @InjectView(R.id.edited)
    TextView editedText;

    private Card card;

    @Setter
    private CardListFragment cardListFragment;

    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.card_list_item, this);
        ButterKnife.inject(this, view);
    }

    public void setCard(Card card) {
        this.card = card;
        nameText.setText(card.getName());
        descriptionText.setText(card.getDesc());
        if(card.getEdited() != null && card.getEdited()) {
            editedText.setVisibility(View.VISIBLE);
        } else {
            editedText.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.edit)
    public void editCard() {
        cardListFragment.editCard(card);
    }

    @OnClick(R.id.move)
    public void moveCardToAnotherList() {
        cardListFragment.moveCard(card);
    }

    @OnClick(R.id.delete)
    public void deleteCard() {
        cardListFragment.deleteCard(card);
    }
}
