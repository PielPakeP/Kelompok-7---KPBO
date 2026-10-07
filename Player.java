import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    private GreenfootImage[] walkFrames = new GreenfootImage[4];
    private int frame = 0;
    private int animationCounter = 0;
    private boolean facingLeft = false;

    public Player()
    {
        walkFrames[0] = new GreenfootImage("1.png");
        walkFrames[1] = new GreenfootImage("2.png");
        walkFrames[2] = new GreenfootImage("3.png");
        walkFrames[3] = new GreenfootImage("4.png");
        for (int i = 0; i < 4; i++)
        {
            walkFrames[i].scale(70, 50);
        }
        setImage(walkFrames[0]);        
    }

    public void act()
    {
        movement();
    }

    private void movement()
    {
        boolean moving = false;

        if (Greenfoot.isKeyDown("a"))
        {
            setLocation(getX() - 1, getY());
            facingLeft = true;
            moving = true;
        }

        if (Greenfoot.isKeyDown("d"))
        {
            setLocation(getX() + 1, getY());
            facingLeft = false;
            moving = true;
        }

        if (Greenfoot.isKeyDown("w"))
        {
            setLocation(getX(), getY() - 1);
            moving = true;
        }

        if (Greenfoot.isKeyDown("s"))
        {
            setLocation(getX(), getY() + 1);
            moving = true;
        }

        if (moving)
        {
            animateWalk();
        }
    }

        private void animateWalk()
    {
        animationCounter++;

        if (animationCounter >= 5)
        {
            animationCounter = 0;
            frame++;

        if (frame >= 4)
        {
            frame = 0;
        }

        GreenfootImage image = new GreenfootImage(walkFrames[frame]);

        if (facingLeft)
        {
            image.mirrorHorizontally();
        }

        setImage(image);
        }
    }
}
