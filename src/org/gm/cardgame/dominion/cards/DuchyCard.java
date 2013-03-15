package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

public class DuchyCard extends DominionCard
{
    public DuchyCard()
    {
        super( "Duchy", 5, 0, EnumSet.of( DominionCard.CardType.VICTORY ),
                DominionCard.CardSet.BASE );
    }
}
