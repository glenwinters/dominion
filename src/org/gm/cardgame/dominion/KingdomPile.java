package org.gm.cardgame.dominion;

import java.util.Stack;

import org.gm.cardgame.dominion.cards.DominionCard;
import org.gm.cardgame.dominion.cards.common.*;
import org.gm.cardgame.dominion.cards.prosperity.*;
import org.gm.cardgame.dominion.cards.alchemy.*;

public class KingdomPile
{
    // TODO: Decide if discounts need to be here. We need to keep track of discounts in DominionGame, so there may be
    // no need to replicate that sort of info.
    private final DominionCard card;
    private Stack<DominionCard> cardSupply;
    private int currentCoinCost;
    private int currentPotionCost;
    private boolean banned;
    private boolean tradeRouteToken;
    private int embargoTokens;

    public KingdomPile( final DominionCard card, final int numPlayers )
    {
        card.setId( -1 );
        this.card = card;
        cardSupply = new Stack<DominionCard>();
        int cardsRemaining;
        if ( card.getType().contains( DominionCard.CardType.VICTORY ) )
        {
            if ( numPlayers == 2 )
            {
                cardsRemaining = 8;
            }
            else
            {
                cardsRemaining = 12;
            }
        }
        else if ( card.getType().contains( DominionCard.CardType.CURSE )
                || card.getType().contains( DominionCard.CardType.RUINS ) )
        {
            cardsRemaining = (numPlayers - 1) * 10;
        }
        else if ( card instanceof CopperCard )
        {
            cardsRemaining = 60 - (7 * numPlayers);
        }
        else if ( card instanceof SilverCard )
        {
            cardsRemaining = 40;
        }
        else if ( card instanceof GoldCard )
        {
            cardsRemaining = 30;
        }
        else if ( card instanceof PlatinumCard )
        {
            cardsRemaining = 12;
        }
        else if ( card instanceof PotionCard )
        {
            cardsRemaining = 16;
        }
        // else if ( card instanceof RatsCard )
        // {
        // cardsRemaining = 20;
        // }
        else
        {
            cardsRemaining = 10;
        }
        
        for( int i = 0; i < cardsRemaining; i++ )
        {
            try 
            {
                // TODO: knights and ruins don't follow this pattern.
                DominionCard newCard = card.getClass().newInstance();
                newCard.setId( i );
                cardSupply.push( newCard );
            }
            catch ( InstantiationException | IllegalAccessException ie )
            {
                // should never happen, at least once we get everything straightened
                // out, as there is no variable input to this call.
                // TODO: when we have logging, log this exception and fail.
            }
        }

        currentCoinCost = card.getCoinCost();
        currentPotionCost = card.getPotionCost();
        banned = false;
        tradeRouteToken = false;
        embargoTokens = 0;
    }

    // this one is just for reading info from the card inside, not actual gameplay
    public DominionCard getCardPrototype()
    {
        return card;
    }

    public int getCardsRemaining()
    {
        return cardSupply.size();
    }

    // TODO: scheduled for deletion pending revision of how discounts are stored and applied
    public void addCoinDiscount( int amount )
    {
        currentCoinCost -= amount;
        if ( currentCoinCost < 0 )
        {
            currentCoinCost = 0;
        }
    }

    // TODO: scheduled for deletion pending revision of how discounts are stored and applied
    public int getCoinCost()
    {
        return currentCoinCost;
    }

    // TODO: scheduled for deletion pending revision of how discounts are stored and applied
    public int getPotionCost()
    {
        return currentPotionCost;
    }

    public boolean isBanned()
    {
        return banned;
    }

    public void banCard()
    {
        // banning can only be cleared on reset after a turn.
        banned = true;
    }

    public void resetCard()
    {
        banned = false;
        currentCoinCost = card.getCoinCost();
        currentPotionCost = card.getPotionCost();
    }

    public int getEmbargoTokens()
    {
        return embargoTokens;
    }

    public void addEmbargoToken()
    {
        // there is no way to remove embargo tokens during a game.
        embargoTokens++;
    }

    public boolean hasTradeRouteToken()
    {
        return tradeRouteToken;
    }

    public void setTradeRouteToken( boolean tradeRouteToken )
    {
        this.tradeRouteToken = tradeRouteToken;
    }

    public boolean isBuyable()
    {
        return !banned && !cardSupply.empty();
    }

    public DominionCard takeCard()
    {
        if ( cardSupply.empty() )
        {
            return null;
        }

        return cardSupply.pop();
    }

    public void returnCard( DominionCard card )
    {
        cardSupply.push( card );
    }
}
