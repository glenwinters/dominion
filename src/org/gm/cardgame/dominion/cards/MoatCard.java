package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;

public class MoatCard extends DominionCard
{
    public MoatCard()
    {
        super( "Moat", 2, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.REACTION ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.getCurrentPlayer().drawCards( 2 );
    }
    
    @Override
    public void onReveal( DominionGame game )
    {
        // get card's owner, and set 'moat played' for this turn?
    }
    
    @Override
    public boolean canReact( DominionCard.ActionType actionType )
    {
        return actionType == DominionCard.ActionType.ATTACK;
    }
}
