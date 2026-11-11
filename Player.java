
import greenfoot.*;

public class Player extends Actor
{
    private static final int SPEED = 3;
    private static final String[] DIRS = { "down", "up", "left", "right" };

    private final GreenfootImage[][] imgs = new GreenfootImage[4][2];

    private int dir = 0;
    private int tick = 0;
    private boolean walking = false;
    private boolean scriptedWalking = false;

    public Player()
    {
        for (int d = 0; d < 4; d++)
            for (int f = 0; f < 2; f++)
                imgs[d][f] = Gfx.img("player_" + DIRS[d] + "_" + (f + 1));

        setImage(imgs[0][0]);
    }

    public void act()
    {
        MapWorld w = (MapWorld) getWorld();
        if (w == null) return;

        // Saat adegan cerita, jangan menerima input keyboard.
        if (w.isLocked())
        {
            if (!scriptedWalking)
            {
                walking = false;
                updateImage();
            }
            return;
        }

        int dx = 0, dy = 0;

        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) dx -= SPEED;
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) dx += SPEED;
        if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("w")) dy -= SPEED;
        if (Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("s")) dy += SPEED;

        walking = (dx != 0 || dy != 0);

        if (dx != 0)
        {
            dir = dx < 0 ? 2 : 3;
            if (!w.isBlocked(getX() + dx, getY()))
                setLocation(getX() + dx, getY());
        }

        if (dy != 0)
        {
            dir = dy < 0 ? 1 : 0;
            if (!w.isBlocked(getX(), getY() + dy))
                setLocation(getX(), getY() + dy);
        }

        if (walking) tick++;
        updateImage();
    }


    private void updateImage()
    {
        int frame = walking ? (tick / 7) % 2 : 0;
        setImage(imgs[dir][frame]);
    }
}
