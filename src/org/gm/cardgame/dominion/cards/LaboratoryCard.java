package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;

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
