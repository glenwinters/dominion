package org.gm.cardgame.dominion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.TreeMap;

import org.gm.cardgame.dominion.cards.DominionCard;
import org.gm.cardgame.dominion.cards.common.*;
import org.gm.cardgame.dominion.cards.prosperity.ColonyCard;
import org.gm.cardgame.dominion.cards.prosperity.PlatinumCard;
import org.gm.cardgame.dominion.cards.alchemy.PotionCard;

public class DominionTable
{
    private HashMap<String, KingdomPile> supply;
    private TreeMap<String, KingdomPile> kingdom;
    private List<KingdomPile> basicTreasures;
    private List<KingdomPile> basicVP;
    private List<KingdomPile> extraPiles;
    private List<DominionCard> trash;

    public DominionTable( final List<DominionCard> cards, final int numPlayers, boolean useColonies )
    {
        supply = new HashMap<String, KingdomPile>();
        kingdom = new TreeMap<String, KingdomPile>();
        trash = new LinkedList<DominionCard>();
        basicVP = new ArrayList<KingdomPile>();
        basicTreasures = new ArrayList<KingdomPile>();
        extraPiles = new ArrayList<KingdomPile>();

        basicVP.add( new KingdomPile( new EstateCard(), numPlayers ) );
        basicVP.add( new KingdomPile( new DuchyCard(), numPlayers ) );
        basicVP.add( new KingdomPile( new ProvinceCard(), numPlayers ) );

        basicTreasures.add( new KingdomPile( new CopperCard(), numPlayers ) );
        basicTreasures.add( new KingdomPile( new SilverCard(), numPlayers ) );
        basicTreasures.add( new KingdomPile( new GoldCard(), numPlayers ) );

        extraPiles.add( new KingdomPile( new CurseCard(), numPlayers ) );

        if ( useColonies )
        {
            basicVP.add( new KingdomPile( new ColonyCard(), numPlayers ) );
            basicTreasures.add( new KingdomPile( new PlatinumCard(), numPlayers ) );
        }

        boolean needPotions = false;
        for ( DominionCard card : cards )
        {
            KingdomPile newPile = new KingdomPile( card, numPlayers );
            kingdom.put( card.getName(), newPile );
            supply.put( card.getName(), newPile );

            if ( card.getPotionCost() > 0 )
            {
                needPotions = true;
            }

            // TODO: some other cards need supplementary things done when
            // they're present
            // urchin and hermit need mercenary and madman, NOT in supply
            // tournament needs prizes, NOT in supply
            // black market needs a black market deck, NOT in supply

            // if ( card instanceof TradeRouteCard )
            // {
            // foreach( KingdomPile vpPile : basicVP )
            // {
            // vpCard.setTradeRouteToken( true );
            // }
            // }

            if ( card.getType().contains( DominionCard.CardType.LOOTER ) )
            {
                // add ruins to extraPiles
                // put ruins pile in supply
            }
        }

        if ( needPotions )
        {
            basicTreasures.add( new KingdomPile( new PotionCard(), numPlayers ) );
        }

        for ( KingdomPile pile : basicVP )
        {
            supply.put( pile.getCard().getName(), pile );
        }

        for ( KingdomPile pile : basicTreasures )
        {
            supply.put( pile.getCard().getName(), pile );
        }

        for ( KingdomPile pile : extraPiles )
        {
            supply.put( pile.getCard().getName(), pile );
        }
    }

    public DominionCard gainCard( String name )
    {
        KingdomPile pile = supply.get( name );
        if ( pile == null )
        {
            return null;
        }
        return pile.takeCard();
    }

    public void trashCard( DominionCard card )
    {
        trash.add( card );
    }

    public List<DominionCard> getTrash()
    {
        return trash;
    }

    public List<KingdomPile> getBasicVP()
    {
        return basicVP;
    }

    public List<KingdomPile> getBasicTreasures()
    {
        return basicTreasures;
    }

    public List<KingdomPile> getExtraPiles()
    {
        return extraPiles;
    }

    public Collection<KingdomPile> getKingdom() // might need to change this
    {
        return kingdom.values();
    }

    public Collection<KingdomPile> getSupply()
    {
        return supply.values();
    }

    public boolean isGameOver()
    {
        int pilesEmpty = 0;
        for ( KingdomPile pile : supply.values() )
        {
            if ( pile.getCardsRemaining() == 0 )
            {
                DominionCard card = pile.getCard();
                if ( card.getName().equals( "Province" ) || card.getName().equals( "Colony" ) )
                {
                    return true;
                }
                else
                {
                    pilesEmpty++;
                    if ( pilesEmpty >= 3 )
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
