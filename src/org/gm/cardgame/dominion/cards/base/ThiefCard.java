package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class ThiefCard extends DominionCard
{
    public ThiefCard()
    {
        super( "Thief", 4, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.ATTACK ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();

        for ( DominionPlayer opponent : game.getOpponents( currentPlayer ) )
        {
            if( opponent.isVulnerableToAttack() )
            {
                // Reveal 2 cards
                List<DominionCard> cardRevealedList = opponent.takeCardsFromDeck( 2 );

                if( cardRevealedList.size() != 0 )
                {
                    List<DominionCard> treasureCards = new LinkedList<DominionCard>();

                    // Check for treasure cards
                    for ( DominionCard cardRevealed : cardRevealedList )
                    {
                        if( cardRevealed.getType().contains( DominionCard.CardType.TREASURE ) )
                        {
                            treasureCards.add( cardRevealed );
                        }
                    }

                    // Prompt to pick a treasure card
                    if( treasureCards.size() != 0 )
                    {
                        // TODO technically this should not be an optional
                        // prompt, but it is until the prompting is fixed
                        List<DominionCard> cardToTrashList = currentPlayer.promptToChooseMultipleCards( treasureCards, 1, 1,
                                "Pick a treasure card to trash", true );
                        if( cardToTrashList.size() != 0 )
                        {
                            DominionCard cardToTrash = cardToTrashList.get( 0 );
                            game.trashCard( cardToTrash, opponent );
                            cardRevealedList.remove( cardToTrash );
                        }

                    }

                    // Discard other revealed cards
                    opponent.addCardsToDiscardPile( cardRevealedList );

                }
            }
        }
    }
}
