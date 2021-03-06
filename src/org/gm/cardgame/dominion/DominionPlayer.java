package org.gm.cardgame.dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.gm.cardgame.Player;
import org.gm.cardgame.dominion.cards.DominionCard;

public class DominionPlayer extends Player
{
    private Deck deck;
    private List<DominionCard> hand;
    private List<DominionCard> oldDurationCards; // cards played during the
                                                 // player's last turn
    private List<DominionCard> newDurationCards; // cards played during this
                                                 // player's turn in progress
    private boolean moatPlayed;

    public DominionPlayer( boolean useShelters )
    {
        oldDurationCards = new LinkedList<DominionCard>();
        newDurationCards = new LinkedList<DominionCard>();
        deck = new Deck( useShelters );
        hand = new ArrayList<DominionCard>();
        drawCards( 5 );
    }

    public DominionPlayer( List<DominionCard> cardList )
    {
        oldDurationCards = new LinkedList<DominionCard>();
        newDurationCards = new LinkedList<DominionCard>();
        deck = new Deck( cardList );
        hand = new ArrayList<DominionCard>();
        drawCards( 5 );
    }

    public List<DominionCard> getHand()
    {
        return hand;
    }

    // prompt methods
    public DominionCard promptToPlay()
    {
        return promptToChooseOneCard( null, "Choose a card to play", false );
    }

    public boolean promptYesNo( String prompt )
    {
        // Generic prompt to ask the player a yes/no question.
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        // Print prompt
        System.out.println( prompt + " (y/n)" );

        String choice = new String( "" );
        do
        {
            System.out.print( ">" );
            try
            {
                choice = br.readLine();
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if( choice == null )
            {
                choice = new String( "" );
            }

        } while ( !choice.equalsIgnoreCase( "y" ) && !choice.equalsIgnoreCase( "n" ) );

        if( choice.equalsIgnoreCase( "y" ) )
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    /**
     * 
     * @param choices
     *            A list of choices to display to the user
     * @param optional
     *            true if a choice does not have to be made.
     * @return the index of the chosen option, or -1 if none were chosen.
     */
    public int promptMultipleChoice( List<String> choices, boolean optional )
    {
        boolean done = false;
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        // TODO: pass this to the client.

        String input = new String( "" );
        int choice = -1;
        do
        {
            for ( int i = 0; i < choices.size(); i++ )
            {
                System.out.printf( "%s) %s\n", i + 1, choices.get( i ) );
            }
            if( optional )
            {
                System.out.println( "0) None" );
            }

            System.out.print( "> " );

            try
            {
                input = br.readLine();
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if( input != null && !input.equals( "" ) )
            {
                choice = Integer.parseInt( input );
                if( choice > 0 && choice < choices.size() + 1 )
                {
                    done = true;
                }
                else if( optional && choice == 0 )
                {
                    done = true;
                }
                else
                {
                    System.out.println( "That is not a possible choice." );
                }
            }
        } while ( !done );
        return choice - 1;
    }

    /*
     * Prompt user to choose one card from their hand matching the given type. This can be used for various purposes, i.e. return a card to
     * deck, trash a card, discard a card, reveal a card, so it's up to the caller to decide what to do with all that.
     */
    public DominionCard promptToChooseOneCard( List<DominionCard> possibleCards, String prompt, boolean optional )
    {
        // TODO: pass this call to client.

        // Test terminal code
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String input = null;

        if( possibleCards.size() == 0 )
        {
            return null;
        }

        // Print prompt
        System.out.println( prompt );

        // Get user's card choice
        int i = 0;
        int choice = -1;
        DominionCard card = null;
        do
        {
            for ( i = 0; i < possibleCards.size(); i++ )
            {
                System.out.printf( "%d) %s\n", i + 1, possibleCards.get( i ).getName() );
            }
            if( optional )
            {
                i++;
                System.out.printf( "%d) Done\n", i );
            }
            System.out.print( "> " );

            try
            {
                input = br.readLine();
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if( input != null && !input.equals( "" ) )
            {
                choice = Integer.parseInt( input );
                if( choice > 0 && choice < possibleCards.size() + 1 )
                {
                    card = possibleCards.get( choice - 1 );
                }
                else if( optional && choice == possibleCards.size() + 1 )
                {
                    // Done choosing cards for this phase
                    break;
                }
                else
                {
                    System.out.println( "That is not a possible choice." );
                }
            }
        } while ( card == null );

        return card;
    }

    public List<DominionCard> promptToChooseMultipleCards( List<DominionCard> cards, int min, int max, String prompt, boolean optional )
    {
        // TODO: pass call to client and let the client handle it.

        // for now,
        // go through hand, build a list of cards that match the specified type.
        // or if type == null, just list the whole hand.
        // if list size > 0, prompt user to pick as many as they want and return
        // the list of chosen cards.

        LinkedList<DominionCard> chosenCards = new LinkedList<DominionCard>();
        DominionCard cardPicked;
        do
        {
            cardPicked = promptToChooseOneCard( cards, prompt, optional );
            if( cardPicked != null )
            {
                chosenCards.add( cardPicked );
            }
        } while ( cardPicked != null );

        return chosenCards;
    }

    // TODO: Might want to pass list of cards to this, already adjusted for
    // discount, rather than the entire Table.
    public String promptToBuy( DominionTable table, int maxCoins, int maxPotions )
    {
        // TODO: put all this UI logic in the client.

        // prompt player to buy a card. return name of card to buy, or null if
        // none

        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String input = null;

        // Build list of possible cards to choose by type
        int i = 0;
        LinkedList<KingdomPile> allPiles = new LinkedList<KingdomPile>( table.getSupply() );
        LinkedList<KingdomPile> piles = new LinkedList<KingdomPile>();
        for ( KingdomPile pile : allPiles )
        {
            if( pile.getCoinCost() <= maxCoins && pile.getPotionCost() <= maxPotions )
            {
                piles.add( pile );
            }
        }

        // TODO Look into using Comparable<DominionCard> to sort by coin cost
        for ( i = 0; i < piles.size(); i++ )
        {
            DominionCard card = piles.get( i ).getCardPrototype();
            int cardsRemaining = piles.get( i ).getCardsRemaining();
            System.out.printf( "%02d) %-20s $%-2d %d P (%-2d left)\n", i + 1, card.getName(), card.getCoinCost(), card.getPotionCost(),
                    cardsRemaining );
        }
        i++;
        System.out.printf( "%02d) Done\n", i );
        System.out.print( "> " );

        // Get user's card choice
        int choice = -1;
        DominionCard card = null;
        do
        {
            try
            {
                input = br.readLine();
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if( input != null && !input.equals( "" ) )
            {
                choice = Integer.parseInt( input );
                if( choice > 0 && choice < piles.size() + 1 )
                {
                    card = piles.get( choice - 1 ).getCardPrototype();
                }
                else if( choice == piles.size() + 1 )
                {
                    // Done choosing cards for this phase
                    break;
                }
                else
                {
                    System.out.println( "That is not a possible choice." );
                }
            }
        } while ( card == null );

        if( card == null )
        {
            return null;
        }
        else
        {
            return card.getName();
        }
    }

    // this will need restriction by type as well
    public DominionCard promptToGain( boolean optional, int coinCostMin, int coinCostMax ) // might
                                                                                           // also
                                                                                           // need
                                                                                           // potion
                                                                                           // limit
    {
        // TODO: pass call to client.

        // prompt to gain a card for some reason other than buying.
        // even if optional is true, this could return null if there are no
        // eligible cards
        return null;
    }

    // hand-related methods
    /**
     * Check whether the player has a card of a certain type in hand.
     * 
     * @param type
     *            The card type to check for
     * @return <b>true</b> if the player has a card of that type in hand, <b>false</b> if not
     */
    public boolean hasCardTypeInHand( DominionCard.CardType type )
    {
        for ( DominionCard card : hand )
        {
            if( card.getType().contains( type ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all cards in the player's hand that are of the specified type.
     * 
     * @param type
     *            The card type to check for
     * @return A List of cards matching that card type.
     */
    public List<DominionCard> getCardsByType( DominionCard.CardType type )
    {
        LinkedList<DominionCard> cards = new LinkedList<DominionCard>();
        for ( DominionCard card : hand )
        {
            if( card.getType().contains( type ) )
            {
                cards.add( card );
            }
        }
        return cards;
    }

    /**
     * Check whether the player has an applicable reaction card in hand.
     * 
     * @param trigger
     *            The reaction trigger type
     * @return <b>true</b> if the player has an applicable reaction card in hand, <b>false</b> if not.
     */
    public boolean hasReactionTypeInHand( DominionCard.ReactionTriggerType trigger )
    {
        for ( DominionCard card : hand )
        {
            if( card.canReact( trigger ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the player has an applicable reaction card in hand.
     * 
     * @param trigger
     *            The reaction trigger type
     * @return <b>true</b> if the player has an applicable reaction card in hand, <b>false</b> if not.
     */
    public boolean hasReactionTypeInHand( DominionCard.ReactionTriggerType trigger, DominionCard card )
    {
        for ( DominionCard handCard : hand )
        {
            if( handCard.canReact( trigger, card ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all non-card-specific reactions in hand matching a trigger type.
     * 
     * @param type
     *            The trigger type to check for
     * @return A List of the cards matching that trigger type.
     */
    public List<DominionCard> getReactions( DominionCard.ReactionTriggerType type )
    {
        LinkedList<DominionCard> reactions = new LinkedList<DominionCard>();
        for ( DominionCard card : hand )
        {
            if( card.canReact( type ) )
            {
                reactions.add( card );
            }
        }
        return reactions;
    }

    /**
     * Get all card-specific reactions in hand matching a trigger type.
     * 
     * @param type
     *            The trigger type to check for.
     * @param card
     *            The card related to the action
     * @return A List of cards matching that trigger type and card.
     */
    public List<DominionCard> getReactions( DominionCard.ReactionTriggerType type, DominionCard card )
    {
        LinkedList<DominionCard> reactions = new LinkedList<DominionCard>();
        for ( DominionCard handCard : hand )
        {
            if( handCard.canReact( type, card ) )
            {
                reactions.add( handCard );
            }
        }
        return reactions;
    }

    /**
     * Draw a certain number of cards from the deck into the player's hand.
     * 
     * @param numCards
     *            The number of cards to draw
     * @return A list of the cards drawn. May be fewer than the number requested if there aren't enough in the deck to meet the request.
     */
    public List<DominionCard> drawCards( int numCards )
    {
        List<DominionCard> drawnCards = deck.drawCards( numCards );
        hand.addAll( drawnCards );
        return drawnCards;
    }

    /**
     * Same as above, but don't put the cards in hand (for revealing, setting aside and such). The caller needs to maintain a proper
     * location for these cards as they are removed from the deck.
     * 
     * @param numCards
     *            The number of cards to take
     * @return A list of the cards taken from the deck. May be fewer than the number requested if there aren't enough in the deck to meet
     *         the request.
     */
    public List<DominionCard> takeCardsFromDeck( int numCards )
    {
        List<DominionCard> drawnCards = deck.drawCards( numCards );
        return drawnCards;
    }

    /**
     * Add a card to the player's discard pile from an external source. Can be used to gain cards.
     * 
     * @param cardToAdd
     *            The card to add to the pile. If cardToAdd is null, no action is performed.
     */
    public void addCardToDiscardPile( DominionCard cardToAdd )
    {
        if( cardToAdd != null )
        {
            deck.discardCard( cardToAdd );
        }
    }

    /**
     * Add a list of cards to the player's discard pile from an external source.
     * 
     * @param cardsToAdd
     *            The list of cards to add to the pile.
     */
    public void addCardsToDiscardPile( List<DominionCard> cardsToAdd )
    {
        deck.discardCards( cardsToAdd );
    }

    /**
     * Discard a card from the player's hand
     * 
     * @param cardToDiscard
     *            The specific card to discard
     */
    public void discardCard( DominionCard cardToDiscard )
    {
        ListIterator<DominionCard> it = hand.listIterator();
        while ( it.hasNext() )
        {
            DominionCard card = it.next();
            if( cardToDiscard.equals( card ) )
            {
                // Remove the current card
                it.remove();
                deck.discardCard( card );
                break;
            }
        }
    }

    public void discardHand()
    {
        List<DominionCard> handCopy = new ArrayList<DominionCard>( hand );
        for ( DominionCard card : handCopy )
        {
            discardCard( card );
        }
    }

    /**
     * Place a card on top of the player's draw pile. If the card was not already in the player's possession, this is a gain and the caller
     * needs to handle that.
     * 
     * @param card
     *            The card to place on the deck.
     */
    public void placeCardOnDeck( DominionCard card )
    {
        deck.placeCard( card );
    }

    /**
     * Put the player's entire draw pile into their discard.
     */
    public void discardDeck()
    {
        deck.discardDrawPile();
    }

    /**
     * Remove a card from the hand without putting it in the discard. The caller must handle putting the card in the right place, because
     * the Player loses the card altogether at this point.
     * 
     * @param cardToRemove
     *            The card to remove from the player's hand.
     */
    public void removeCardFromHand( DominionCard cardToRemove )
    {
        ListIterator<DominionCard> it = hand.listIterator();
        while ( it.hasNext() )
        {
            DominionCard card = it.next();
            if( cardToRemove.equals( card ) )
            {
                it.remove();
                break;
            }
        }
    }

    /**
     * Put a card in the hand from some external source, e.g. mine, masquerade, native village mat, or torturer. If this card wasn't
     * originally set aside from a player's deck, this is gaining a card, so the caller needs to check for gain reactions if appropriate.
     * 
     * @param cardToAdd
     *            The card to add to the player's hand
     */
    public void addCardTohand( DominionCard cardToAdd )
    {
        hand.add( cardToAdd );
    }

    /**
     * Put a list of cards in the hand from some external source
     * 
     * @param cardsToAdd
     *            The card list to add to the player's hand
     */
    public void addCardsToHand( List<DominionCard> cardsToAdd )
    {
        hand.addAll( cardsToAdd );
    }

    /**
     * Perform any turn-end maintenance.
     */
    public void endTurn( List<DominionCard> playedCards )
    {
        addCardsToDiscardPile( playedCards ); // TODO: duration cards played
                                              // this turn don't go into discard
        addCardsToDiscardPile( oldDurationCards );
        discardHand();
        drawCards( 5 );
        oldDurationCards = newDurationCards;
        newDurationCards = new LinkedList<DominionCard>();
        moatPlayed = false;
    }

    public void moatRevealed()
    {
        moatPlayed = true;
    }

    public boolean isVulnerableToAttack()
    {
        for ( DominionCard card : oldDurationCards )
        {
            // if( card instanceof
            // org.gm.cardgame.dominion.cards.seaside.LighthouseCard )
            // {
            // return false;
            // }
        }
        return !moatPlayed;
    }

    public int getVictoryPoints()
    {
        this.discardHand();
        return deck.getVictoryPoints();
    }
}
