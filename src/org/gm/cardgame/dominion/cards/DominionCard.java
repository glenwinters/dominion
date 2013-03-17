package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;

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
    
    // The four things that can trigger a reaction
    public enum ActionType
    {
        ATTACK,
        BUY,
        GAIN,
        TRASH
    }

    protected final String name;
    protected final int coinCost;
    protected final int potionCost;
    protected boolean bane = false;
    protected final EnumSet<CardType> type;
    protected final CardSet set; //not sure this is useful like this. may need to put this info somewhere else
    
    protected DominionCard(String name, int coinCost, int potionCost, EnumSet<CardType> type, CardSet set)
    {
    	this.name = name;
    	this.coinCost = coinCost;
    	this.potionCost = potionCost;
    	this.type = type;
    	this.set = set;
    }
    
    // Card methods to be overridden by cards that do specific things 
    public void onPlay( DominionGame game )
    {
    }
    
    public void onBuy( DominionGame game )
    {
    }
    
    public void onGain( DominionGame game )
    {
    }
    
    public void onDiscard( DominionGame game )
    {
    }
    
    public void onTrash( DominionGame game )
    {
    }
    
    public void onReveal( DominionGame game )
    {
    }
    
    public boolean canReact( ActionType actionType )
    {
        return false;
    }
    
    
    // accessors
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
    
    public boolean isBane()
    {
        return bane;
    }
    
    public void setBane( boolean bane )
    {
        this.bane = bane;
    }
    
    public EnumSet<CardType> getType()
    {
        return type;
    }
    
    //Object overrides
    @Override
    public boolean equals(Object obj)
    {
        if( obj == null || !(obj instanceof DominionCard) )
        {
            return false;
        }
        if( obj == this )
        {
            return true;
        }

        DominionCard otherCard = (DominionCard)obj;
        //cards being equal by name is enough to mean they're the same card.
        return( otherCard.name.equals(this.name) );
    }
}
