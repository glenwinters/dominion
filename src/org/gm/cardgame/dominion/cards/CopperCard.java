package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;

public class CopperCard extends DominionCard
{
    public CopperCard()
    {
        super( "Copper", 0, 0, EnumSet.of( DominionCard.CardType.TREASURE ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addCoins( 1 );
    }
}
