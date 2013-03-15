package org.gm.cardgame.dominion;

import org.gm.cardgame.dominion.cards.DominionCard;

public class KingdomPile
{
    private final DominionCard card;
    private int cardsRemaining;
    private int currentCoinCost;
    private int currentPotionCost;
    private boolean banned;
    private boolean tradeRouteToken;
    private int embargoTokens;

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
        // else if ( card instanceof org.gm.cardgame.dominion.cards.RatsCard )
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
        setTradeRouteToken(false);
        embargoTokens = 0;
    }

    // this one is just for reading info from the card inside, not actual gameplay
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
        //banning can only be cleared on reset after a turn.
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
        //there is no way to remove embargo tokens during a game.
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
        catch(InstantiationException|IllegalAccessException ie)
        {
            //should never happen, at least once we get everything straightened out, as there is no variable input to this call.
            //when we have logging, log this exception and fail.
            return null;
        }
    }

    public void returnCard()
    {
        cardsRemaining++;
    }
}
