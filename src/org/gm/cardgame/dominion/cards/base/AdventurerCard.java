package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;
import java.util.List;

import org.gm.cardgame.dominion.Deck;
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
        List<DominionCard> playerHand = currentPlayer.getHand();
        int handSize = playerHand.size();
        int prevHandSize = -1;
        while ( treasureCount < 2 )
        {
            currentPlayer.drawCards( 1 );

            // TODO Should reveal cards to the other players, not just draw

            // Stop if there are no cards left to draw
            handSize = playerHand.size();
            if ( handSize == prevHandSize )
            {
                break;
            }

            DominionCard drawnCard = playerHand.get( handSize - 1 );

            // If the drawn card is a Treasure card, increment the treasure
            // count
            if ( drawnCard.getType().contains( DominionCard.CardType.TREASURE ) )
            {
                treasureCount++;
            }

            prevHandSize = handSize;
        }
    }

}
