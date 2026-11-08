import greenfoot.*;

/** Kotak dialog (cerita / petunjuk). Gambar: dialog_box (840 x 150, akan di-scale sesuai ukuran). */
public class DialogBox extends Actor
{
    private final int w, h;

    public DialogBox(int w, int h)
    {
        this.w = w;
        this.h = h;
    }

    /** speaker boleh "" (tanpa nama). hint = tulisan kecil di kanan bawah (boleh ""). */
    public void show(String speaker, String text, String hint)
    {
        GreenfootImage img = Gfx.img(w < 800 ? "dialog_box_small" : "dialog_box", w, h);
        int top = 14;
        if (speaker.length() > 0)
        {
            img.drawImage(Gfx.text(speaker, 20, Gfx.TEXT_ACCENT), 28, top);
            top += 32;
        }
        Gfx.drawParagraph(img, text, 22, Gfx.TEXT_LIGHT, 28, top + 4, w - 56, false);
        if (hint.length() > 0)
        {
            GreenfootImage t = Gfx.text(hint, 14, Gfx.TEXT_LIGHT);
            img.drawImage(t, w - t.getWidth() - 24, h - t.getHeight() - 8);
        }
        setImage(img);
    }
}
