package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class BureaucratCard extends DominionCard
{
    public BureaucratCard()
    {
        super( "Bureaucrat", 4, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.ATTACK ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();

        currentPlayer.placeCardOnDeck( game.takeCardFromSupply( "Silver", currentPlayer ) );

        for ( DominionPlayer opponent : game.getOpponents( currentPlayer ) )
        {
            if( opponent.isVulnerableToAttack() )
            {
                DominionCard vCard = opponent.promptToChooseOneCard( currentPlayer.getCardsByType( DominionCard.CardType.VICTORY ),
                        "Choose a victory card to reveal", false );
                if( vCard != null )
                {
                    // TODO game.reveal(vCard);
                    opponent.getHand().remove( vCard );
                    opponent.placeCardOnDeck( vCard );
                }
                else
                {
                    // TODO game.reveal( opponent.getHand() );
                }
            }
        }
    }
}
