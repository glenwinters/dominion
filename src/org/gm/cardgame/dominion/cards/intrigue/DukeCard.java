package org.gm.cardgame.dominion.cards.intrigue;

import java.util.EnumSet;

import org.gm.cardgame.dominion.Deck;
import org.gm.cardgame.dominion.cards.DominionCard;

public class DukeCard extends DominionCard
{
    public DukeCard()
    {
        super( "Duke", 5, 0, EnumSet.of( DominionCard.CardType.VICTORY ), DominionCard.CardSet.INTRIGUE );
    }

    @Override
    public int getVictoryPoints( Deck deck )
    {
        int dukeCount = 0;
        for ( DominionCard card : deck.getCardList() )
        {
            if( card instanceof org.gm.cardgame.dominion.cards.intrigue.DukeCard )
            {
                dukeCount++;
            }
        }
        return dukeCount;
    }
}
