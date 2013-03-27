package org.gm.cardgame.dominion.cards.darkages;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class OvergrownEstateCard extends DominionCard
{
    public OvergrownEstateCard()
    {
        super( "Overgrown Estate", 1, 0, EnumSet.of( DominionCard.CardType.SHELTER, DominionCard.CardType.VICTORY ), DominionCard.CardSet.DARKAGES );
        this.notInSupply = true;
    }

    @Override
    public void onTrash( DominionGame game, DominionPlayer owner )
    {
        owner.drawCards( 1 );
    }
}
