package org.gm.cardgame.dominion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.gm.cardgame.Game;
import org.gm.cardgame.User;
import org.gm.cardgame.dominion.cards.DominionCard;
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
    protected int coinDiscount; // from bridge, highway, princess; applies to all cards
    protected int quarriesPlayed; // $2 off per quarry, but only action cards.
    protected List<DominionCard> playArea;
    
    public DominionGame( List<User> users, List<DominionCard> cards )
    {
        super();
        boolean useShelters = false; // this affects players' starting hands
        boolean useColonies = false; // this affects whether or not colonies and
                                     // platinums are on the table
        Random random = new Random();
        float sheltersChance = 0.0f;
        float coloniesChance = 0.0f;
        for( DominionCard card : cards )
        {
            // each card of the appropriate set adds a 10% chance of using that set's extra cards
            if( card.getSet() == DominionCard.CardSet.DARKAGES )
            {
                sheltersChance += 0.1f;
            }
            if( card.getSet() == DominionCard.CardSet.PROSPERITY )
            {
                coloniesChance += 0.1f;
            }
        }
        if( random.nextFloat() <= sheltersChance )
        {
            useShelters = true;
        }
        if( random.nextFloat() <= coloniesChance )
        {
            useColonies = true;
        }
        
        
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
        quarriesPlayed = 0;
        coinDiscount = 0;
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
                DominionCard boughtCard = table.getCardPrototype( cardName );
                
                // On-buy reactions never affect the bought card, but we still need to know what it is in case they depend
                // on something about that card
                if( currentPlayer.hasReactionTypeInHand( ReactionTriggerType.BUY, boughtCard ) )
                {
                    DominionCard reactionToReveal = null;
                    do
                    {
                        List<DominionCard> reactions = currentPlayer.getReactions( ReactionTriggerType.BUY, boughtCard );
                        if( reactions.size() == 0 )
                        {
                            break;
                        }
                        reactionToReveal = currentPlayer.promptToChooseOneCard( reactions, "Choose a reaction to reveal", true );
                        if( reactionToReveal != null )
                        {
                            reactionToReveal.onReveal( this, currentPlayer );
                        }
                    } while( reactionToReveal != null );
                }
                
                // TODO: on-buy effects for non-reaction cards.
                
                currentPlayer.addCardToDiscardPile( takeCardFromSupply( cardName, currentPlayer ) );
            }
        }

        // clean-up phase
        currentPlayer.endTurn( playArea );
        playArea.clear();
    }

    public void playCard( DominionPlayer player, DominionCard cardToPlay )
    {
        player.getHand().remove( cardToPlay );

        if( cardToPlay.getType().contains( DominionCard.CardType.ATTACK ) )
        {
            // The only on-play reactions are opponents reacting to an attack.
            List<DominionPlayer> opponents = getOpponents( player );
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

    // TODO: name this method better?
    /*
     * Take a card from the kingdom and return it Note the returned card may not
     * be the named one, due to reactions If this returns null, no card is
     * gained through the normal channel, though it is possible that a card was still gained
     * through a reaction.
     */
    public DominionCard takeCardFromSupply( String cardName, DominionPlayer owner )
    {
        DominionCard cardToGain = table.takeCard( cardName );

        if ( cardToGain == null )
        {
            // can happen if the supply pile is empty. No further gain effects can happen.
            return null;
        }

        // check for owner pre-gain reactions. These can actually prevent the gain, 
        // and any on-gain effects, from happening. They don't depend on what the gained card is.
        if( owner.hasReactionTypeInHand( ReactionTriggerType.OWNER_PRE_GAIN ) )
        {
            DominionCard originalCard = cardToGain;
            List<DominionCard> reactions = owner.getReactions( ReactionTriggerType.OWNER_PRE_GAIN );
            // The only pre-gain reaction right now is Trader, and it can only apply once to a gained card
            // regardless of how many traders are in hand. At some point we may need to allow for different kinds of
            // pre-gain reactions and loop through this like other reactions, but not for now.
            DominionCard reactionToReveal = owner.promptToChooseOneCard( reactions, "Choose a reaction to reveal", true );
            if( reactionToReveal != null )
            {
                cardToGain = reactionToReveal.onOwnerGainReveal( this, owner, cardToGain );
            }
            
            if( !originalCard.equals( cardToGain ) )
            {
                // The original card is no longer gained. Any on-gain effects for the new card have already happened.
                // just return.
                return cardToGain;
            }
                
        }
        
        // Resolve any non-reaction gain effects. These never affect the gained card.
        cardToGain.onGain( this, owner );
        
        // Check for other-player gain reactions. These never affect the gained card, but can depend on what the gained card is.
        for( DominionPlayer opponent : getOpponents( owner ) )
        {
            if( opponent.hasReactionTypeInHand( ReactionTriggerType.OTHER_PLAYER_GAIN ) )
            {
                List<DominionCard> reactions = opponent.getReactions( ReactionTriggerType.OTHER_PLAYER_GAIN, cardToGain );
                DominionCard reactionToReveal = opponent.promptToChooseOneCard( reactions, "Choose a reaction to reveal", true );
                if( reactionToReveal != null )
                {
                    reactionToReveal.onOpponentGainReveal( this, owner, cardToGain );
                }
            }
        }
        
        // check for owner gain reactions. These only take effect once the gain has already happened and can affect
        // where the gained card goes, so we still need to keep track of cardToGain.
        if( owner.hasReactionTypeInHand( ReactionTriggerType.OWNER_GAIN ) )
        {
            List<DominionCard> reactions = owner.getReactions( ReactionTriggerType.OWNER_GAIN );
            DominionCard reactionToReveal = null;
            do
            { 
                reactionToReveal = owner.promptToChooseOneCard( reactions, "Choose a reaction to reveal", true );
                if( reactionToReveal != null )
                {
                    cardToGain = reactionToReveal.onOwnerGainReveal( this, owner, cardToGain );
                }
            } while( reactionToReveal != null && cardToGain != null );
        }
        
        return cardToGain;
    }
    
    /*
     * Return a card to the supply, through something like Ambassador or Trader.
     * Do not use this for non-supply cards that return themselves when played, like Spoils and Madman.
     */
    public boolean returnCardToSupply( DominionCard card )
    {
        return table.returnCard( card );
    }
    
    /*
     * Put a card in the trash and trigger any onTrash effects it has.
     * Caller must remove the card from if applicable before calling this, as trashing isn't always done from the hand
     * and that isn't addressed here.
     */
    public void trashCard( DominionCard card, DominionPlayer owner )
    {
        card.onTrash( this, owner );
        table.trashCard( card );
    }
    
    /*
     * Get a card's current coin cost
     */
    public int getCurrentCoinCost( DominionCard card )
    {
        int currentCost = card.getCoinCost();
        if( card.getType().contains( DominionCard.CardType.ACTION ) )
        {
            currentCost -= (2 * quarriesPlayed);
        }
        currentCost -= coinDiscount;
        if( currentCost < 0 )
        {
            currentCost = 0;
        }
        return currentCost;
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
    
    public List<DominionCard> getPlayedCards()
    {
        return playArea;
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
     * Gets the opponents, in play order, of the player passed in.
     * @param player The Player to use as a reference point when checking for opponents.
     * @return a List of the other players, in play order.
     */
    public List<DominionPlayer> getOpponents( DominionPlayer player )
    {
        ArrayList<DominionPlayer> opponents = new ArrayList<DominionPlayer>();
        int basePlayerIndex = -1;
        for ( int i = 0; i < players.length; i++ )
        {
            if( players[i].equals(player) )
            {
                basePlayerIndex = i;
                break;
            }
        }
        
        for( int i = 0; i < players.length - 1; i++ )
        {
            opponents.add( players[(basePlayerIndex + 1 + i) % players.length] );
        }
        return opponents;
    }
}
