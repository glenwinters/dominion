package org.gm.cardgame;

import java.util.List;

public abstract class Game
{
    protected List<Player> players;
    
    public abstract void startGame(List<Player> players);
    public abstract boolean isGameOver();
    public abstract void nextTurn();
}
