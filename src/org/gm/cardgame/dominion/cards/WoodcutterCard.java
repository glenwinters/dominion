package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;

public class WoodcutterCard extends DominionCard
{
    public WoodcutterCard()
    {
        super( "Woodcutter", 3, 0, EnumSet.of( DominionCard.CardType.ACTION ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addBuys( 1 );
        game.addCoins( 2 );
    }
}
