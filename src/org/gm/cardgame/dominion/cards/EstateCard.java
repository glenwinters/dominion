package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

public class EstateCard extends DominionCard
{
    public EstateCard()
    {
        super( "Estate", 2, 0, EnumSet.of( DominionCard.CardType.VICTORY ),
                DominionCard.CardSet.BASE );
    }
}
