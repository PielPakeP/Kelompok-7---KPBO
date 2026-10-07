import greenfoot.*;

public class spider extends Actor
{
    public void act()
    {
        kejarRapael();
    }

    public void kejarRapael()
    {
        Actor rapael = getWorld().getObjects(rapael.class).get(0);

        turnTowards(rapael.getX(), rapael.getY());
        move(2);
    }
}