package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

public class CurseCard extends DominionCard
{
    public CurseCard()
    {
        super( "Curse", 0, 0, EnumSet.of( DominionCard.CardType.CURSE ), DominionCard.CardSet.BASE );
    }
}
