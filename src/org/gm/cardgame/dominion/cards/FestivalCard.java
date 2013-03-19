package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;

public class FestivalCard extends DominionCard
{
    public FestivalCard()
    {
        super( "Festival", 5, 0, EnumSet.of( DominionCard.CardType.ACTION ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addActions( 2 );
        game.addBuys( 1 );
        game.addCoins( 2 );
    }
}
