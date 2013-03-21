package org.gm.cardgame.dominion;

import java.util.ArrayList;
import java.util.List;

import org.gm.cardgame.Game;
import org.gm.cardgame.User;
import org.gm.cardgame.dominion.cards.DominionCard;

public class DominionGame extends Game
{
    protected final DominionPlayer[] players;
    protected final DominionTable table;

    // current turn info

    protected int coins;
    protected int potions;
    protected int actions;
    protected int buys;
    protected List<DominionCard> playArea;

    public DominionGame( List<User> users, List<DominionCard> cards )
    {
        super();
        boolean useShelters = false; // this affects players' starting hands
        boolean useColonies = false; // this affects whether or not colonies and
                                     // platinums are on the table

        this.players = new DominionPlayer[users.size()];
        for ( int i = 0; i < users.size(); i++ )
        {
            this.players[i] = new DominionPlayer( useShelters );
            this.players[i].setUser( users.get( i ) );
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
    public void startGame( List<Game> gameList )
    {
        // Add this game to the GameServer's game list
        gameList.add( this );

        do
        {
            nextTurn();
        } while ( !table.isGameOver() );
        // do any post-game stuff and print out winner, scores, decks, etc.
    }

    @Override
    public void nextTurn()
    {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        coins = 0;
        actions = 1;
        buys = 1;
        boolean doneBuys = false;
        DominionPlayer currentPlayer = players[currentPlayerIndex];

        //TODO: put a 'your turn' prompt in DominionPlayer instead of here, to pass to the client.
        System.out.printf( "-- %s's turn --\n", currentPlayer.getUser().getName() );
        
        while ( actions > 0 && currentPlayer.hasCardTypeInHand( DominionCard.CardType.ACTION ) )
        {
            DominionCard cardToPlay = currentPlayer.promptToChooseOneCard( null,
                    "Choose an action card to play", true );

            if ( cardToPlay == null )
            {
                if( currentPlayer.promptYesNo( "Done playing actions?" ) )
                {
                    break;
                }
            }
            else if ( cardToPlay.getType().contains( DominionCard.CardType.ACTION ) )
            {
                actions--;
                currentPlayer.getHand().remove( cardToPlay );

                // TODO: check for attack reactions

                cardToPlay.onPlay( this );

                if ( !cardToPlay.isTrashed() )
                {
                    // some cards get trashed as an effect of being played.
                    playArea.add( cardToPlay );
                }
            }
            else
            {
                //TODO: put this 'show message' thing in DominionPlayer to pass to the client
                System.out.println( "The chosen card is not an action card." );
            }
        }

        while ( currentPlayer.hasCardTypeInHand( DominionCard.CardType.TREASURE ) )
        {
            DominionCard cardToPlay = currentPlayer.promptToChooseOneCard( null,
                    "Choose a treasure card to play", true );

            // TODO: add a smart 'play all treasures' button that plays all silver/gold/platinum on first click,
            // then everything else on second click
            // maybe think more about what we'd want played on first click and what not to play, i.e. coppers block grand market
            // and you don't want to play counterfeit after laying down $8,
            // or bank while you still have a bunch of treasures in your hand.
            
            if ( cardToPlay == null )
            {
                if( currentPlayer.promptYesNo( "Done playing treasures?" ) )
                {
                    break;
                }
            }
            else if ( cardToPlay.getType().contains( DominionCard.CardType.TREASURE ) )
            {
                currentPlayer.getHand().remove( cardToPlay );
                cardToPlay.onPlay( this );
                
                if ( !cardToPlay.isTrashed() )
                {
                    playArea.add( cardToPlay );
                }
            }
            else
            {
                //TODO: put this in DominionPlayer to pass to client.
                // Maybe get DominionPlayer to do the validation.
                System.out.println( "The chosen card is not a treasure card." );
            }
        }

        while ( buys > 0 && !doneBuys )
        {
            String cardName = currentPlayer.promptToBuy( table );
            if ( cardName == null )
            {
                doneBuys = currentPlayer.promptYesNo( "Done with buys?" );
                // Add confirmation
            }
            else
            {
                buys--;
                // check for on-buy reactions
                DominionCard cardToGain = gainCard( cardName );
                currentPlayer.gainCard( cardToGain );
            }
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

        if ( cardToGain == null )
        {
            // Should never happen.
            // Log an exception and abort game, because whatever allowed this to
            // happen needs fixing.
        }

        // check for on-gain effects
        // Reactions:
        // trader might replace 'cardToGain' with a Silver card and put the
        // other one back in the kingdom
        // Watchtower might redirect it to the player's deck or the trash pile
        // instead.

        // Effects:
        // A lot of Hinterlands cards have "when you gain this..." effects
        // despite not being reactions.
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
     * 
     * @return The DominionPlayer of the player up next.
     */
    public DominionPlayer getNextPlayer()
    {
        return players[(currentPlayerIndex + 1) % players.length];
    }

    /**
     * Gets the opponents, in order.
     * 
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
