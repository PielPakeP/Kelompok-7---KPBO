import greenfoot.*;

/** Panah penanda ruangan tujuan di peta (naik-turun). Gambar: marker (34 x 34, .png transparan). */
public class Marker extends Actor
{
    private int baseY = -1;
    private int tick = 0;

    public Marker()
    {
        setImage(Gfx.img("marker", 34, 34));
    }

    public void act()
    {
        if (baseY < 0) baseY = getY();
        tick++;
        setLocation(getX(), baseY + (int) (Math.sin(tick * 0.12) * 5));
    }
}
