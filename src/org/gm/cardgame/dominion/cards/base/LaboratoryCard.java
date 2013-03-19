package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class LaboratoryCard extends DominionCard
{
    public LaboratoryCard()
    {
        super( "Festival", 5, 0, EnumSet.of( DominionCard.CardType.ACTION ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();

        game.addActions( 1 );

        currentPlayer.drawCards( 2 );
    }
}
