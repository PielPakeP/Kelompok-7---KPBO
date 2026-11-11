import greenfoot.*;

/**
 * Menampilkan potret pembicara di samping kotak dialog:
 * POPI di sisi kiri, karakter lain (Bu Rita, para guru) di sisi kanan.
 * Hanya pembicara yang sedang bicara yang tampil. Narator / catatan [..] = tidak ada potret.
 * Posisi: bagian bawah potret berhenti tepat di atas kotak dialog (bottomY).
 */
public class DialogPortraits
{
    private final World world;
    private final int heightPx, leftX, rightX, bottomY;
    private final Portrait left = new Portrait();
    private final Portrait right = new Portrait();

    public DialogPortraits(World world, int heightPx, int leftX, int rightX, int bottomY)
    {
        this.world = world;
        this.heightPx = heightPx;
        this.leftX = leftX;
        this.rightX = rightX;
        this.bottomY = bottomY;
    }

    public void show(String speaker, String expression)
    {
        hide();
        GreenfootImage img = Characters.portrait(speaker, expression, heightPx);
        if (img == null) return;
        boolean isLeft = Characters.isLeft(speaker);
        Portrait p = isLeft ? left : right;
        p.setImage(img);
        world.addObject(p, isLeft ? leftX : rightX, bottomY - img.getHeight() / 2);
    }

    public void hide()
    {
        if (left.getWorld() != null) world.removeObject(left);
        if (right.getWorld() != null) world.removeObject(right);
    }
}
