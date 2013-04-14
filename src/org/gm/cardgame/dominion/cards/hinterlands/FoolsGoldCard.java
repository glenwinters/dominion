package org.gm.cardgame.dominion.cards.hinterlands;

import java.util.EnumSet;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class FoolsGoldCard extends DominionCard
{
    public FoolsGoldCard()
    {
        super( "Fool's Gold", 2, 0, EnumSet.of( DominionCard.CardType.TREASURE, DominionCard.CardType.REACTION ),
                DominionCard.CardSet.HINTERLANDS );
    }

    @Override
    public void onPlay( DominionGame game )
    {
        int cardValue = 1;
        List<DominionCard> playedCards = game.getPlayedCards();

        for ( DominionCard card : playedCards )
        {
            if( card instanceof org.gm.cardgame.dominion.cards.hinterlands.FoolsGoldCard )
            {
                cardValue = 4;
            }
        }

        game.addCoins( cardValue );
    }

    @Override
    public boolean canReact( ReactionTriggerType actionType, DominionCard card )
    {
        return actionType == ReactionTriggerType.OTHER_PLAYER_GAIN && card instanceof org.gm.cardgame.dominion.cards.common.ProvinceCard;
    }

    @Override
    public void onOpponentGainReveal( DominionGame game, DominionPlayer owner, DominionCard cardToGain )
    {
        // TODO: check if we actually want to prompt this here
        if( owner.promptYesNo( "Trash Fool's Gold to gain Gold on your deck?" ) )
        {
            owner.removeCardFromHand( this );
            game.trashCard( this, owner );
            owner.placeCardOnDeck( game.takeCardFromSupply( "Gold", owner ) );
        }
    }
}
