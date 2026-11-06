import greenfoot.*;
import java.awt.Color;
import java.util.*;

/**
 * Pemuat gambar + fungsi bantu teks.
 *
 * SEMUA tampilan game (background, tombol, kartu, karakter, bar, popup) diambil dari file gambar
 * di folder  images/  . Cukup tulis nama file TANPA ekstensi; game mencari  .jpg  lalu  .png
 * (pakai .png untuk gambar yang butuh bagian transparan, misalnya karakter).
 * Kalau file tidak ditemukan, muncul gambar kosong dan pesan [ASET HILANG] di jendela Greenfoot.
 *
 * Teks (dialog, angka timer, isi kartu) tetap ditulis lewat kode karena isinya berubah-ubah.
 * Warna teks hanya diatur di 3 konstanta di bawah ini.
 */
public class Gfx
{
    public static greenfoot.Color toGreenfootColor(Color c)
    {
        return new greenfoot.Color(
            c.getRed(),
            c.getGreen(),
            c.getBlue(),
            c.getAlpha()
        );
    }
    
    // ===== satu-satunya tempat pengaturan warna teks =====
    public static final Color TEXT_LIGHT = Color.WHITE;
    public static final Color TEXT_DARK = new Color(20, 30, 55);
    public static final Color TEXT_ACCENT = new Color(247, 190, 50);

    // ---------- memuat gambar ----------
    private static final String[] EXT = { ".jpg", ".png", ".jpeg" };
    private static final Map<String, GreenfootImage> cache = new HashMap<String, GreenfootImage>();
    private static final Set<String> warned = new HashSet<String>();

    private static GreenfootImage load(String name)
    {
        if (cache.containsKey(name)) return cache.get(name);
        GreenfootImage img = null;
        for (String e : EXT)
        {
            try
            {
                img = new GreenfootImage(name + e);
                break;
            }
            catch (Exception ex) { }
        }
        cache.put(name, img);
        return img;
    }

    /** true jika file images/<name>.jpg (atau .png) ada. */
    public static boolean exists(String name)
    {
        return load(name) != null;
    }

    /** Gambar dengan ukuran aslinya (salinan baru, aman untuk dicoret-coret). */
    public static GreenfootImage img(String name)
    {
        GreenfootImage b = load(name);
        if (b == null)
        {
            if (warned.add(name)) System.out.println("[ASET HILANG] images/" + name + ".jpg (atau .png)");
            return new GreenfootImage(40, 40);
        }
        return new GreenfootImage(b);
    }

    /** Gambar yang sudah diubah ke ukuran w x h. */
    public static GreenfootImage img(String name, int w, int h)
    {
        GreenfootImage i = img(name);
        i.scale(w, h);
        return i;
    }

    // ---------- teks ----------
    public static GreenfootImage text(String s, int size, Color c)
    {
        if (s == null || s.length() == 0) {
            s = " ";
        }
    
        return new GreenfootImage(
            s,
            size,
            toGreenfootColor(c),
            null
        );
    }

    public static void drawCentered(GreenfootImage g, String s, int size, Color c, int cx, int cy)
    {
        GreenfootImage t = text(s, size, c);
        g.drawImage(t, cx - t.getWidth() / 2, cy - t.getHeight() / 2);
    }

    /** Pecah teks menjadi baris-baris agar muat dalam lebar maxW. Mendukung "\n". */
    public static List<String> wrap(String s, int size, int maxW)
    {
        List<String> lines = new ArrayList<String>();
        for (String para : s.split("\n"))
        {
            String cur = "";
            for (String word : para.split(" "))
            {
                String cand = cur.length() == 0 ? word : cur + " " + word;
                if (cur.length() == 0 || text(cand, size, Color.BLACK).getWidth() <= maxW)
                    cur = cand;
                else
                {
                    lines.add(cur);
                    cur = word;
                }
            }
            lines.add(cur);
        }
        return lines;
    }

    /** Gambar paragraf. y = sisi atas. Kalau center=true, x = titik tengah; kalau tidak, x = sisi kiri. Return tinggi total. */
    public static int drawParagraph(GreenfootImage g, String s, int size, Color c, int x, int y, int maxW, boolean center)
    {
        int cy = y;
        for (String line : wrap(s, size, maxW))
        {
            GreenfootImage t = text(line, size, c);
            int tx = center ? x - t.getWidth() / 2 : x;
            g.drawImage(t, tx, cy);
            cy += t.getHeight();
        }
        return cy - y;
    }

    /** Teks otomatis mengecil sampai muat di kotak (dipakai untuk isi kartu & balon bicara). */
    public static void drawFitted(GreenfootImage g, String s, int bx, int by, int bw, int bh, Color c, int maxSize, int minSize)
    {
        int size = maxSize;
        while (size >= minSize)
        {
            int total = 0;
            boolean ok = true;
            for (String l : wrap(s, size, bw))
            {
                GreenfootImage t = text(l, size, c);
                total += t.getHeight();
                if (t.getWidth() > bw) ok = false;
            }
            if (ok && total <= bh) break;
            size--;
        }
        if (size < minSize) size = minSize;
        List<String> lines = wrap(s, size, bw);
        int total = 0;
        for (String l : lines) total += text(l, size, c).getHeight();
        int cy = by + (bh - total) / 2;
        for (String l : lines)
        {
            GreenfootImage t = text(l, size, c);
            g.drawImage(t, bx + (bw - t.getWidth()) / 2, cy);
            cy += t.getHeight();
        }
    }
}
