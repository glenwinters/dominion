package org.gm.cardgame.dominion;

import java.util.ArrayList;
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
    protected List<DominionCard> playArea;

    public DominionGame( List<DominionPlayer> players, List<DominionCard> cards ) // probably want to change players to List<User>
    {
        boolean useShelters = false; // this affects players' starting hands
        boolean useColonies = false; // this affects whether or not colonies and platinums are on the table
        
        this.players = new DominionPlayer[players.size()];
        for ( int i = 0; i < players.size(); i++ )
        {
            this.players[i] = players.get( i );
        }
        this.table = new DominionTable( cards, this.players.length, useColonies );
        playArea = new ArrayList<DominionCard>();
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

        while ( actions > 0 && !doneActions ) // && player has any action cards
                                              // in hand
        {
            DominionCard cardToPlay = currentPlayer.promptToPlay(); // prompt by
                                                                    // type?
            if ( cardToPlay == null )
            {
                // chose 'done actions'.
                // are you sure?
                // if yes, break
                // otherwise, continue;
            }
            if ( cardToPlay.getType().contains( DominionCard.CardType.ACTION ) )
            {
                actions--;
                currentPlayer.getHand().remove( cardToPlay );
                
                // check for attack reactions

                cardToPlay.onPlay( this );
                
                if( !cardToPlay.isTrashed() )
                {
                    // some cards get trashed as an effect of being played.
                    playArea.add( cardToPlay );                    
                }
            }
        }

        while ( !doneTreasures ) // && player has any treasures in hand
        {
            DominionCard cardToPlay = currentPlayer.promptToPlay(); // restrict
                                                                    // to
                                                                    // treasures
            if ( cardToPlay == null )
            {
                // chose 'done treasures'
                // are you sure?
                // if yes, break
                // otherwise, continue
            }
            cardToPlay.onPlay( this );
        }

        while ( buys > 0 )
        {
            String cardName = currentPlayer.promptToBuy(); // need to take max
                                                           // coins into account
            if ( cardName == null )
            {
                // not buying anything
                // are you sure?
                // if yes, go to cleanup phase.
            }
            buys--;
            // check for on-buy reactions
            DominionCard cardToGain = gainCard( cardName );
            currentPlayer.gainCard( cardToGain );
        }
        
        // clean-up phase
        currentPlayer.discardHand();
        currentPlayer.cleanUpPlayedCards( playArea );
        currentPlayer.drawCards( 5 );
    }

    // there may be a better way to do this
    /*
     * Take a card from the kingdom and return it Note the returned card may not
     * be the named one, due to reactions If this returns null, no card is
     * gained.
     */
    public DominionCard gainCard( String cardName )
    {
        DominionCard cardToGain = table.gainCard( cardName );
        
        if( cardToGain == null )
        {
            // Should never happen.
            // Log an exception and abort game, because whatever allowed this to happen needs fixing.
        }
        
        // check for on-gain effects
        // Reactions:
        // trader might replace 'cardToGain' with a Silver card and put the
        // other one back in the kingdom
        // Watchtower might redirect it to the player's deck or the trash pile
        // instead.
        
        // Effects:
        // A lot of Hinterlands cards have "when you gain this..." effects despite not being reactions. 
        return cardToGain;
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

    /**
     * Gets the next player to play (i.e. the one to the player's left)
     * @return The DominionPlayer of the player up next.
     */
    public DominionPlayer getNextPlayer()
    {
        return players[(currentPlayerIndex + 1) % players.length];
    }

    /**
     * Gets the opponents, in order.
     * @return a List of the other players, in play order.
     */
    public List<DominionPlayer> getOpponents()
    {
        ArrayList<DominionPlayer> opponents = new ArrayList<DominionPlayer>();
        for ( int i = 0; i < players.length; i++ )
        {
            opponents.add( players[(currentPlayerIndex + 1 + i) % players.length] );
        }
        return opponents;
    }
}
