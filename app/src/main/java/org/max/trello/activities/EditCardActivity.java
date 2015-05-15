package org.max.trello.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import org.max.trello.dao.CardsDao;
import org.max.trello.entities.Card;
import org.max.trello.loaders.DataLoader;
import org.max.trello.loaders.LoadingCallback;

import javax.inject.Inject;

public class EditCardActivity extends CardActivity implements LoaderManager.LoaderCallbacks<Card>{

    private long cardId;

    @Inject
    CardsDao cardsDao;

    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCardForEdit();
    }

    private void loadCardForEdit() {
        getCardIdFromIntent();
        loadCardData();
    }

    private void getCardIdFromIntent() {
        cardId = getIntent().getLongExtra(CARD_ID_PARAM, -1);
    }

    private void loadCardData() {
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<Card> onCreateLoader(int id, Bundle args) {
        return new DataLoader<>(this, new LoadingCallback<Card>() {
            @Override
            public Card loadData() {
                return cardsDao.getById(cardId);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<Card> loader, Card data) {
        this.card = data;
        showData();
    }

    private void showData() {
        name.setText(card.getName());
        description.setText(card.getDesc());
    }

    @Override
    public void onLoaderReset(Loader<Card> loader) {
        //nothing
    }

    @Override
    public void saveData() {
        card.setName(name.getText().toString());
        card.setDesc(description.getText().toString());
        card.setEdited(true);
        cardsDao.save(card);
    }
}
