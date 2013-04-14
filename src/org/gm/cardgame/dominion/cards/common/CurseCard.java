package org.gm.cardgame.dominion.cards.common;

import java.util.EnumSet;

import org.gm.cardgame.dominion.Deck;
import org.gm.cardgame.dominion.cards.DominionCard;

public class CurseCard extends DominionCard
{
    public CurseCard()
    {
        super( "Curse", 0, 0, EnumSet.of( DominionCard.CardType.CURSE ), DominionCard.CardSet.BASE );
    }

    @Override
    public int getVictoryPoints( Deck deck )
    {
        return -1;
    }
}
