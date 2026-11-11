import greenfoot.*;

/**
 * Bar timer. Gambar: timer_frame (340x50, sudah ada ikon jam),
 * timer_fill (isi bar) dan timer_fill_low (isi bar saat waktu hampir habis).
 */
public class TimerBar extends Actor
{
    private static final int W = 340, H = 50;
    private int lastKey = -1;

    public TimerBar()
    {
        update(1.0, 120);
    }

    /** frac = sisa waktu 0.0 - 1.0, secs = sisa detik. */
    public void update(double frac, int secs)
    {
        int barW = 230;
        int px = (int) (barW * frac);
        int key = px * 1000 + secs;
        if (key == lastKey) return;
        lastKey = key;

        GreenfootImage img = Gfx.img("timer_frame", W, H);
        if (px > 0) img.drawImage(Gfx.img(frac < 0.25 ? "timer_fill_low" : "timer_fill", px, 20), 56, 15);
        String s = (secs / 60) + ":" + (secs % 60 < 10 ? "0" : "") + (secs % 60);
        Gfx.drawCentered(img, s, 18, Gfx.TEXT_LIGHT, W - 36, H / 2);
        setImage(img);
    }
}
