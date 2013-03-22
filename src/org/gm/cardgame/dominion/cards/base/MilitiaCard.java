package org.gm.cardgame.dominion.cards.base;

import java.util.EnumSet;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class MilitiaCard extends DominionCard
{
    public MilitiaCard()
    {
        super( "Militia", 4, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.ATTACK ),
                DominionCard.CardSet.BASE );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        game.addCoins( 2 );

        for (DominionPlayer opponent : game.getOpponents())
        {
            int numCardsToDiscard = opponent.getHand().size() - 3;
            if( numCardsToDiscard > 0 )
            {
                List<DominionCard> cardsToDiscard = 
                        opponent.promptToChooseMultipleCards( null, numCardsToDiscard, numCardsToDiscard, 
                        "Choose " + numCardsToDiscard + " cards to discard", false);
                for( DominionCard card : cardsToDiscard )
                {
                    opponent.discardCard( card );
                }
            }
        }
    }
}
