package org.gm.cardgame;

import java.util.List;

public abstract class Game
{
    public enum GameName
    {
        DOMINION
    }

    protected int currentPlayerIndex;

    protected List<Player> players;

    public Game()
    {
        currentPlayerIndex = -1;
    }

    public abstract void startGame( List<Game> gameList );

    public abstract boolean isGameOver();

    public abstract void nextTurn();
}
