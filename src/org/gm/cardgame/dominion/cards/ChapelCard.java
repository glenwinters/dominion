package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;

public class ChapelCard extends DominionCard
{
    public ChapelCard()
    {
        super( "Chapel", 2, 0, EnumSet.of( DominionCard.CardType.ACTION ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        int cardsTrashed = 0;
        DominionPlayer currentPlayer = game.getCurrentPlayer();
        
        DominionCard cardToTrash = null;
        do
        {
            cardToTrash = currentPlayer.promptToTrash(true);
            if( cardToTrash != null )
            {
                currentPlayer.trashCard( cardToTrash );
                cardsTrashed++;
            }
        } while ( cardToTrash != null && cardsTrashed < 4 );
    }
}
