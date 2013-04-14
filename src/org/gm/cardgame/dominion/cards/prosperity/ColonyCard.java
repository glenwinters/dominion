package org.gm.cardgame.dominion.cards.prosperity;

import java.util.EnumSet;

import org.gm.cardgame.dominion.Deck;
import org.gm.cardgame.dominion.cards.DominionCard;

public class ColonyCard extends DominionCard
{
    public ColonyCard()
    {
        super( "Colony", 11, 0, EnumSet.of( DominionCard.CardType.VICTORY ), DominionCard.CardSet.PROSPERITY );
    }

    @Override
    public int getVictoryPoints( Deck deck )
    {
        return 10;
    }
}
