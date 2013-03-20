package org.gm.cardgame.dominion;

import org.gm.cardgame.dominion.cards.DominionCard;
import org.gm.cardgame.dominion.cards.common.*;
import org.gm.cardgame.dominion.cards.prosperity.*;
import org.gm.cardgame.dominion.cards.alchemy.*;

public class KingdomPile
{
    private final DominionCard card;
    private int cardsRemaining;
    private int currentCoinCost;
    private int currentPotionCost;
    private boolean banned;
    private boolean tradeRouteToken;
    private int embargoTokens;
    
    //TODO: some kingdom piles aren't all the same card, like DA's Knights and Ruins.
    // we may need to eventually put a list in here, instead of one template card.
    // That won't affect the interface though,

    public KingdomPile( final DominionCard card, final int numPlayers )
    {
        this.card = card;
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

        currentCoinCost = card.getCoinCost();
        currentPotionCost = card.getPotionCost();
        banned = false;
        tradeRouteToken = false;
        embargoTokens = 0;
    }

    // this one is just for reading info from the card inside, not actual
    // gameplay
    public DominionCard getCard()
    {
        return card;
    }

    public int getCardsRemaining()
    {
        return cardsRemaining;
    }

    public void addCoinDiscount( int amount )
    {
        currentCoinCost -= amount;
        if ( currentCoinCost < 0 )
        {
            currentCoinCost = 0;
        }
    }

    public int getCoinCost()
    {
        return currentCoinCost;
    }

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
        return !banned && cardsRemaining > 0;
    }

    public DominionCard takeCard()
    {
        if ( cardsRemaining <= 0 )
        {
            return null;
        }

        cardsRemaining--;
        try
        {
            return card.getClass().newInstance();
        }
        catch ( InstantiationException | IllegalAccessException ie )
        {
            // should never happen, at least once we get everything straightened
            // out, as there is no variable input to this call.
            // when we have logging, log this exception and fail.
            return null;
        }
    }

    public void returnCard()
    {
        cardsRemaining++;
    }
}
