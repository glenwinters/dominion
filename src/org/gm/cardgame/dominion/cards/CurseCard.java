package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

public class CurseCard
{
    public CurseCard()
    {
        set = DominionCard.CardSet.BASE;
        name = "Curse";
        coinCost = 0;
        potionCost = 0;
        type = EnumSet.of( DominionCard.CardType.CURSE );
    }
}
