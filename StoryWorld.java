import greenfoot.*;

/**
 * STORY: cerita pembuka (ending=false) atau penutup (ending=true).
 * Klik / Spasi / Enter untuk lanjut. Tombol "Lewati" untuk melompat.
 */
public class StoryWorld extends BaseWorld
{
    private static final String[][] INTRO = {
        { "Narator", "Di sebuah desa kecil, berdiri sebuah SMP yang dikelilingi sungai yang indah." },
        { "Narator", "Satu-satunya jembatan menuju sekolah kini rusak. Papannya lapuk dan talinya putus." },
        { "Kamu", "Aku murid baru di sini... tapi aku tidak mau teman-temanku susah menyeberang setiap hari!" },
        { "Narator", "Para guru menyimpan material untuk memperbaiki jembatan. Material itu hanya diberikan kepada murid yang berhasil melewati tantangan di kelas mereka." },
        { "Papan Sekolah", "Jembatan yang rusak bukan akhir, tapi awal dari perubahan." },
        { "Narator", "Temukan semua pasangan kartu sebelum waktu 2 menit habis. Kumpulkan 4 material: Tali, Papan, Paku, dan Palu!" }
    };

    private static final String[][] ENDING = {
        { "Narator", "Tali, papan, paku, dan palu... semua material sudah terkumpul!" },
        { "Narator", "Para guru dan teman-teman bergotong royong memperbaiki jembatan." },
        { "Kamu", "Bersama, kita bisa memperbaiki jembatan ini!" },
        { "Narator", "Kini jembatan itu berdiri kokoh. Perubahan baik selalu dimulai dari satu langkah kecil." }
    };

    private final boolean ending;
    private final String[][] pages;
    private int page = 0;
    private DialogBox box;
    private Button skipBtn;

    public StoryWorld(boolean ending)
    {
        super();
        this.ending = ending;
        pages = ending ? ENDING : INTRO;
        setBackground(Backgrounds.bridge(ending));
        box = new DialogBox(840, 150);
        addObject(box, 450, 505);
        skipBtn = new Button("btn_skip", 130, 44);
        addObject(skipBtn, 825, 40);
        showPage();
        SoundManager.playMusic("bgm_menu.mp3");
    }

    private void showPage()
    {
        box.show(pages[page][0], pages[page][1], "klik untuk lanjut");
    }

    public void act()
    {
        if (skipBtn.clicked())
        {
            finish();
            return;
        }
        String k = Greenfoot.getKey();
        boolean key = (k != null && (k.equals("space") || k.equals("enter")));
        if (key || Greenfoot.mouseClicked(null))
        {
            page++;
            if (page >= pages.length) finish();
            else
            {
                SoundManager.sfx("click.wav");
                showPage();
            }
        }
    }

    private void finish()
    {
        if (ending) Greenfoot.setWorld(new EndingWorld());
        else Greenfoot.setWorld(new MapWorld(1, true));
    }
}
