package org.gm.cardgame.dominion.cards.darkages;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class HovelCard extends DominionCard
{
    public HovelCard()
    {
        super( "Hovel", 1, 0, EnumSet.of( DominionCard.CardType.SHELTER, DominionCard.CardType.REACTION ), DominionCard.CardSet.DARKAGES );
    }

    @Override
    public boolean canReact( ReactionTriggerType actionType, DominionCard card )
    {
        return actionType == ReactionTriggerType.BUY && card.getType().contains( DominionCard.CardType.VICTORY );
    }

    @Override
    public void onReveal( DominionGame game, DominionPlayer owner )
    {
        // TODO: check if we actually want to prompt this here
        if( owner.promptYesNo( "Trash Hovel?" ) )
        {
            owner.removeCardFromHand( this );
            game.trashCard( this, owner );
        }
    }
}
