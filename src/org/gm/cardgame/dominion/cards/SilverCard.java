package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.GameClient;
import org.gm.cardgame.dominion.DominionGame;

public class SilverCard extends DominionCard
{
    public SilverCard()
    {
    	super("Silver", 3, 0, EnumSet.of(DominionCard.CardType.TREASURE), DominionCard.CardSet.BASE);
    }
    
    @Override
    public void onPlay()
    { 
        DominionGame currentGame = (DominionGame)GameClient.getCurrentGame();
        currentGame.addCoins(2);
    }    
}
