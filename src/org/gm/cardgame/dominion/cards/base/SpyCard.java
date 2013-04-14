package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class SpyCard extends DominionCard
{
    public SpyCard()
    {
        super( "Spy", 4, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.ATTACK ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();
        List<DominionPlayer> allPlayers = new LinkedList<DominionPlayer>();

        // Create list of all players
        allPlayers.add( currentPlayer );
        allPlayers.addAll( game.getOpponents( currentPlayer ) );

        // Add actions and draws
        game.addActions( 1 );
        currentPlayer.drawCards( 1 );

        // Reveal a card and prompt for discard for each player
        for ( DominionPlayer player : allPlayers )
        {
            List<DominionCard> cardRevealedList = player.takeCardsFromDeck( 1 );

            // TODO Should reveal card to the other players

            if( cardRevealedList.size() != 0 )
            {
                DominionCard cardRevealed = cardRevealedList.get( 0 );

                // TODO Change prompt to not have the card name once there is a
                // GUI
                boolean discard = player.promptYesNo( "Discard " + cardRevealed.getName() + "?" );

                if( discard )
                {
                    player.discardCard( cardRevealed );
                }
                else
                {
                    player.placeCardOnDeck( cardRevealed );
                }
            }
        }
    }

}
