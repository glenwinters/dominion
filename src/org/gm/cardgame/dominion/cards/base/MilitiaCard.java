package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class MilitiaCard extends DominionCard
{
    public MilitiaCard()
    {
        super( "Militia", 4, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.ATTACK ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addCoins( 2 );

        for (DominionPlayer opponent : game.getOpponents())
        {
            while (opponent.getHand().size() > 3)
            {
                opponent.promptToDiscard( null, 1, 1, false);
            }
        }
    }
}
