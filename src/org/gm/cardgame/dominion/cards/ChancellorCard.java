package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;

public class ChancellorCard extends DominionCard
{
    public ChancellorCard()
    {
        super( "Chancellor", 3, 0, EnumSet.of( DominionCard.CardType.ACTION ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();
        game.addCoins( 2 );
        if ( currentPlayer.promptToDiscardDeck() )
        {
            currentPlayer.discardDeck();
        }
    }
}
