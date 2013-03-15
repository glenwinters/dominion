package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

public class CopperCard extends DominionCard
{
    public CopperCard()
    {
    	super("Copper", 0, 0, false, EnumSet.of(DominionCard.CardType.TREASURE), DominionCard.CardSet.BASE);
    }
    
    @Override
    public void onPlay()
    {
        //currentGame.coins += 1;
    }    
}
