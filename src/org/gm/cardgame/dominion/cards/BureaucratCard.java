package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;

public class BureaucratCard extends DominionCard
{
    public BureaucratCard()
    {
        super( "Bureaucrat", 4, 0, EnumSet.of( DominionCard.CardType.ACTION,
                DominionCard.CardType.ATTACK ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();

        currentPlayer.placeCardOnDeck( game.gainCard( "Silver" ) );

        for ( DominionPlayer opponent : game.getOpponents() )
        {
            DominionCard vCard = opponent.promptToChooseOneCard( DominionCard.CardType.VICTORY,
                    "Choose a victory card to reveal", false );
            if ( vCard != null )
            {
                // game.reveal(vCard);
                opponent.getHand().remove( vCard );
                opponent.placeCardOnDeck( vCard );
            }
            else
            {
                // game.reveal( opponent.getHand() );
            }
        }
    }
}
