package org.gm.cardgame.dominion;

import java.util.List;
import java.util.LinkedList;

import org.gm.cardgame.dominion.cards.DominionCard;

public class Deck
{

    private LinkedList<DominionCard> drawPile;
    private LinkedList<DominionCard> discardPile;
    
    public Deck()
    {
        drawPile = new LinkedList<DominionCard>();
        discardPile = new LinkedList<DominionCard>();
    }
    
    public void shuffleDrawPile()
    {
        // TODO
        // Just shuffle the draw pile. All cards that are supposed to be shuffled in are
        // already there.
    }
    
    public void discardDrawPile()
    {
        discardPile.addAll( drawPile );
        drawPile.clear();
    }
    
    /*
     * Draw the specified number of cards from the draw pile. If necessary, will shuffle the
     * discard pile into the draw pile. If there still aren't enough cards after doing so,
     * just go with what we were able to get.
     * Note that this can also be used for revealing if the returned list is kept separate
     * from the player's hand.
     */
    public List<DominionCard> drawCards( int numCards )
    {
        LinkedList<DominionCard> result = new LinkedList<DominionCard>();
        for(int i = 0; i < numCards; i++ )
        {
            if( drawPile.size() == 0 )
            {
                drawPile.addAll( discardPile );
                discardPile.clear();
                shuffleDrawPile();
                if( drawPile.size() == 0 )
                {
                    //no cards left to draw at all. Just return what we've got.
                    break;
                }
            }
            result.add( drawPile.remove() );
        }
        return result;
    }
    
    /*
     * Put a card on top of the draw pile, so it's drawn next. Some cards do this directly,
     * or it can also be used to return cards from hand / revealing / other top-deck effects
     */
    public void placeCard( DominionCard card )
    {
        drawPile.add( 0, card );
    }
    
    /*
     * Put a card in the discard pile. Can also be used for standard card gain if the source isn't the hand
     * or play area.
     */
    public void discardCard( DominionCard card )
    {
        discardPile.add( card );
    }
}
