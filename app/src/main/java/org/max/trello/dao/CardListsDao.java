package org.max.trello.dao;

import org.max.trello.entities.Card;
import org.max.trello.entities.CardList;

import java.util.List;

public class CardListsDao extends BaseDao<CardList> {

    public CardListsDao() {
        super(CardList.class);
    }

    public CardList getByWebId(String webId) {
        List<CardList> cardLists = Card.find(clazz, "web_id = ?", webId);
        if(cardLists.isEmpty()) {
            return null;
        } else {
            return cardLists.get(0);
        }
    }

}
