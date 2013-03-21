package org.gm.cardgame;

public class User
{
    private String name;
    private int id;

    public User( String name, int id )
    {
        this.name = name;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }
}
