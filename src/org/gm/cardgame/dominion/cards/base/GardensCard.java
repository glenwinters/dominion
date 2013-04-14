package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;

import org.gm.cardgame.dominion.Deck;
import org.gm.cardgame.dominion.cards.DominionCard;

public class GardensCard extends DominionCard
{
    public GardensCard()
    {
        super( "Gardens", 4, 0, EnumSet.of( DominionCard.CardType.VICTORY ), DominionCard.CardSet.BASE );
    }

    @Override
    public int getVictoryPoints( Deck deck )
    {
        return deck.size() / 10;
    }

}
