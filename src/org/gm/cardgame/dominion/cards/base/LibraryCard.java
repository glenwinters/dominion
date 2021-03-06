package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class LibraryCard extends DominionCard
{
    public LibraryCard()
    {
        super( "Library", 5, 0, EnumSet.of( DominionCard.CardType.ACTION ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();

        while ( currentPlayer.getHand().size() < 7 )
        {
            // Draw a card
            List<DominionCard> cardsDrawn = currentPlayer.drawCards( 1 );
            if( cardsDrawn.size() == 0 )
            {
                // no cards left to draw. just stop.
                break;
            }

            DominionCard cardDrawn = cardsDrawn.get( 0 );

            // Prompt to set aside action cards
            if( cardDrawn.getType().contains( DominionCard.CardType.ACTION ) )
            {
                boolean setCardAside = currentPlayer.promptYesNo( "Set aside " + cardDrawn.getName() + "?" );
                if( setCardAside )
                {
                    currentPlayer.discardCard( cardDrawn );
                }
            }
        }
    }
}
