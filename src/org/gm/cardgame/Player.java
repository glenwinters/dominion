package org.gm.cardgame;

public abstract class Player
{
    private User user;

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }
}
