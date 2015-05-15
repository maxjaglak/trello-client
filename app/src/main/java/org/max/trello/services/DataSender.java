package org.max.trello.services;


import android.content.Context;
import android.util.Log;

import org.max.trello.Config;
import org.max.trello.TrelloApplication;
import org.max.trello.dao.CardsDao;
import org.max.trello.entities.Card;
import org.max.trello.events.DataModified;
import org.max.trello.events.FailedToSync;
import org.max.trello.rest.TrelloApiClient;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class DataSender {

    private final Context context;

    @Inject
    CardsDao cardsDao;

    @Inject
    TrelloApiClient trelloApiClient;

    @Inject
    Config config;

    @Inject
    EventBus eventBus;

    public DataSender(Context context) {
        this.context = context;
        TrelloApplication.getApp(context).inject(this);
    }

    public void sendData() {
        try {
            syncDeletedCards();
            syncNewCards();
            syncEditedCards();
            eventBus.postSticky(new DataModified());
        } catch (Exception e) {
            Log.e(TrelloApplication.TAG, e.getMessage(), e);
            eventBus.postSticky(new FailedToSync());
        }
    }

    private void syncDeletedCards() {
        List<Card> deletedCards = cardsDao.getDeletedCards();
        for (Card deletedCard : deletedCards) {
            deleteSingleCard(deletedCard);
        }
    }

    private void deleteSingleCard(Card deletedCard) {
        trelloApiClient.deleteCard(deletedCard.getWebId(), config.getAppKey(), config.getToken());
        cardsDao.delete(deletedCard);
    }

    private void syncNewCards() {
        List<Card> newCards = cardsDao.getNewCards();
        for (Card newCard : newCards) {
            syncSingleNewCard(newCard);
        }
    }

    private void syncSingleNewCard(Card newCard) {
        Card card = trelloApiClient.postNewCard(newCard.getIdList(), newCard, config.getAppKey(), config.getToken());
        newCard.setWebId(card.getWebId());
        newCard.setEdited(false);
        cardsDao.save(newCard);
    }

    private void syncEditedCards() {
        List<Card> editedCards = cardsDao.getEditedCards();
        for (Card editedCard : editedCards) {
            syncSingleEditedCard(editedCard);
        }
    }

    private void syncSingleEditedCard(Card editedCard) {
        trelloApiClient.putCardField(editedCard.getWebId(), "name", editedCard.getName(), config.getAppKey(), config.getToken());
        trelloApiClient.putCardField(editedCard.getWebId(), "desc", editedCard.getDesc(), config.getAppKey(), config.getToken());
        trelloApiClient.putCardField(editedCard.getWebId(), "idList", editedCard.getIdList(), config.getAppKey(), config.getToken());
        editedCard.setEdited(false);
        cardsDao.save(editedCard);
    }
}
