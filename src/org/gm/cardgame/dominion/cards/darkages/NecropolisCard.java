package org.gm.cardgame.dominion.cards.darkages;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.cards.DominionCard;

public class NecropolisCard extends DominionCard
{
    public NecropolisCard()
    {
        super( "Necropolis", 1, 0, EnumSet.of( DominionCard.CardType.SHELTER, DominionCard.CardType.ACTION ), DominionCard.CardSet.DARKAGES );
        this.notInSupply = true;
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addActions( 2 );
    }
}
