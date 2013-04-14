package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class WitchCard extends DominionCard
{
    public WitchCard()
    {
        super( "Witch", 5, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.ATTACK ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();

        currentPlayer.drawCards( 2 );

        for ( DominionPlayer opponent : game.getOpponents( currentPlayer ) )
        {
            if( opponent.isVulnerableToAttack() )
            {
                opponent.addCardToDiscardPile( game.takeCardFromSupply( "Curse", opponent ) );
            }
        }
    }
}
