package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;

public class PlatinumCard extends DominionCard
{
    public PlatinumCard()
    {
        super( "Platinum", 9, 0, EnumSet.of( DominionCard.CardType.TREASURE ),
                DominionCard.CardSet.PROSPERITY );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addCoins( 5 );
    }
}
