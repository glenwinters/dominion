package org.gm.cardgame.dominion.cards;

import java.util.EnumSet;

import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.DominionPlayer;

public abstract class DominionCard implements Comparable<DominionCard>
{

    public enum CardSet
    {
        BASE, INTRIGUE, SEASIDE, ALCHEMY, PROSPERITY, CORNUCOPIA, HINTERLANDS, DARKAGES
    }

    public enum CardType
    {
        TREASURE, ACTION, VICTORY, CURSE, ATTACK, REACTION, DURATION, PRIZE, SHELTER, LOOTER, RUINS, KNIGHT
    }

    // Things that can trigger a reaction
    public enum ReactionTriggerType
    {
        ATTACK, BUY, CURRENT_PLAYER_GAIN, OTHER_PLAYER_GAIN, TRASH
    }

    protected final String name;
    protected final int coinCost;
    protected final int potionCost;
    protected final EnumSet<CardType> type;
    protected final CardSet set; // not sure this is useful like this. may need to put this somewhere else.
    protected int id = -1;

    protected boolean bane = false;
    protected boolean trashed = false; // for cards that get trashed when played.
    protected boolean notInSupply = false; // for black market / madman / mercenary / prizes / spoils / shelters

    protected DominionCard( String name, int coinCost, int potionCost, EnumSet<CardType> type, CardSet set )
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
        this.trashed = true;
    }

    // Reveals may or may not be done by players other than the current player, so we need
    // to include the player doing the revealing
    public void onReveal( DominionGame game, DominionPlayer owner )
    {
    }

    public boolean canReact( ReactionTriggerType actionType )
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
    
    public int getId()
    {
        return id;
    }
    
    public void setId( int id )
    {
        this.id = id;
    }

    public boolean isNotInSupply()
    {
        return notInSupply;
    }

    public void setNotInSupply( boolean notInSupply )
    {
        this.notInSupply = notInSupply;
    }

    public EnumSet<CardType> getType()
    {
        return type;
    }

    public boolean isTrashed()
    {
        return trashed;
    }

    public void setTrashed( boolean trashed )
    {
        this.trashed = trashed;
    }

    // Object overrides
    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null || !(obj instanceof DominionCard) )
        {
            return false;
        }
        if ( obj == this )
        {
            return true;
        }

        DominionCard otherCard = (DominionCard) obj;
        
        return (otherCard.name.equals( this.name ) && otherCard.id == this.id);
    }

    /*
     * Override the compareTo method. Note that this does NOT tie in with
     * equals() and is only used to sort cards by cost in the kingdom.
     */
    @Override
    public int compareTo( DominionCard rhs )
    {
        if ( this.coinCost < rhs.getCoinCost() )
        {
            return -1;
        }
        else if ( this.coinCost > rhs.getCoinCost() )
        {
            return 1;
        }
        // coin costs are equal; compare potions
        if ( this.potionCost < rhs.getPotionCost() )
        {
            return -1;
        }
        else if ( this.potionCost > rhs.getPotionCost() )
        {
            return 1;
        }
        return 0;
    }
    
    @Override
    public String toString()
    {
        return this.name + " " + this.id;
    }
}
