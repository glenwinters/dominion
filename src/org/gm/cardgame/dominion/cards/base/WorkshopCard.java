package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class WorkshopCard extends DominionCard
{
    public WorkshopCard()
    {
        super( "Workshop", 3, 0, EnumSet.of( DominionCard.CardType.ACTION ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();
        DominionCard cardToGain = currentPlayer.promptToGain( false, 0, 4 );

        // will probably never be null, but there are edge cases where it might
        // be.
        if ( cardToGain != null )
        {
            currentPlayer.gainCard( cardToGain );
        }
    }
}
