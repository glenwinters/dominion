package org.gm.cardgame.dominion;

import java.util.List;

import org.gm.cardgame.Game;
import org.gm.cardgame.Player;
import org.gm.cardgame.dominion.cards.DominionCard;

public class DominionGame extends Game
{
    protected final DominionPlayer[] players;
    protected final DominionTable table;
    
    // current turn info
    protected int currentPlayerIndex;
    protected int coins;
    protected int potions;
    protected int actions;
    protected int buys;

    public DominionGame( List<DominionPlayer> players, List<DominionCard> cards )
    {
        this.players = new DominionPlayer[players.size()];
        for(int i = 0; i < players.size(); i++)
        {
            this.players[i] = players.get( i );  
        }
        this.table = new DominionTable( cards );
    }

    //this may not be necessary
    public boolean isGameOver()
    {
        return table.isGameOver();
    }

    @Override
    public void startGame( List<Player> players )
    {
        currentPlayerIndex = -1;
        do
        {
            nextTurn();
        } while(table.isGameOver());
        // do any post-game stuff and print out winner, scores, decks, etc.
    }

    @Override
    public void nextTurn()
    {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        coins = 0;
        actions = 1;
        buys = 1;
        players[currentPlayerIndex].takeTurn();
    }
    
    public int getCoins()
    {
        return coins;
    }
    
    public void addCoins( int coinsToAdd )
    {
        coins += coinsToAdd;
    }
    
    public int getPotions()
    {
        return potions;
    }
    
    public void addPotions( int potionsToAdd )
    {
        potions += potionsToAdd;
    }
    
    public int getActions()
    {
        return actions;
    }
    
    public void addActions( int actionsToAdd )
    {
        actions += actionsToAdd;
    }
    
    public int getBuys()
    {
        return buys;
    }
    
    public void addBuys( int buysToAdd )
    {
        buys += buysToAdd;
    }
}
