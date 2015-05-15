package org.max.trello.base;

import android.test.ApplicationTestCase;

import org.max.trello.TrelloApplication;


public abstract class AbstractTest extends ApplicationTestCase<TrelloApplication> {

    public AbstractTest() {
        super(TrelloApplication.class);
    }

}
