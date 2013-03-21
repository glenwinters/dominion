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

    public DominionPlayer( boolean useShelters )
    {
        deck = new Deck( useShelters );
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

    // will also need discard / trash prompts that restrict by type, since some
    // cards require that
    public List<DominionCard> promptToDiscard( DominionCard.CardType type, int min, int max,
            boolean optional )
    {
        return promptToChooseMultipleCards( type, min, max, "Choose cards to discard", optional );
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
            if ( choice == null )
            {
                choice = new String( "" );
            }

        } while ( !choice.equalsIgnoreCase( "y" ) && !choice.equalsIgnoreCase( "n" ) );

        if ( choice.equalsIgnoreCase( "y" ) )
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public List<DominionCard> promptToTrash( DominionCard.CardType type, int min, int max,
            boolean optional )
    {
        return promptToChooseMultipleCards( type, min, max, "Choose cards to trash", optional );
    }

    /*
     * Prompt user to choose one card from their hand matching the given type.
     * This can be used for various purposes, i.e. return a card to deck, trash
     * a card, discard a card, reveal a card, so it's up to the caller to decide
     * what to do with all that.
     */
    public DominionCard promptToChooseOneCard( DominionCard.CardType type, String prompt,
            boolean optional )
    {
        // go through hand, build a list of cards that match the specified type.
        // or if type == null, just list the whole hand.
        // if list size > 0, prompt user to pick one and return it.

        // Test terminal code
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String input = null;

        // Print prompt
        System.out.println( prompt );

        // Print possible cards
        List<DominionCard> possibleCards = new LinkedList<DominionCard>();

        // Build list of possible cards to choose by type
        for ( DominionCard card : hand )
        {
            if ( type == null || card.getType().contains( type ) )
            {
                possibleCards.add( card );
            }
        }

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
            if ( optional )
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

            if ( input != null && !input.equals( "" ) )
            {
                choice = Integer.parseInt( input );
                if ( choice > 0 && choice < possibleCards.size() + 1 )
                {
                    card = possibleCards.get( choice - 1 );
                }
                else if ( optional && choice == possibleCards.size() + 1 )
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

    public List<DominionCard> promptToChooseMultipleCards( DominionCard.CardType type, int min,
            int max, String prompt, boolean optional )
    {
        // go through hand, build a list of cards that match the specified type.
        // or if type == null, just list the whole hand.
        // if list size > 0, prompt user to pick as many as they want and return
        // the list of chosen cards.
        return null;
    }

    public DominionCard promptToReact( DominionCard.ReactionTriggerType trigger )
    {
        // go through hand, build a list of reaction cards that match the
        // trigger type.
        // if list size > 0, prompt user to pick one and return it.
        // MOAT MOAT MOAT MAOT
        return null;
    }

    public String promptToBuy()
    {
        // prompt player to buy a card. return name of card to buy, or null if
        // none
        return null;
    }

    // this will need restriction by type as well
    public DominionCard promptToGain( boolean optional, int coinCostMin, int coinCostMax ) // might
                                                                                           // also
                                                                                           // need
                                                                                           // potion
                                                                                           // limit
    {
        // prompt to gain a card for some reason other than buying.
        // even if optional is true, this could return null if there are no
        // eligible cards
        return null;
    }

    // hand manipulation methods
    public void drawCards( int numCards )
    {
        hand.addAll( deck.drawCards( numCards ) );
    }

    public void gainCard( DominionCard cardToGain )
    {
        if ( cardToGain != null )
        {
            deck.discardCard( cardToGain );
        }
    }

    public void discardCard( DominionCard cardToDiscard )
    {
        // A simple for-loop needed to be converted into a while with an
        // iterator because modifying a Collection is not normally allowed while
        // iterating over it. To solve this problem, an iterator is used and
        // iterator.remove() is used to remove the current item the iterator is
        // pointing to. This strategy avoids the ConcurrentModificationException
        ListIterator<DominionCard> it = hand.listIterator();
        while ( it.hasNext() )
        {
            DominionCard card = it.next();
            if ( cardToDiscard.equals( card ) )
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
        // A copy must be used to iterate over the hand since it will be
        // modified during the iteration
        List<DominionCard> handCopy = new ArrayList<DominionCard>( hand );
        for ( DominionCard card : handCopy )
        {
            discardCard( card );
        }
    }

    public void placeCardOnDeck( DominionCard card )
    {
        deck.placeCard( card );
    }

    public void discardDeck()
    {
        deck.discardDrawPile();
    }

    public void trashCard( DominionCard cardToTrash )
    {
        for ( DominionCard card : hand )
        {
            if ( cardToTrash.equals( card ) )
            {
                // remove card from hand and don't place in discard
                // DominionGame handles putting it in the trash?
            }
        }
    }

    // not convinced this is right.
    public void cleanUpPlayedCards( List<DominionCard> playedCards )
    {
        deck.discardCards( playedCards );
    }
}
