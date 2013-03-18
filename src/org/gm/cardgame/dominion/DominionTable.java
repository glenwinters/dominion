package org.gm.cardgame.dominion;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

import org.gm.cardgame.dominion.cards.DominionCard;

public class DominionTable
{
    private HashMap<String, KingdomPile> kingdom;
    private List<DominionCard> trash;
    
    public DominionTable(List<DominionCard> cards)
    {
        kingdom = new HashMap<String, KingdomPile>();
        trash = new LinkedList<DominionCard>(); 
        
        //populate kingdom
    }
    
    public DominionCard gainCard( String name )
    {
        KingdomPile pile = kingdom.get( name );
        if( pile == null )
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
    
    public boolean isGameOver()
    {
        int pilesEmpty = 0;
        for( KingdomPile pile : kingdom.values() )
        {
            if( pile.getCardsRemaining() == 0 )
            {
                DominionCard card = pile.getCard();
                if(card.getName().equals("Province") || card.getName().equals("Colony"))
                {
                    return true;
                } else
                {
                    pilesEmpty++;
                    if(pilesEmpty >= 3)
                    {
                        return true;
                    }
                }
            }
        }
        
		return false;
    }
}
