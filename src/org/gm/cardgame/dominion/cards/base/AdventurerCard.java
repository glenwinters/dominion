package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class AdventurerCard extends DominionCard
{
    public AdventurerCard()
    {
        super( "Adventurer", 6, 0, EnumSet.of( DominionCard.CardType.ACTION ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        int treasureCount = 0;
        DominionPlayer currentPlayer = game.getCurrentPlayer();
        List<DominionCard> cardsRevealed = new LinkedList<DominionCard>();
        List<DominionCard> treasuresRevealed = new LinkedList<DominionCard>();

        while ( treasureCount < 2 )
        {
            List<DominionCard> cardRevealedList = currentPlayer.takeCardsFromDeck( 1 );

            // Stop if there are no cards left to draw
            if ( cardRevealedList.size() == 0 )
            {
                break;
            }

            DominionCard cardRevealed = cardRevealedList.get( 0 );

            // Add revealed card to list of revealed cards
            cardsRevealed.add( cardRevealed );

            // TODO Should reveal card to the other players

            // If the drawn card is a Treasure card, increment the treasure
            // count
            if ( cardRevealed.getType().contains( DominionCard.CardType.TREASURE ) )
            {
                treasuresRevealed.add( cardRevealed );
                cardsRevealed.remove( cardRevealed );
                treasureCount++;
            }
        }

        // Put the Treasure cards into the player's hand and discard the others
        currentPlayer.addCardsToDiscardPile( cardsRevealed );
        currentPlayer.addCardsToHand( treasuresRevealed );
    }

}
