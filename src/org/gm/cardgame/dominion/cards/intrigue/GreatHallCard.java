package org.gm.cardgame.dominion.cards.intrigue;

import java.util.EnumSet;

import org.gm.cardgame.dominion.Deck;
import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.cards.DominionCard;

public class GreatHallCard extends DominionCard
{
    public GreatHallCard()
    {
        super( "Great Hall", 3, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.VICTORY ), DominionCard.CardSet.INTRIGUE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.getCurrentPlayer().drawCards( 1 );
        game.addActions( 1 );
    }

    @Override
    public int getVictoryPoints( Deck deck )
    {
        return 1;
    }
}
