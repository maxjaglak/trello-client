package org.max.trello.base;

import org.max.trello.entities.Card;

public class CardProvider {

    public static Card getMockCard() {
        Card card = new Card();
        card.setName("test");
        card.setDesc("test");
        return card;
    }
}
