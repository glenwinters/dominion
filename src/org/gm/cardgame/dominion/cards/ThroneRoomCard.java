package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;

public class ThroneRoomCard extends DominionCard
{
    public ThroneRoomCard()
    {
        super( "Throne Room", 4, 0, EnumSet.of( DominionCard.CardType.ACTION ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();

        DominionCard actionCard = currentPlayer.promptToChooseOneCard(
                DominionCard.CardType.ACTION, "Choose an action card to play twice", false );

        actionCard.onPlay( game );
        actionCard.onPlay( game );
    }
}