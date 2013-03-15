package org.gm.cardgame.dominion;

import java.util.List;

import org.gm.cardgame.Game;
import org.gm.cardgame.Player;
import org.gm.cardgame.dominion.cards.DominionCard;

public class DominionGame extends Game
{
	protected final List<DominionPlayer> players;
	protected final DominionTable table;
    public DominionGame(List<DominionPlayer> players, List<DominionCard> cards)
    {
        this.players = players;
        this.table = new DominionTable(cards);
    }
    
    public boolean checkGameEnd()
    {
        return table.checkGameEnd();
    }

	@Override
	public void startGame(List<Player> players) {
		
	}

	@Override
	public void nextTurn() {
		
	}
}
