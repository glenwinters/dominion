package org.gm.cardgame.dominion.cards.prosperity;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.cards.DominionCard;

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
