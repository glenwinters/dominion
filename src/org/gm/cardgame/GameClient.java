package org.gm.cardgame;

public class GameClient
{

    private static Game currentGame;

    public static void main( String[] args )
    {
        System.out.println( "-- Card Game Client --" );
    }

    // this might not be necessary. Don't know till client structure is
    // determined further.
    public static Game getCurrentGame()
    {
        return currentGame;
    }
}
