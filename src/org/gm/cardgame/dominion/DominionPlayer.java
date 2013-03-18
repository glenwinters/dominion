package org.gm.cardgame.dominion;

import java.util.List;

import org.gm.cardgame.dominion.cards.DominionCard;

public class DominionPlayer
{
    private Deck deck;
    private List<DominionCard> hand;
    
    //TODO need constructor
    
    public List<DominionCard> getHand()
    {
        return hand;
    }
    
    // prompt methods
    public DominionCard promptToPlay()
    {
        return promptToChooseOneCard( null, "Choose a card to play", false);
    }

    // will also need discard / trash prompts that restrict by type, since some cards require that
    public List<DominionCard> promptToDiscard( DominionCard.CardType type, int min, int max, boolean optional )
    {
        return promptToChooseMultipleCards( type, min, max, "Choose cards to discard", optional );
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

    public List<DominionCard> promptToTrash( DominionCard.CardType type, int min, int max, boolean optional )
    {
        return promptToChooseMultipleCards( type, min, max, "Choose cards to trash", optional);
    }

    /*
     * Prompt user to choose one card from their hand matching the given type.
     * This can be used for various purposes, i.e. return a card to deck, trash a card, discard a card,
     * reveal a card, so it's up to the caller to decide what to do with all that.
     */
    public DominionCard promptToChooseOneCard( DominionCard.CardType type, String prompt, boolean optional )
    {
        // go through hand, build a list of cards that match the specified type.
        // or if type == null, just list the whole hand.
        // if list size > 0, prompt user to pick one and return it.
        return null;
    }
    
    public List<DominionCard> promptToChooseMultipleCards( DominionCard.CardType type, int min, int max, String prompt, boolean optional )
    {
        // go through hand, build a list of cards that match the specified type.
        // or if type == null, just list the whole hand.
        // if list size > 0, prompt user to pick as many as they want and return the list of chosen cards.
        return null;
    }
    
    public DominionCard promptToReact( DominionCard.ReactionTriggerType trigger )
    {
        //go through hand, build a list of reaction cards that match the trigger type.
        // if list size > 0, prompt user to pick one and return it.
        // MOAT MOAT MOAT MAOT
        return null;
    }
    
    public String promptToBuy()
    {
        // prompt player to buy a card. return name of card to buy, or null if none
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
    public void drawCards( int numCards )
    {
        hand.addAll( deck.drawCards( numCards ) );
    }

    public void gainCard( DominionCard cardToGain )
    {
        if( cardToGain != null )
        {
            deck.discardCard( cardToGain );
        }
    }
    
    public void discardCard( DominionCard cardToDiscard )
    {
        for( DominionCard card : hand )
        {
            if( cardToDiscard.equals( card ) )
            {
                hand.remove( card );
                deck.discardCard( card );
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
        for( DominionCard card : hand )
        {
            if( cardToTrash.equals( card ) )
            {
                //remove card from hand and don't place in discard
                //DominionGame handles putting it in the trash?
            }
        }
    }
}
