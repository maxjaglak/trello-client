package org.max.trello.dao;

import org.max.trello.entities.Card;

import java.util.List;


public class CardsDao extends BaseDao<Card> {

    public CardsDao() {
        super(Card.class);
    }

    public List<Card> getCardsForList(String listWebId) {
        return Card.find(clazz, "id_list = ? and ( to_delete = ? or to_delete is ?)", listWebId, "0", "null");
    }

    public List<Card> getNewCards() {
        return Card.find(clazz, "web_id is ?", "null");
    }

    public Card getByWebId(String webId) {
        List<Card> cards = Card.find(clazz, "web_id = ?", webId);
        if(!cards.isEmpty()) {
            return cards.get(0);
        }
        return null;
    }

    public List<Card> getEditedCards() {
        return Card.find(clazz, "edited = ?", "1");
    }

    public List<Card> getDeletedCards() {
        return Card.find(clazz, "to_delete = ?", "1");
    }
}
