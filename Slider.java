import greenfoot.*;

/** Slider volume 0-100 dari gambar: slider_track, slider_fill, slider_knob. Bisa digeser dengan mouse. */
public class Slider extends Actor
{
    private int trackW;
    private int value;

    public Slider(int trackW, int value)
    {
        this.trackW = trackW;
        this.value = value;
        redraw();
    }

    public int getValue()
    {
        return value;
    }

    public void act()
    {
        if (Greenfoot.mousePressed(this) || Greenfoot.mouseDragged(this))
        {
            MouseInfo mi = Greenfoot.getMouseInfo();
            if (mi != null)
            {
                int left = getX() - trackW / 2;
                int v = (int) Math.round((mi.getX() - left) * 100.0 / trackW);
                setValue(v);
            }
        }
    }

    private void setValue(int v)
    {
        v = Math.max(0, Math.min(100, v));
        if (v != value)
        {
            value = v;
            redraw();
        }
    }

    private void redraw()
    {
        int pad = 16;
        GreenfootImage img = new GreenfootImage(trackW + pad * 2, 36);
        img.drawImage(Gfx.img("slider_track", trackW, 12), pad, 12);
        int fillW = trackW * value / 100;
        if (fillW > 0) img.drawImage(Gfx.img("slider_fill", fillW, 12), pad, 12);
        img.drawImage(Gfx.img("slider_knob", 28, 28), pad + fillW - 14, 4);
        setImage(img);
    }
}
