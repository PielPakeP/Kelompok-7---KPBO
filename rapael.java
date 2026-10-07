import greenfoot.*;  

public class rapael extends Actor
{
    public void act()
    {
        bergerak();
    }

    public void bergerak()
    {
        if (Greenfoot.isKeyDown("left"))
        {
            setLocation(getX() - 3, getY());
        }

        if (Greenfoot.isKeyDown("right"))
        {
            setLocation(getX() + 3, getY());
        }

        if (Greenfoot.isKeyDown("up"))
        {
            setLocation(getX(), getY() - 3);
        }

        if (Greenfoot.isKeyDown("down"))
        {
            setLocation(getX(), getY() + 3);
        }
    }
}