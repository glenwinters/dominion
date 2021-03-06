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
        cardsToTrash = currentPlayer.promptToChooseMultipleCards( currentPlayer.getHand(), 0, 4, "Choose up to 4 cards to trash", true );
        // For multi-trashers, you need to choose all the cards you're going to trash at once, then on-trash effects occur once you're done
        // choosing. They don't happen one at a time as you choose.
        if( cardsToTrash.size() > 0 )
        {
            for ( DominionCard card : cardsToTrash )
            {
                currentPlayer.removeCardFromHand( card );
                game.trashCard( card, currentPlayer );
            }
        }
    }
}
