package org.max.trello.entities;


import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

public class Board extends SugarRecord<Board> {

    @Getter @Setter
    private String webId;

    @Getter @Setter
    private Boolean closed;

    @Getter @Setter
    private String name;

}
