package org.gm.cardgame.dominion.cards.intrigue;

import java.util.EnumSet;

import org.gm.cardgame.dominion.Deck;
import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.cards.DominionCard;

public class HaremCard extends DominionCard
{
    public HaremCard()
    {
        super( "Harem", 6, 0, EnumSet.of( DominionCard.CardType.TREASURE, DominionCard.CardType.VICTORY ), DominionCard.CardSet.INTRIGUE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addCoins( 2 );
    }

    @Override
    public int getVictoryPoints( Deck deck )
    {
        return 2;
    }
}
