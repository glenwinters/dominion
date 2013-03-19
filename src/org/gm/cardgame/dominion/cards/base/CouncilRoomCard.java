package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class CouncilRoomCard extends DominionCard
{
    public CouncilRoomCard()
    {
        super( "Council Room", 5, 0, EnumSet.of( DominionCard.CardType.ACTION ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();

        game.addBuys( 1 );

        currentPlayer.drawCards( 4 );

        for ( DominionPlayer opponent : game.getOpponents() )
        {
            opponent.drawCards( 1 );
        }
    }
}
