import greenfoot.*;

/**
 * STORY: cerita pembuka (ending=false) atau penutup (ending=true).
 * Klik / Spasi / Enter untuk lanjut. Tombol "Lewati" untuk melompat.
 */
public class StoryWorld extends BaseWorld
{
    private final boolean ending;
    private final String[][] pages;
    private int page = 0;
    private int repairPage = -1;
    private int teachersPage = -1;
    private boolean repaired = false;
    private boolean teachersShown = false;
    private DialogBox box;
    private DialogPortraits portraits;
    private Button skipBtn;

    public StoryWorld(boolean ending)
    {
        super();
        this.ending = ending;
        pages = ending ? Dialogs.ENDING : Dialogs.MAP_INTRO;
        setBackground(Backgrounds.bridge(false));   // jembatan rusak dulu; berganti utuh setelah CUE_REPAIR

        // cari halaman penanda di ending (background berganti, guru muncul)
        for (int i = 0; i < pages.length; i++)
        {
            if (pages[i][1].equals(Dialogs.CUE_REPAIR)) repairPage = i;
            if (pages[i][1].equals(Dialogs.CUE_TEACHERS)) teachersPage = i;
        }
        box = new DialogBox(840, 150);
        addObject(box, 450, 505);
        portraits = new DialogPortraits(this, 250, 110, 790, 430);
        skipBtn = new Button("btn_skip", 130, 44);
        addObject(skipBtn, 825, 40);
        showPage();
        SoundManager.playMusic("bgm_menu.mp3");
    }

    private void showPage()
    {
        // setelah halaman "[POPI mulai memperbaiki jembatan.]" -> jembatan tiba-tiba sudah utuh
        if (ending && !repaired && repairPage >= 0 && page > repairPage)
        {
            repaired = true;
            setBackground(Backgrounds.bridge(true));
        }
        // "[Semua guru datang.]" -> semua karakter muncul (POPI dan Bu Rita hanya tampil kalau gambarnya ada)
        if (ending && !teachersShown && page == teachersPage)
        {
            teachersShown = true;
            showCast();
        }
        box.show(pages[page][0], pages[page][1], "klik / SPASI untuk lanjut");
        // potret pembicara; setelah semua guru muncul di layar, potret tunggal tidak dipakai lagi
        if (teachersShown) portraits.hide();
        else portraits.show(pages[page][0], Dialogs.exprOf(pages[page]));
    }

    private void showCast()
    {
        // semua karakter muncul dengan ekspresi tertawa (cocok dengan "[Semua tertawa.]")
        String[] bases = { "popi", "teacher_1", "teacher_2", "teacher_3", "teacher_4", "bu_rita" };
        int[] xs = { 120, 260, 400, 540, 680, 810 };
        for (int i = 0; i < bases.length; i++)
        {
            if (Characters.find(bases[i], "tertawa") == null) continue;   // belum ada gambarnya -> dilewati
            Teacher t = new Teacher(bases[i], 170);
            t.setExpression("tertawa");
            addObject(t, xs[i], 335);
        }
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
