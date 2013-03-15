package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

public class ProvinceCard extends DominionCard
{
    public ProvinceCard()
    {
        super( "Province", 8, 0, EnumSet.of( DominionCard.CardType.VICTORY ),
                DominionCard.CardSet.BASE );
    }
}
