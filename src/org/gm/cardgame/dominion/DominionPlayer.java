package org.gm.cardgame.dominion;

import java.util.List;

import org.gm.cardgame.dominion.cards.DominionCard;

public class DominionPlayer
{
    private List<DominionCard> hand;
    
    //need constructor
    
    // prompt methods
    public DominionCard promptToPlay()
    {
        // prompt to select a card to play.
        // returns the card selected, or null if none.
        // this may change depending on how processing flow evolves.
        return null;
    }

    // will also need discard / trash prompts that restrict by type, since some cards require that
    public List<DominionCard> promptToDiscard( int min, int max, boolean optional )
    {
        // if max == 0, no limit besides hand size.
        
        // return the discarded cards, or null if none.
        return null;
    }
    
    public boolean promptToDiscardDeck()
    {
        //ask player if they want to discard their whole deck. this is always optional.
        return promptYesNo( "Place your deck in the discard pile?" );
    }
    
    public boolean promptYesNo( String prompt )
    {
        //Generic prompt to ask the player a yes/no question.
        return false;
    }

    public List<DominionCard> promptToTrash( int min, int max, boolean optional )
    {
        // prompt to trash a card from hand. if optional == true, include 'none' option.
        
        // return the trashed card, or null if none.
        return null;
    }

    public DominionCard promptToReact()
    {
        // prompt to react. Need to put a bit more thought into this to determine what cards are valid reactions
        // at what points, etc.
        // MOAT MOAT MOAT MOAT
        return null;
    }
    
    public DominionCard promptToBuy()
    {
        // prompt player to buy a card. return bought card, or null if none.
        return null;
    }
    
    // this will need restriction by type as well
    public DominionCard promptToGain( boolean optional, int coinCostMin, int coinCostMax ) //might also need potion limit
    {
        // prompt to gain a card for some reason other than buying.
        // even if optional is true, this could return null if there are no eligible cards
        return null;
    }
    
    
    // hand manipulation methods
    public void drawCard()
    {
        //if deck size == 0
        // shuffle discard pile into deck

        // take top card from deck into hand
    }

    public void drawCards( int numCards )
    {
        for ( int i = 0; i < numCards; i++ )
        {
            drawCard();
        }
    }

    public void gainCard( DominionCard cardToGain )
    {
        //add card to discard pile
    }
    
    public void discardCard( DominionCard cardToDiscard )
    {
        for( DominionCard card : hand )
        {
            if( cardToDiscard.equals( card ) )
            {
                //remove card from hand
                //add card to discard pile
            }
        }
    }
    
    // this is for discarding your hand during play, i.e. as a result of minion or tactician,
    // not for cleaning up at the end of a turn.
    public void discardHand()
    {
        for(DominionCard card : hand )
        {
            discardCard( card );
        }
    }
    
    public void discardDeck()
    {
        //discardPile.addAll(deck)
        //deck.clear();
    }
    
    public void trashCard( DominionCard cardToTrash )
    {
        for( DominionCard card : hand )
        {
            if( cardToTrash.equals( card ) )
            {
                //remove card from hand and don't place in discard
            }
        }
    }
}
