package org.max.trello.entities;


import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

public class Card extends SugarRecord<Card> {

    @Getter @Setter
    private String webId;

    @Getter @Setter
    private Boolean closed;

    @Getter @Setter
    private String idBoard;

    @Getter @Setter
    private String idList;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Integer pos;

    @Getter @Setter
    private String url;

    @Getter @Setter
    private String desc;

    @Getter @Setter
    private Boolean edited;

    @Getter @Setter
    private Boolean toDelete = false;

}
