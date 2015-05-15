package org.max.trello.activities;


import android.os.Bundle;

import org.max.trello.entities.Card;

public class NewCardActivity extends CardActivity {

    private String listWebId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListIdFromIntent();
    }

    private void getListIdFromIntent() {
        listWebId = getIntent().getStringExtra(CardActivity.CARD_LIST_WEBID_PARAM);
    }

    @Override
    public void saveData() {
        Card card = new Card();
        card.setIdList(listWebId);
        card.setName(name.getText().toString());
        card.setDesc(description.getText().toString());
        card.setEdited(true);
        cardsDao.save(card);
    }
}
