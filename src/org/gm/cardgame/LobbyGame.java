package org.gm.cardgame;

import java.util.LinkedList;
import java.util.List;

import org.gm.cardgame.Game.GameName;
import org.gm.cardgame.dominion.DominionGame;
import org.gm.cardgame.dominion.cards.base.*;

public class LobbyGame
{
    private GameName gameName;
    private int maxPlayers;
    private List<User> userList;

    public LobbyGame( GameName gameName, int maxPlayers, User creator )
    {
        this.gameName = gameName;
        this.maxPlayers = maxPlayers;
        userList = new LinkedList<User>();
        userList.add( creator );
    }

    // Return true if the game was successfully started; else, return false
    public boolean startGame( List<Game> gameList )
    {
        if ( gameName == GameName.DOMINION )
        {
            // Create card list
            List<DominionCard> cards = new LinkedList<DominionCard>();

            // Add 10 base cards to the card list
            // TODO Allow user to select which cards to play with
            cards.add( new CellarCard() );
            cards.add( new ChancellorCard() );
            cards.add( new ChapelCard() );
            cards.add( new CouncilRoomCard() );
            cards.add( new FestivalCard() );
            cards.add( new ThroneRoomCard() );
            cards.add( new LaboratoryCard() );
            cards.add( new MarketCard() );
            cards.add( new WoodcutterCard() );
            cards.add( new SmithyCard() );

            DominionGame newGame = new DominionGame( userList, cards );

            newGame.startGame( gameList );

            return true;
        }
        return false;
    }

    // Add a given user
    // Return value
    public boolean addUser( User user )
    {
        if ( userList.size() < maxPlayers )
        {
            userList.add( user );
            return true;
        }
        else
        {
            return false;
        }
    }
}
