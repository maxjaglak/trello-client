package org.max.trello.services;


import android.content.Context;
import android.util.Log;

import org.max.trello.Config;
import org.max.trello.TrelloApplication;
import org.max.trello.dao.BoardsDao;
import org.max.trello.dao.CardListsDao;
import org.max.trello.dao.CardsDao;
import org.max.trello.entities.Board;
import org.max.trello.entities.Card;
import org.max.trello.entities.CardList;
import org.max.trello.events.DataDownloaded;
import org.max.trello.events.DataModified;
import org.max.trello.events.FailedToSync;
import org.max.trello.rest.TrelloApiClient;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class DataDownloader {

    private final Context context;

    @Inject
    TrelloApiClient trelloApiClient;

    @Inject
    BoardsDao boardsDao;

    @Inject
    CardListsDao cardListsDao;

    @Inject
    CardsDao cardsDao;

    @Inject
    Config config;

    @Inject
    EventBus eventBus;

    private Board board;
    private List<CardList> cardLists;

    public DataDownloader(Context context) {
        this.context = context;
        TrelloApplication.getApp(context).inject(this);
    }

    public void downloadData() {
        try {
            downloadNamedBoard();
            downloadCardLists();
            downloadCards();
            postCompletedEvent();
        } catch (Exception e) {
            logException(e);
            eventBus.postSticky(new FailedToSync());
        }
    }

    private void logException(Exception e) {
        Log.e(TrelloApplication.TAG, e.getMessage(), e);
    }

    private void downloadNamedBoard() {
        List<Board> boards = trelloApiClient.getBoards(config.getUserId(), config.getAppKey(), config.getToken());
        board = findNamedBoard(boards);
        boardsDao.deleteAll();
        boardsDao.save(board);
    }

    private Board findNamedBoard(List<Board> boards) {
        for (Board board : boards) {
            if(config.getMainBoardName().equals(board.getName())) {
                return board;
            }
        }
        throw new IllegalStateException("Main board not present!");
    }

    private void downloadCardLists() {
        cardLists = trelloApiClient.getCardLists(board.getWebId(), config.getAppKey(), config.getToken());
        cardListsDao.deleteAll();
        cardListsDao.save(cardLists);
    }

    private void downloadCards() {
        cardsDao.deleteAll();
        for (CardList cardList : cardLists) {
            List<Card> cardsForList = trelloApiClient.getCardsForList(cardList.getWebId(), config.getAppKey(), config.getToken());
            cardsDao.save(cardsForList);
        }
    }

    private void postCompletedEvent() {
        eventBus.postSticky(new DataDownloaded());
        eventBus.postSticky(new DataModified());
    }
}
