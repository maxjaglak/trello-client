package org.max.trello.entities;


import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

public class CardList extends SugarRecord<CardList> {

    @Setter @Getter
    private String webId;

    @Setter @Getter
    private String name;

}
