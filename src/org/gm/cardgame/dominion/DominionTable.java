package org.gm.cardgame.dominion;

import java.util.List;
import org.gm.cardgame.dominion.cards.DominionCard;

public class DominionTable
{
    private List<KingdomPile> kingdom; //maybe HashMap would be better for this
    private List<DominionCard> trash;
    
    public DominionTable(List<DominionCard> cards)
    {
        
    }
    
    public boolean checkGameEnd()
    {
		return false; // FIX
    }
}
