import greenfoot.*;

/**
 * Semua background diambil dari gambar (ukuran 900 x 600):
 *   bg_menu, bg_settings, bg_info, bg_ending, bg_level1 ... bg_level4, map
 */
public class Backgrounds
{
    public static final int W = 900;
    public static final int H = 600;

    /** Pemandangan sekolah + jembatan: rusak (menu / cerita) atau sudah diperbaiki (ending). */
    public static GreenfootImage bridge(boolean repaired)
    {
        return Gfx.img(repaired ? "bg_ending" : "bg_menu", W, H);
    }

    public static GreenfootImage settings() { return Gfx.img("bg_settings", W, H); }
    public static GreenfootImage info() { return Gfx.img("bg_info", W, H); }
    public static GreenfootImage map() { return Gfx.img("map", W, H); }

    /** Kelas tiap level (1-4). */
    public static GreenfootImage classroom(int level)
    {
        return Gfx.img("bg_level" + level, W, H);
    }
}
