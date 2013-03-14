package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

public abstract class DominionCard
{

    public enum CardSet
    {
        BASE,
        INTRIGUE,
        SEASIDE,
        ALCHEMY,
        PROSPERITY,
        CORNUCOPIA,
        HINTERLANDS,
        DARKAGES
    }

    public enum CardType
    {
        TREASURE,
        ACTION,
        VICTORY,
        CURSE,
        ATTACK,
        REACTION,
        DURATION,
        PRIZE,
        SHELTER,
        LOOTER,
        RUINS,
        KNIGHT
    }

    protected final String name;
    protected final int coinCost;
    protected final int potionCost;
    protected boolean isBane = false;
    protected final EnumSet<CardType> type;
    protected final CardSet set; //not sure this is useful like this. may need to put this info somewhere else
    
    protected DominionCard()
    {
    }
    
    public void onPlay()
    {
    }
    
    public void onBuy()
    {
    }
    
    public void onGain()
    {
    }
    
    public void onTrash()
    {
    }
    
    public void onReveal()
    {
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getCoinCost()
    {
        return coinCost;
    }
    
    public int getPotionCost()
    {
        return potionCost;
    }
    
    public boolean getIsBane()
    {
        return isBane;
    }
    
    public boolean setIsBane( boolean isBane )
    {
        this.isBane = isBane;
    }
    
    public EnumSet<CardType> getType()
    {
        return type;
    }
}
