import greenfoot.*;

/** Balon bicara guru. Gambar: bubble (170 x 100, ekor di bawah; badan balon di bagian atas ~82 px). */
public class SpeechBubble extends Actor
{
    private static final int W = 170, H = 100;
    private String text = null;

    public SpeechBubble()
    {
        setText("...");
    }

    public void setText(String t)
    {
        if (t.equals(text)) return;
        text = t;
        GreenfootImage img = Gfx.img("bubble", W, H);
        Gfx.drawFitted(img, t, 12, 8, W - 24, 66, Gfx.TEXT_DARK, 22, 11);
        setImage(img);
    }
}
