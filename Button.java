import greenfoot.*;

/**
 * Tombol bergambar. Contoh: new Button("btn_start", 260, 58)
 * memakai images/btn_start.jpg. Kalau ada btn_start_hover.jpg dipakai saat mouse di atas tombol;
 * kalau tidak ada, tombol sedikit membesar.
 */
public class Button extends Actor
{
    private GreenfootImage normal;
    private GreenfootImage hover;
    private boolean hovering = false;

    public Button(String imageName, int w, int h)
    {
        normal = Gfx.img(imageName, w, h);
        if (Gfx.exists(imageName + "_hover")) hover = Gfx.img(imageName + "_hover", w, h);
        else hover = Gfx.img(imageName, (int) (w * 1.06), (int) (h * 1.06));
        setImage(normal);
    }

    public void act()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        boolean over = (mi != null && mi.getActor() == this);
        if (over != hovering)
        {
            hovering = over;
            setImage(over ? hover : normal);
        }
    }

    /** true jika tombol ini diklik (sekaligus memainkan suara klik). */
    public boolean clicked()
    {
        if (Greenfoot.mouseClicked(this))
        {
            SoundManager.sfx("click.wav");
            return true;
        }
        return false;
    }
}
