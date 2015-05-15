package org.max.trello.dao;

import org.max.trello.entities.Board;


public class BoardsDao extends BaseDao<Board> {

    public BoardsDao() {
        super(Board.class);
    }
}
