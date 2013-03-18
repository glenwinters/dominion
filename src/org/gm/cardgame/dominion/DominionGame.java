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
        for ( int i = 0; i < players.size(); i++ )
        {
            this.players[i] = players.get( i );
        }
        this.table = new DominionTable( cards );
    }

    // this may not be necessary
    public boolean isGameOver()
    {
        return table.isGameOver();
    }

    @Override
    public void startGame( List<Player> players )
    {
        // this could probably go in Game as it's pretty generic
        currentPlayerIndex = -1;
        do
        {
            nextTurn();
        } while ( table.isGameOver() );
        // do any post-game stuff and print out winner, scores, decks, etc.
    }

    @Override
    public void nextTurn()
    {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        coins = 0;
        actions = 1;
        buys = 1;
        boolean doneActions = false;
        boolean doneTreasures = false;
        DominionPlayer currentPlayer = players[currentPlayerIndex];
        //players[currentPlayerIndex].takeTurn();
        
        while(actions > 0 && !doneActions) // && player has any action cards in hand
        {
            DominionCard cardToPlay = currentPlayer.promptToPlay(); // prompt by type?
            if( cardToPlay == null )
            {
                // chose 'done actions'.
                // are you sure?
                // if yes, set doneActions == true;
                // break;
                // otherwise, continue;
            }
            if( cardToPlay.getType().contains( DominionCard.CardType.ACTION ))
            {
                actions--;
                
                //check for attack reactions
                
                cardToPlay.onPlay( this );
            } else if ( cardToPlay.getType().contains( DominionCard.CardType.TREASURE) )
            {
                // are you sure?
                // if yes, doneActions = true. play card and break;
            }
        }
        
        while ( !doneTreasures ) // && player has any treasures in hand
        {
            DominionCard cardToPlay = currentPlayer.promptToPlay(); // restrict to treasures
            if(cardToPlay == null)
            {
                // chose 'done treasures'
                // are you sure?
                // if yes, set doneTreasures == true.
            }
            cardToPlay.onPlay( this );
        }
        
        while( buys > 0 )
        {
            DominionCard cardToBuy = currentPlayer.promptToBuy(); // need to take max coins into account
            if( cardToBuy == null )
            {
                // not buying anything
                // are you sure?
                // if yes, go to cleanup phase.
            }
            buys--;
            // check for on-buy reactions
            // remove card from kingdom
            // check for on-gain reactions
            currentPlayer.gainCard( cardToBuy );
        }
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
    
    public DominionPlayer getCurrentPlayer()
    {
        return players[currentPlayerIndex];
    }
    
    public DominionPlayer getNextPlayer()
    {
        return players[(currentPlayerIndex + 1) % players.length];
    }
}
