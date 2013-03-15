package org.gm.cardgame;

public class GameClient
{
    
    private static Game currentGame;
    
    public static void main(String[] args) 
    {
        System.out.println("-- Card Game Client --");
    }
    
    public static Game getCurrentGame()
    {
        return currentGame;
    }
}
