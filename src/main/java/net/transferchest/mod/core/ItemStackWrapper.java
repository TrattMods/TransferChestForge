package net.transferchest.mod.core;

import java.io.Serializable;

public class ItemStackWrapper implements Serializable
{
    private int ID;
    private int count;
    
    public ItemStackWrapper(int ID, int count)
    {
        this.ID = ID;
        this.count = count;
    }
    
    public int getID()
    {
        return ID;
    }
    
    public int getCount()
    {
        return count;
    }
}
