import greenfoot.*;

/**
 * Empat slot material di pojok atas. Gambar per slot (48x48):
 * material_1 ... material_4 (sudah didapat) dan material_1_off ... material_4_off (bayangan / belum didapat).
 */
public class MaterialBar extends Actor
{
    public MaterialBar()
    {
        refresh();
    }

    public void refresh()
    {
        int slot = 32, gap = 8;
        int w = 4 * slot + 3 * gap + 8, h = slot + 8;
        GreenfootImage img = new GreenfootImage(w, h);
        for (int i = 0; i < 4; i++)
        {
            String name = "material_" + (i + 1) + (GameData.materials[i] ? "" : "_off");
            img.drawImage(Gfx.img(name, slot, slot), 4 + i * (slot + gap), 4);
        }
        setImage(img);
    }
}
