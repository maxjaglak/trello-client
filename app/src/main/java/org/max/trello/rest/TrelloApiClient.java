package org.max.trello.rest;


import org.max.trello.entities.Board;
import org.max.trello.entities.Card;
import org.max.trello.entities.CardList;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface TrelloApiClient {

    @GET("/1/members/{userId}/boards")
    public List<Board> getBoards(@Path("userId") String userId, @Query("key") String appKey, @Query("token") String token);

    @GET("/1/boards/{boardId}/lists")
    public List<CardList> getCardLists(@Path("boardId") String boardId, @Query("key") String appKey, @Query("token") String token);

    @GET("/1/lists/{listId}/cards")
    public List<Card> getCardsForList(@Path("listId") String listId, @Query("key") String appKey, @Query("token") String token);

    @POST("/1/lists/{listId}/cards")
    public Card postNewCard(@Path("listId") String listId, @Body Card newCard, @Query("key") String appKey, @Query("token") String token);

    @PUT("/1/cards/{cardId}/{field}")
    public Card putCardField(@Path("cardId") String cardId, @Path("field") String field, @Query("value") String value, @Query("key") String appKey, @Query("token") String token);

    @DELETE("/1/cards/{cardId}")
    public Card deleteCard(@Path("cardId") String cardId, @Query("key") String appKey, @Query("token") String token);

}