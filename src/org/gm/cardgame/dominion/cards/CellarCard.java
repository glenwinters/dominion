package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;

public class CellarCard extends DominionCard
{
    public CellarCard()
    {
        super( "Cellar", 2, 0, EnumSet.of( DominionCard.CardType.ACTION ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        int cardsDiscarded = 0;
        DominionPlayer currentPlayer = game.getCurrentPlayer();
        game.addActions(1);
        
        DominionCard cardToDiscard = null;
        do
        {
            cardToDiscard = currentPlayer.promptToDiscard(true);
            if( cardToDiscard != null )
            {
                currentPlayer.discardCard( cardToDiscard );
                cardsDiscarded++;
            }
        } while ( cardToDiscard != null );
        currentPlayer.drawCards( cardsDiscarded );
    }
}
