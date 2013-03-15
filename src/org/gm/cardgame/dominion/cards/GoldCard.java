package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.GameClient;
import org.gm.cardgame.dominion.DominionGame;

public class GoldCard extends DominionCard
{
    public GoldCard()
    {
    	super("Gold", 6, 0, EnumSet.of(DominionCard.CardType.TREASURE), DominionCard.CardSet.BASE);
    }
    
    @Override
    public void onPlay()
    { 
        DominionGame currentGame = (DominionGame)GameClient.getCurrentGame();
        currentGame.addCoins(3);
    }    
}
