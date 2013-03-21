package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.cards.DominionCard;

public class VillageCard extends DominionCard
{
    public VillageCard()
    {
        super( "Village", 3, 0, EnumSet.of( DominionCard.CardType.ACTION ), DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addActions( 2 );
        game.getCurrentPlayer().drawCards( 1 );
    }
}
