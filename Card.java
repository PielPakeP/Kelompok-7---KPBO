import greenfoot.*;

/**
 * Satu kartu memori. Punya animasi membalik (flip) dan status: tertutup / terbuka / cocok.
 * Dua kartu dengan pairId yang sama = satu pasangan.
 */
public class Card extends Actor
{
    private static final int FLIP_FRAMES = 10;

    private final int pairId;
    private final String text;
    private final int w, h;
    private final int level;

    private boolean faceUp = false;
    private boolean matched = false;
    private int flipFrame = -1;     // -1 = tidak sedang animasi

    private GreenfootImage front;
    private GreenfootImage back;

    public Card(int pairId, String text, int w, int h, int level)
    {
        this.pairId = pairId;
        this.text = text;
        this.w = w;
        this.h = h;
        this.level = level;
        front = makeFront(false);
        back = makeBack();
        setImage(back);
    }

    public int getPairId() { return pairId; }
    public boolean isFaceUp() { return faceUp; }
    public boolean isMatched() { return matched; }

    /** Balik kartu: true = tampilkan isi, false = tutup. */
    public void flip(boolean up)
    {
        if (faceUp == up) return;
        faceUp = up;
        flipFrame = 0;
    }

    /** Tandai kartu sebagai pasangan yang benar (border hijau). */
    public void markMatched()
    {
        matched = true;
        front = makeFront(true);
        if (flipFrame < 0) setImage(front);
    }

    public void act()
    {
        if (flipFrame < 0) return;
        flipFrame++;
        int half = FLIP_FRAMES / 2;
        if (flipFrame >= FLIP_FRAMES)
        {
            flipFrame = -1;
            setImage(faceUp ? front : back);
            return;
        }
        boolean showFront = (flipFrame < half) ? !faceUp : faceUp;
        double scale = (flipFrame < half) ? 1.0 - (double) flipFrame / half : (double) (flipFrame - half) / half;
        GreenfootImage img = new GreenfootImage(showFront ? front : back);
        img.scale(Math.max(3, (int) (w * scale)), h);
        setImage(img);
    }

    // ---------- gambar kartu (dari file gambar) ----------
    private GreenfootImage makeFront(boolean ok)
    {
        GreenfootImage img = Gfx.img(ok ? "card_front_ok" : "card_front", w, h);
        Gfx.drawFitted(img, text, 8, 8, w - 16, h - 16, Gfx.TEXT_DARK, 22, 9);
        return img;
    }

    private GreenfootImage makeBack()
    {
        return Gfx.img("card_back_" + level, w, h);
    }
}
