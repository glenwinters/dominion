package org.gm.cardgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.gm.cardgame.Game.GameName;

public class GameServer
{
    private static List<Game> gameList = new LinkedList<Game>();
    private static List<User> userList = new LinkedList<User>();

    public static void main( String[] args )
    {
        // This main method ignores the server-client model for now in order to
        // test the game before we have the network stuff done

        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String input = null;

        System.out.println( "-- Card Game Server --" );

        // Get username
        System.out.println( "Username for player 1: " );
        try
        {
            input = br.readLine();
        }
        catch ( IOException e )
        {
            System.out.println( "Error: barfed on I/O stuff" );
            return;
        }

        // Create user
        User user1 = new User( input, 0 );
        userList.add( user1 );

        System.out.println( "Username for player 2: " );
        try
        {
            input = br.readLine();
        }
        catch ( IOException e )
        {
            System.out.println( "Error: barfed on I/O stuff" );
            return;
        }

        // Create user
        User user2 = new User( input, 1 );
        userList.add( user2 );

        // Create LobbyGame
        LobbyGame currentLobbyGame = new LobbyGame( GameName.DOMINION, 2, user1 );

        // Add the second user to the game
        if ( !currentLobbyGame.addUser( user2 ) )
        {
            System.out.println( "Game full" );
        }

        // Start the LobbyGame
        if ( !currentLobbyGame.startGame( gameList ) )
        {
            System.out.println( "Could not start game" );
            return;
        }
    }
}
