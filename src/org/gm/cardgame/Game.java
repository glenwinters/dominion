package org.gm.cardgame;

import java.util.List;

public abstract class Game
{
    public enum GameName
    {
        DOMINION
    }

    protected List<Player> players;

    public abstract void startGame( List<Game> gameList );

    public abstract boolean isGameOver();

    public abstract void nextTurn();
}
