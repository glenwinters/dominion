package org.gm.cardgame.dominion.cards.prosperity;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;
import org.gm.cardgame.dominion.cards.DominionCard;

public class WatchtowerCard extends DominionCard
{
    private enum Choice
    {
        NONE ( -1 ),
        TRASH ( 0 ),
        TOPDECK ( 1 );
        
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
    
    public WatchtowerCard()
    {
        super( "Watchtower", 3, 0, EnumSet.of( DominionCard.CardType.ACTION, DominionCard.CardType.REACTION ),
                DominionCard.CardSet.PROSPERITY );
    }
    
    @Override
    public void onPlay( DominionGame game )
    {
        DominionPlayer currentPlayer = game.getCurrentPlayer();
        
        while ( currentPlayer.getHand().size() < 6 )
        {
            List<DominionCard> cardsDrawn = currentPlayer.drawCards( 1 );
            if( cardsDrawn.size() == 0 )
            {
                break;
            }
        }
    }
    
     @Override
    public boolean canReact( ReactionTriggerType actionType )
    {
        return actionType == ReactionTriggerType.OWNER_GAIN;
    }

    /*
     * Ask player if they want to put the gained card on top of the deck, trash it, or not reveal.
     */
    @Override
    public DominionCard onOwnerGainReveal( DominionGame game, DominionPlayer owner, DominionCard cardToGain )
    {
        List<String> choices = Arrays.asList( "Trash card", "Place card on top of draw pile" ); 
        int choice = owner.promptMultipleChoice( choices, true );
        if( choice == Choice.TOPDECK.getValue() )
        {
            owner.placeCardOnDeck( cardToGain );
            return null; // card is already placed; don't let normal gain happen.
        }
        else if( choice == Choice.TRASH.getValue() )
        {
            game.trashCard( cardToGain, owner );
            return null; // don't let normal gain happen
        }
        
        // For now, assume anything else means don't reveal;
        return cardToGain;
    }
}
