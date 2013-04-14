package org.gm.cardgame.dominion.cards.common;

import java.util.EnumSet;

import org.gm.cardgame.dominion.Deck;
import org.gm.cardgame.dominion.cards.DominionCard;

public class EstateCard extends DominionCard
{
    public EstateCard()
    {
        super( "Estate", 2, 0, EnumSet.of( DominionCard.CardType.VICTORY ), DominionCard.CardSet.BASE );
    }

    @Override
    public int getVictoryPoints( Deck deck )
    {
        return 1;
    }
}
