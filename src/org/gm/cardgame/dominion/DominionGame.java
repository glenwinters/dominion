package org.gm.cardgame.dominion;

import java.util.List;

public class DominionGame extends org.gm.cardgame.Game
{
    
    public DominionGame(List<DominionPlayer> players, List<DominionCard> cards)
    {
        this.players = players;
        this.table = new DominionTable(cards);
    }
    
    public boolean checkGameEnd()
    {
        return table.checkGameEnd();
    }
}
