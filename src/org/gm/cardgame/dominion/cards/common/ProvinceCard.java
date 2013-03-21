package org.gm.cardgame.dominion.cards.common;

import java.util.EnumSet;

import org.gm.cardgame.dominion.cards.DominionCard;

public class ProvinceCard extends DominionCard
{
    public ProvinceCard()
    {
        super( "Province", 8, 0, EnumSet.of( DominionCard.CardType.VICTORY ), DominionCard.CardSet.BASE );
    }
}
