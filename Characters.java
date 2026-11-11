import greenfoot.*;

/**
 * Daftar karakter + ekspresi, dan pencari gambar potret.
 *
 * Nama file gambar:  <karakter>_<ekspresi>.png   (atau .jpg)
 *   karakter : popi, bu_rita, teacher_1 (Pak Piel), teacher_2 (Pak Aaron), teacher_3 (Pak Raffael), teacher_4 (Pak Steven)
 *   ekspresi : netral, senang, tertawa, marah, sedih, kaget
 * Contoh: teacher_1_marah.png, popi_tertawa.png
 *
 * Kalau gambar ekspresi tertentu belum ada, dipakai  <karakter>_netral,  lalu  <karakter>  (mis. teacher_1.png).
 */
public class Characters
{
    public static final String[] EXPRESSIONS = { "netral", "senang", "tertawa", "marah", "sedih", "kaget" };

    // Kata-kata guru di balon bicara, sesuai ekspresinya (urutan sama dengan EXPRESSIONS)
    private static final String[][] PHRASES = {
        /* netral  */ { "Ayo, fokus ya!", "Kamu pasti bisa!", "Perhatikan baik-baik." },
        /* senang  */ { "Mantap!", "Benar!", "Bagus sekali!", "Nah itu dia!" },
        /* tertawa */ { "Hahaha, hebat!", "Wah, jago banget!", "Luar biasa!" },
        /* marah   */ { "Kok salah lagi?!", "Ayo yang serius!", "Konsentrasi, dong!" },
        /* sedih   */ { "Waktunya hampir habis...", "Yah... sayang sekali.", "Jangan menyerah ya..." },
        /* kaget   */ { "Eh, salah!", "Wah, hampir!", "Hah?!" }
    };

    /** Kata acak yang cocok dengan ekspresi tersebut. */
    public static String phrase(String expr)
    {
        String[] list = PHRASES[indexOf(expr)];
        return list[Greenfoot.getRandomNumber(list.length)];
    }

    private static int indexOf(String expr)
    {
        for (int i = 0; i < EXPRESSIONS.length; i++)
            if (EXPRESSIONS[i].equals(expr)) return i;
        return 0;
    }

    /** Nama dasar file gambar untuk pembicara. null = tidak punya gambar (narator, Pesan Moral, dll). */
    public static String baseOf(String speaker)
    {
        if (speaker == null) return null;
        if (speaker.startsWith("POPI")) return "popi";
        if (speaker.equals("Bu Rita")) return "bu_rita";
        if (speaker.equals("Pak Piel")) return "teacher_1";
        if (speaker.equals("Pak Aaron")) return "teacher_2";
        if (speaker.equals("Pak Raffael")) return "teacher_3";
        if (speaker.equals("Pak Steven")) return "teacher_4";
        return null;
    }

    /** POPI tampil di sisi kiri, karakter lain di sisi kanan. */
    public static boolean isLeft(String speaker)
    {
        return speaker != null && speaker.startsWith("POPI");
    }

    /** Cari gambar: base_ekspresi, lalu base_netral, lalu base. Return null kalau tidak ada satu pun. */
    public static GreenfootImage find(String base, String expr)
    {
        String[] tries = { base + "_" + expr, base + "_netral", base };
        for (String t : tries)
            if (Gfx.exists(t)) return Gfx.img(t);
        return null;
    }

    /** Potret pembicara dengan tinggi heightPx, atau null kalau pembicara tidak punya gambar. */
    public static GreenfootImage portrait(String speaker, String expr, int heightPx)
    {
        String base = baseOf(speaker);
        if (base == null) return null;
        GreenfootImage img = find(base, expr);
        if (img == null) return null;
        int w = Math.max(1, img.getWidth() * heightPx / img.getHeight());
        img.scale(w, heightPx);
        return img;
    }
}
