package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;

public class PotionCard extends DominionCard
{
    public PotionCard()
    {
        super( "Potion", 4, 0, EnumSet.of( DominionCard.CardType.TREASURE ),
                DominionCard.CardSet.ALCHEMY );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addPotions( 1 );
    }
}
