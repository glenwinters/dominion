package org.gm.cardgame.dominion.cards.common;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.cards.DominionCard;

public class GoldCard extends DominionCard
{
    public GoldCard()
    {
        super( "Gold", 6, 0, EnumSet.of( DominionCard.CardType.TREASURE ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addCoins( 3 );
    }
}
