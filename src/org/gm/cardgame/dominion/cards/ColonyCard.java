package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

public class ColonyCard extends DominionCard
{
    public ColonyCard()
    {
        super( "Colony", 11, 0, EnumSet.of( DominionCard.CardType.VICTORY ),
                DominionCard.CardSet.PROSPERITY );
    }
}
