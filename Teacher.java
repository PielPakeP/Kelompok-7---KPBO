import greenfoot.*;

/**
 * Guru / NPC dengan ekspresi yang bisa berganti (setExpression).
 * Gambar: lihat Characters (mis. teacher_1_senang, teacher_1_marah, ... atau cukup teacher_1).
 */
public class Teacher extends Actor
{
    private final String base;
    private final int heightPx;
    private String expression = "";
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

    /** baseName = nama dasar gambar, mis. "teacher_1" atau "bu_rita". */
    public Teacher(String baseName, int heightPx)
    {
        this.base = baseName;
        this.heightPx = heightPx;
        applyExpression("netral");
    }

    /** Ganti ekspresi: netral, senang, tertawa, marah, sedih, kaget. */
    public void setExpression(String expr)
    {
        if (!expr.equals(expression)) applyExpression(expr);
    }

    private void applyExpression(String expr)
    {
        GreenfootImage img = Characters.find(base, expr);
        if (img == null)
        {
            if (!expression.isEmpty()) return;   // sudah punya gambar, biarkan
            img = Gfx.img(base);                 // pertama kali & tidak ada: tampilkan peringatan aset hilang
        }
        if (heightPx > 0)
        {
            int w = Math.max(1, img.getWidth() * heightPx / img.getHeight());
            img.scale(w, heightPx);
        }
        setImage(img);
        expression = expr;
    }

    public void act()
    {
        if (baseY < 0) baseY = getY();
        tick++;
        setLocation(getX(), baseY + (int) (Math.sin(tick * 0.08) * 3));
    }
}
