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

    /*
     * Prompt user to choose one card from their hand matching the given type.
     * This can be used for various purposes, i.e. return a card to deck, trash
     * a card, discard a card, reveal a card, so it's up to the caller to decide
     * what to do with all that.
     */
    public DominionCard promptToChooseOneCard( DominionCard.CardType type, String prompt, boolean optional )
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

        if( possibleCards.size() == 0 )
        {
            return null;
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

    public List<DominionCard> promptToChooseMultipleCards( DominionCard.CardType type, int min, int max, String prompt,
            boolean optional )
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
            cardPicked = promptToChooseOneCard( type, prompt, optional );
            if( cardPicked != null )
            {
                chosenCards.add( cardPicked );
            }
        } while( cardPicked != null );
        
        return chosenCards;
    }

    public DominionCard promptToReact( DominionCard.ReactionTriggerType trigger )
    {
        // go through hand, build a list of reaction cards that match the
        // trigger type.
        // if list size > 0, prompt user to pick one and return it.
        // MOAT MOAT MOAT MAOT
        return null;
    }

    public String promptToBuy( DominionTable table, int maxCoins, int maxPotions )
    {
        //TODO: put all this UI logic in the client.
        
        // prompt player to buy a card. return name of card to buy, or null if
        // none

        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String input = null;

        // Build list of possible cards to choose by type
        int i = 0;
        LinkedList<KingdomPile> allPiles = new LinkedList<KingdomPile>( table.getSupply() );
        LinkedList<KingdomPile> piles = new LinkedList<KingdomPile>();
        for( KingdomPile pile : allPiles )
        {
            if( pile.getCoinCost() <= maxCoins && pile.getPotionCost() <= maxPotions )
            {
                piles.add( pile );
            }
        }

        // TODO Look into using Comparable<DominionCard> to sort by coin cost
        for ( i = 0; i < piles.size(); i++ )
        {
            System.out.printf( "%02d) %-20s $%-2d %d P (%-2d left)\n", i + 1, piles.get( i ).getCard().getName(), piles
                    .get( i ).getCard().getCoinCost(), piles.get( i ).getCard().getPotionCost(), piles.get( i )
                    .getCardsRemaining() );
        }
        i++;
        System.out.printf( "%d) Done\n", i );
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

            if ( input != null && !input.equals( "" ) )
            {
                choice = Integer.parseInt( input );
                if ( choice > 0 && choice < piles.size() + 1 )
                {
                    card = piles.get( choice - 1 ).getCard();
                }
                else if ( choice == piles.size() + 1 )
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

        if ( card == null )
        {
            return null;
        }
        else
        {
            return card.getName();
        }
    }

    // this will need restriction by type as well
    public DominionCard promptToGain( boolean optional, int coinCostMin, int coinCostMax ) // might also need potion limit
    {
        // TODO: pass call to client.
        
        // prompt to gain a card for some reason other than buying.
        // even if optional is true, this could return null if there are no
        // eligible cards
        return null;
    }

    // hand-related methods
    public boolean hasCardTypeInHand( DominionCard.CardType type )
    {
        for ( DominionCard card : hand )
        {
            if ( card.getType().contains( type ) )
            {
                return true;
            }
        }
        return false;
    }

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

    /**
     * Remove a card from the hand without putting it in the discard.
     * The caller must handle putting the card in the right place, because the Player loses the card altogether at this point.
     * @param cardToRemove The card to remove.
     */
    public void removeCardFromHand( DominionCard cardToRemove )
    {
        ListIterator<DominionCard> it = hand.listIterator();
        while ( it.hasNext() )
        {
            DominionCard card = it.next();
            if ( cardToRemove.equals( card ) )
            {
                it.remove();
                break;
            }
        }
    }
    
    /**
     * Put a card in the hand from some external source, e.g. mine, masquerade, native village mat, or torturer.
     * If this card wasn't originally set aside from a player's deck, this is gaining a card, so the caller needs
     * to watch for gain reactions.
     * @param cardToAdd
     */
    public void addCardTohand( DominionCard cardToAdd )
    {
        hand.add( cardToAdd );
    }

    public void takePlayedCards( List<DominionCard> playedCards )
    {
        deck.discardCards( playedCards );
    }
}
