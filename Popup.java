import greenfoot.*;

/**
 * Jendela popup layar penuh. Gambar: popup (480 x 300, panel di tengah layar).
 * Opsional: popup_dim (900 x 600, .png hitam transparan) untuk meredupkan layar di belakang.
 * Tombol ditambahkan oleh World di atasnya.
 */
public class Popup extends Actor
{
    public Popup(String title, String message)
    {
        GreenfootImage img = new GreenfootImage(900, 600);
        if (Gfx.exists("popup_dim")) img.drawImage(Gfx.img("popup_dim", 900, 600), 0, 0);
        img.drawImage(Gfx.img("popup", 480, 300), 210, 150);
        Gfx.drawCentered(img, title, 36, Gfx.TEXT_ACCENT, 450, 195);
        Gfx.drawParagraph(img, message, 22, Gfx.TEXT_LIGHT, 450, 245, 420, true);
        setImage(img);
    }
}
