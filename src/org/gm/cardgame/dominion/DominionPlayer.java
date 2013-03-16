package org.gm.cardgame.dominion;

import org.gm.cardgame.dominion.cards.DominionCard;

public class DominionPlayer
{
    public void takeTurn()
    {
    
    }
    
 // prompt methods
    public DominionCard promptToPlay()
    {
        // prompt to select a card to play.
        // returns the card selected, or null if none.
        // this may change depending on how processing flow evolves.
        return null;
    }

    public DominionCard promptToDiscard( boolean optional )
    {
        // prompt to discard a card from hand. if optional == true, include 'none' option.
        
        // return the discarded card, or null if none.
        return null;
    }

    public DominionCard promptToTrash( boolean optional )
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
    
    public DominionCard promptToGain()
    {
        // prompt to gain a card for some reason other than buying. might not end up using this.
        return null;
    }
}
