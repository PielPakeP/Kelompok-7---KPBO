import greenfoot.*;
import java.awt.Color;

/** Teks di layar. panel = true -> teks diletakkan di atas gambar label_bg. */
public class Label extends Actor
{
    private String text;
    private int size;
    private Color fg;
    private boolean panel;

    public Label(String text, int size, Color fg)
    {
        this(text, size, fg, false);
    }

    public Label(String text, int size, Color fg, boolean panel)
    {
        this.text = text;
        this.size = size;
        this.fg = fg;
        this.panel = panel;
        redraw();
    }

    public void setText(String t)
    {
        if (!t.equals(text))
        {
            text = t;
            redraw();
        }
    }

    private void redraw()
    {
        GreenfootImage t = Gfx.text(text, size, fg);
        if (!panel)
        {
            setImage(t);
            return;
        }
        int w = t.getWidth() + 28;
        int h = t.getHeight() + 14;
        GreenfootImage img = Gfx.img("label_bg", w, h);
        img.drawImage(t, 14, 7);
        setImage(img);
    }
}
