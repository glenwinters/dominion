package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

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
        DominionPlayer currentPlayer = game.getCurrentPlayer();
        game.addActions( 1 );

        List<DominionCard> cardsToDiscard = null;
        cardsToDiscard = currentPlayer.promptToDiscard( null, 0, 0, true );
        if ( cardsToDiscard.size() > 0 )
        {
            for ( DominionCard card : cardsToDiscard )
            {
                currentPlayer.discardCard( card );
            }
        }
        currentPlayer.drawCards( cardsToDiscard.size() );
    }
}
