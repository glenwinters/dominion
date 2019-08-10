package org.gm.cardgame.dominion.cards.intrigue;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import org.gm.cardgame.dominion.Deck;
import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class NoblesCard extends DominionCard
{
    private enum Choice
    {
        NONE( -1 ), DRAW3( 0 ), ACTIONS2( 1 );

        private final int value;

        Choice( int value )
        {
            this.value = value;
        }

        public int getValue()
        {
            return this.value;
        }
    };
    public NoblesCard()
    {
        super( "Nobles", 6, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.VICTORY ), DominionCard.CardSet.INTRIGUE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();
        List<String> choices = Arrays.asList( "+3 Cards", "+2 Actions" );
        int choice = currentPlayer.promptMultipleChoice( choices, true );
        if( choice == Choice.DRAW3.getValue() )
        {
            game.getCurrentPlayer().drawCards( 3 );
        }
        else if( choice == Choice.ACTIONS2.getValue() )
        {
            game.addActions( 2 );
        }
    }

    @Override
    public int getVictoryPoints( Deck deck )
    {
        return 2;
    }
}
