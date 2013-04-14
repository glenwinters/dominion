package org.gm.cardgame.dominion.cards.hinterlands;

import java.util.EnumSet;
import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class TraderCard extends DominionCard
{
    public TraderCard()
    {
        super( "Trader", 4, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.REACTION ), DominionCard.CardSet.HINTERLANDS );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();
        DominionCard cardToTrash = currentPlayer.promptToChooseOneCard( currentPlayer.getHand(), "Choose a card to trash", false );
        if( cardToTrash != null )
        {
            currentPlayer.removeCardFromHand( cardToTrash );
            game.trashCard( cardToTrash, currentPlayer );
            int numSilvers = game.getCurrentCoinCost( cardToTrash );
            for ( int i = 0; i < numSilvers; i++ )
            {
                currentPlayer.addCardToDiscardPile( game.takeCardFromSupply( "Silver", currentPlayer ) );
            }
        }
    }

    @Override
    public boolean canReact( ReactionTriggerType actionType )
    {
        return actionType == ReactionTriggerType.OWNER_PRE_GAIN;
    }

    @Override
    public DominionCard onOwnerGainReveal( DominionGame game, DominionPlayer owner, DominionCard cardToGain )
    {
        if( owner.promptYesNo( "Reveal Trader to gain Silver instead of " + cardToGain.getName() + "?" ) )
        {
            // Yes, this can cause an indefinite loop if the player reveals Trader for the Silver that came
            // from the trader, but that is within the rules if the player wants to do so.
            // The only reason I can think of offhand is to run out the Silver pile and end the game sooner.
            game.returnCardToSupply( cardToGain );
            owner.addCardToDiscardPile( game.takeCardFromSupply( "Silver", owner ) );
            return null;
        }
        return cardToGain;
    }
}
