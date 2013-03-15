package org.gm.cardgame.dominion;

import java.util.List;
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
        if(card.getType().contains( DominionCard.CardType.VICTORY ) )
        {
            if( numPlayers == 2 )
            {
                cardsRemaining = 8;
            }
            else
            {
                cardsRemaining = 12;
            }
        }
        else if(card.getType().contains( DominionCard.CardType.CURSE ) ||
                    card.getType().contains( DominionCard.CardType.RUINS ) )
        {
            cardsRemaining = ( numPlayers - 1 ) * 10;
        }
//        else if ( card instanceof org.gm.cardgame.dominion.cards.RatsCard )
//        {
//            cardsRemaining = 20;
//        }
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
        if( currentCoinCost < 0 )
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
    
    public boolean getBanned()
    {
        return banned;
    }
    
    public void banCard()
    {
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
        embargoTokens++;
    }
    
    public boolean isBuyable()
    {
        return !banned && cardsRemaining > 0;
    }
    
    public DominionCard takeCard()
    {
        if( cardsRemaining <= 0 )
        {
            return null;
        }
        
        cardsRemaining--;
        // return card.getClass().newInstance(); 
        return card; // FIX - changed to card to fix compiler error
    }
    
    public void returnCard()
    {
        cardsRemaining++;
    }
}
