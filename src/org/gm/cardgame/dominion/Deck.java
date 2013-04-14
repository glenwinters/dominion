package org.gm.cardgame.dominion;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

import org.gm.cardgame.dominion.cards.DominionCard;
import org.gm.cardgame.dominion.cards.common.CopperCard;
import org.gm.cardgame.dominion.cards.common.EstateCard;
import org.gm.cardgame.dominion.cards.darkages.HovelCard;
import org.gm.cardgame.dominion.cards.darkages.NecropolisCard;
import org.gm.cardgame.dominion.cards.darkages.OvergrownEstateCard;
import org.gm.cardgame.dominion.cards.prosperity.PlatinumCard;

public class Deck
{
    private LinkedList<DominionCard> drawPile;
    private LinkedList<DominionCard> discardPile;

    public Deck( boolean useShelters )
    {
        drawPile = new LinkedList<DominionCard>();
        discardPile = new LinkedList<DominionCard>();

        for ( int i = 0; i < 7; i++ )
        {
            // drawPile.add( new CopperCard() );
            // TODO Change this back to Copper to play real games
            drawPile.add( new PlatinumCard() );
        }
        if ( useShelters )
        {
            drawPile.add( new HovelCard() );
            drawPile.add( new NecropolisCard() );
            drawPile.add( new OvergrownEstateCard() );
        }
        else
        {
            for ( int i = 0; i < 3; i++ )
            {
                drawPile.add( new EstateCard() );
            }
        }
        shuffleDrawPile();
    }

    public Deck( List<DominionCard> cardList )
    {
        drawPile = new LinkedList<DominionCard>();
        discardPile = new LinkedList<DominionCard>();

        drawPile.addAll( cardList );
        shuffleDrawPile();
    }

    public void shuffleDrawPile()
    {
        Collections.shuffle( drawPile );
    }

    public void discardDrawPile()
    {
        discardPile.addAll( drawPile );
        drawPile.clear();
    }

    /*
     * Draw the specified number of cards from the draw pile. If necessary, will
     * shuffle the discard pile into the draw pile. If there still aren't enough
     * cards after doing so, just go with what we were able to get. Note that
     * this can also be used for revealing if the returned list is kept separate
     * from the player's hand.
     */
    public List<DominionCard> drawCards( int numCards )
    {
        LinkedList<DominionCard> result = new LinkedList<DominionCard>();
        for ( int i = 0; i < numCards; i++ )
        {
            if ( drawPile.size() == 0 )
            {
                drawPile.addAll( discardPile );
                discardPile.clear();
                shuffleDrawPile();
                if ( drawPile.size() == 0 )
                {
                    // no cards left to draw at all. Just return what we've got.
                    break;
                }
            }
            result.add( drawPile.remove() );
        }
        return result;
    }

    /*
     * Put a card on top of the draw pile, so it's drawn next. Some cards do
     * this directly, or it can also be used to return cards from hand /
     * revealing / other top-deck effects
     */
    public void placeCard( DominionCard card )
    {
        drawPile.add( 0, card );
    }

    /*
     * Put a card in the discard pile. Can also be used for standard card gain
     * if the source isn't the hand or play area.
     */
    public void discardCard( DominionCard card )
    {
        discardPile.add( card );
    }

    /*
     * Put multiple cards in the discard pile. Order doesn't matter.
     */
    public void discardCards( List<DominionCard> cards )
    {
        discardPile.addAll( cards );
    }

    /*
     * Calculate the amount of victory points the deck is worth
     */
    public int getVictoryPoints()
    {
        int victoryPoints = 0;
        for ( DominionCard card : this.getCardList() )
        {
            victoryPoints += card.getVictoryPoints( this );
        }

        return victoryPoints;
    }

    /*
     * Calculate the deck size
     */
    public int size()
    {
        return this.drawPile.size() + this.discardPile.size();
    }

    public List<DominionCard> getCardList()
    {
        List<DominionCard> deckCards = new LinkedList<DominionCard>();
        deckCards.addAll( this.drawPile );
        deckCards.addAll( this.discardPile );
        return deckCards;
    }
}
