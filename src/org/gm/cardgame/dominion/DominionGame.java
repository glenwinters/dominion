package org.gm.cardgame.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.gm.cardgame.Game;
import org.gm.cardgame.User;
import org.gm.cardgame.dominion.cards.DominionCard;
import org.gm.cardgame.dominion.cards.DominionCard.CardType;
import org.gm.cardgame.dominion.cards.DominionCard.ReactionTriggerType;

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
    
    protected HashMap<DominionPlayer, Boolean> moatPlayed;
    
    // a lot of Seaside cards are in effect across turns, but this may not be the best way to keep track of them.
    // According to DXV, When a duration card is TRed/KCed/Processioned, the modifier card stays out too so we need
    // somewhere to put it.
    protected HashMap<DominionPlayer, List<DominionCard>> durationCards;

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
        moatPlayed = new HashMap<DominionPlayer, Boolean>();
        durationCards = new HashMap<DominionPlayer, List<DominionCard>>();
        for( DominionPlayer player : players )
        {
            moatPlayed.put( player, false );
            durationCards.put( player, new LinkedList<DominionCard>() );
        }
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

        // TODO: put a 'your turn' prompt in DominionPlayer instead of here, to
        // pass to the client.
        System.out.printf( "-- %s's turn --\n", currentPlayer.getUser().getName() );

        while ( actions > 0 && currentPlayer.hasCardTypeInHand( DominionCard.CardType.ACTION ) )
        {
            DominionCard cardToPlay = currentPlayer.promptToChooseOneCard( currentPlayer.getHand(), "Choose an action card to play", true );

            if ( cardToPlay == null )
            {
                if ( currentPlayer.promptYesNo( "Done playing actions?" ) )
                {
                    break;
                }
            }
            else if ( cardToPlay.getType().contains( DominionCard.CardType.ACTION ) )
            {
                actions--;
                playCard( currentPlayer, cardToPlay );
            }
            else
            {
                // TODO: put this 'show message' thing in DominionPlayer to pass
                // to the client
                System.out.println( "The chosen card is not an action card." );
            }
        }

        while ( currentPlayer.hasCardTypeInHand( DominionCard.CardType.TREASURE ) )
        {
            DominionCard cardToPlay = currentPlayer
                    .promptToChooseOneCard( currentPlayer.getHand(), "Choose a treasure card to play", true );

            // TODO: add a smart 'play all treasures' button that plays all
            // silver/gold/platinum on first click,
            // then everything else on second click
            // maybe think more about what we'd want played on first click and
            // what not to play, i.e. coppers block grand market
            // and you don't want to play contraband after laying down $8,
            // or bank while you still have a bunch of treasures in your hand.

            if ( cardToPlay == null )
            {
                if ( currentPlayer.promptYesNo( "Done playing treasures?" ) )
                {
                    break;
                }
            }
            else if ( cardToPlay.getType().contains( DominionCard.CardType.TREASURE ) )
            {
                playCard( currentPlayer, cardToPlay );
            }
            else
            {
                // TODO: put this in DominionPlayer to pass to client.
                // Maybe get DominionPlayer to do the validation.
                System.out.println( "The chosen card is not a treasure card." );
            }
        }

        while ( buys > 0 && !doneBuys )
        {
            String cardName = currentPlayer.promptToBuy( table, coins, potions );
            if ( cardName == null )
            {
                doneBuys = currentPlayer.promptYesNo( "Done with buys?" );
            }
            else
            {
                buys--;
                coins -= table.getCardCurrentCoinCost( cardName );
                potions -= table.getCardCurrentPotionCost( cardName );
                // TODO: check for on-buy reactions, which happen whether or not the card is actually gained
                DominionCard cardToGain = takeCardFromSupply( cardName );
                if( cardToGain != null )
                {
                    // some effects can prevent card gain, so we need to make sure something is actually gained.
                    currentPlayer.gainCard( cardToGain );
                }
            }
        }

        // clean-up phase
        currentPlayer.discardHand();
        currentPlayer.takePlayedCards( playArea );
        playArea.clear();
        currentPlayer.drawCards( 5 );
        for( DominionPlayer player : players )
        {
            moatPlayed.put( player, false );
        }
    }

    public void playCard( DominionPlayer currentPlayer, DominionCard cardToPlay )
    {
        currentPlayer.getHand().remove( cardToPlay );

        if( cardToPlay.getType().contains( DominionCard.CardType.ATTACK ) )
        {
            // The only on-play reactions are opponents reacting to an attack.
            List<DominionPlayer> opponents = getOpponents();
            for( DominionPlayer opponent : opponents )
            {
                if( opponent.hasReactionTypeInHand( DominionCard.ReactionTriggerType.ATTACK ) )
                {
                    DominionCard reaction;
                    do
                    {
                        List<DominionCard> reactions = opponent.getReactions( DominionCard.ReactionTriggerType.ATTACK );
                        if( reactions.size() == 0 )
                        {
                            // reactions can modify the hand, so we need to check each time.
                            break;
                        }
                        reaction = opponent.promptToChooseOneCard( reactions, "Choose a reaction", true );
                        if( reaction != null )
                        {
                            reaction.onReveal( this, opponent );
                        }
                    } while( reaction != null );
                }
            }
        }
        
        cardToPlay.onPlay( this );

        if ( !cardToPlay.isTrashed() )
        {
            // some cards get trashed as an effect of being played.
            playArea.add( cardToPlay );
        }
    }

    // there may be a better way to do this
    /*
     * Take a card from the kingdom and return it Note the returned card may not
     * be the named one, due to reactions If this returns null, no card is
     * gained.
     */
    public DominionCard takeCardFromSupply( String cardName )
    {
        DominionCard cardToTake = table.takeCard( cardName );

        if ( cardToTake == null )
        {
            // Should never happen.
            // Log an exception and abort game, because whatever allowed this to
            // happen needs fixing.
        }

        // TODO: check for on-gain effects
        // Reactions:
        // trader might replace 'cardToGain' with a Silver card and put the
        // other one back in the kingdom
        // Watchtower might redirect it to the player's deck or the trash pile
        // instead.

        // Effects:
        // A lot of Hinterlands cards have "when you gain this..." effects
        // despite not being reactions.
        return cardToTake;
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
    
    public boolean isVulnerableToAttack( DominionPlayer player )
    {
        return !moatPlayed.get( player ); // && no Lightouse in play. i can't remember if there are any more defense cards.
    }
    
    public void setMoatPlayed( DominionPlayer player, boolean played )
    {
        moatPlayed.put( player, played );
    }
}
