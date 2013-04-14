package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class MoatCard extends DominionCard
{
    public MoatCard()
    {
        super( "Moat", 2, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.REACTION ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.getCurrentPlayer().drawCards( 2 );
    }

    @Override
    public void onReveal( DominionGame game, DominionPlayer owner )
    {
        owner.moatRevealed();
    }

    @Override
    public boolean canReact( DominionCard.ReactionTriggerType actionType )
    {
        return actionType == DominionCard.ReactionTriggerType.ATTACK;
    }
}
