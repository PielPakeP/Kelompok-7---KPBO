import greenfoot.*;

/** Saklar ON / OFF dari gambar toggle_on dan toggle_off (150 x 40). Klik untuk mengganti. */
public class Toggle extends Actor
{
    private boolean on;
    private GreenfootImage onImg = Gfx.img("toggle_on", 150, 40);
    private GreenfootImage offImg = Gfx.img("toggle_off", 150, 40);

    public Toggle(boolean on)
    {
        this.on = on;
        setImage(on ? onImg : offImg);
    }

    public boolean isOn()
    {
        return on;
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            on = !on;
            SoundManager.sfx("click.wav");
            setImage(on ? onImg : offImg);
        }
    }
}
