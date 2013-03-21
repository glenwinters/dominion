package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class ChapelCard extends DominionCard
{
    public ChapelCard()
    {
        super( "Chapel", 2, 0, EnumSet.of( DominionCard.CardType.ACTION ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();

        List<DominionCard> cardsToTrash = null;
        cardsToTrash = currentPlayer.promptToTrash( null, 0, 4, true );
        if ( cardsToTrash.size() > 0 )
        {
            for ( DominionCard card : cardsToTrash )
            {
                currentPlayer.trashCard( card );
            }
        }
    }
}
