package org.gm.cardgame.dominion.cards.common;

import java.util.EnumSet;

import org.gm.cardgame.dominion.Deck;
import org.gm.cardgame.dominion.cards.DominionCard;

public class DuchyCard extends DominionCard
{
    public DuchyCard()
    {
        super( "Duchy", 5, 0, EnumSet.of( DominionCard.CardType.VICTORY ), DominionCard.CardSet.BASE );
    }

    @Override
    public int getVictoryPoints( Deck deck )
    {
        return 3;
    }
}
