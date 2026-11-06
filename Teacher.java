import greenfoot.*;

/**
 * Guru / NPC. Gambar: teacher_1 ... teacher_4 (guru tiap level) dan teacher_principal (Kepala Sekolah).
 * Disarankan .png transparan, tinggi sekitar 230 px.
 */
public class Teacher extends Actor
{
    private int baseY = -1;
    private int tick = 0;

    public Teacher(int level)
    {
        this(level, 0);
    }

    /** heightPx > 0 -> gambar diubah ukurannya ke tinggi tersebut (proporsi tetap). */
    public Teacher(int level, int heightPx)
    {
        this("teacher_" + level, heightPx);
    }

    public Teacher(String imageName, int heightPx)
    {
        GreenfootImage img = Gfx.img(imageName);
        if (heightPx > 0)
        {
            int w = Math.max(1, img.getWidth() * heightPx / img.getHeight());
            img.scale(w, heightPx);
        }
        setImage(img);
    }

    public void act()
    {
        if (baseY < 0) baseY = getY();
        tick++;
        setLocation(getX(), baseY + (int) (Math.sin(tick * 0.08) * 3));
    }
}
