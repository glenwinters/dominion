package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.GameClient;
import org.gm.cardgame.dominion.DominionGame;

public class CopperCard extends DominionCard
{
    public CopperCard()
    {
    	super("Copper", 0, 0, EnumSet.of(DominionCard.CardType.TREASURE), DominionCard.CardSet.BASE);
    }
    
    @Override
    public void onPlay()
    {
        //For this and all other cards, I'm going to assume this is safe. If we're playing DominionCards without
        //the current game being a DominionGame, we deserve to have an exception. 
        DominionGame currentGame = (DominionGame)GameClient.getCurrentGame();
        currentGame.addCoins(1);
    }    
}
