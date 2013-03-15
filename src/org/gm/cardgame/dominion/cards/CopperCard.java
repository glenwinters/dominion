package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

public class CopperCard extends DominionCard
{
    public CopperCard()
    {
        set = DominionCard.CardSet.BASE;
        name = "Copper";
        coinCost = 0;
        potionCost = 0;
        type = EnumSet.of( DominionCard.CardType.TREASURE );
    }
    
    @Override
    public void onPlay()
    {
        currentGame.coins += 1;
    }    
}
