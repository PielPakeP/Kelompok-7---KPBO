import greenfoot.*;

/**
 * Percakapan di dalam ruangan (guru + POPI).
 *   new TalkWorld(level, true)  = SEBELUM level : Dialogs.BEFORE -> lalu masuk GameWorld
 *   new TalkWorld(level)        = SESUDAH level : Dialogs.AFTER  -> lalu kembali ke peta (MapWorld) menuju ruangan berikutnya
 */
public class TalkWorld extends BaseWorld
{
    private final int level;
    private final boolean before;
    private final String[][] pages;
    private int page = 0;
    private DialogBox box;
    private DialogPortraits portraits;
    private Button skipBtn;

    /** Percakapan SESUDAH level selesai (dipanggil dari GameWorld). */
    public TalkWorld(int level)
    {
        this(level, false);
    }

    public TalkWorld(int level, boolean before)
    {
        super();
        this.level = level;
        this.before = before;
        this.pages = before ? Dialogs.BEFORE[level - 1] : Dialogs.AFTER[level - 1];
        setBackground(Backgrounds.classroom(level));
        addObject(new Label("Kelas " + LevelData.SUBJECT[level - 1], 22, Gfx.TEXT_LIGHT, true), 140, 40);
        addObject(new MaterialBar(), 790, 40);
        box = new DialogBox(840, 150);
        addObject(box, 450, 505);
        portraits = new DialogPortraits(this, 300, 140, 760, 430);   // POPI di kiri, guru di kanan
        skipBtn = new Button("btn_skip", 130, 44);
        addObject(skipBtn, 825, 100);
        showPage();
        SoundManager.playMusic("bgm_game.mp3");
    }

    private void showPage()
    {
        box.show(pages[page][0], pages[page][1], "klik / SPASI untuk lanjut");
        portraits.show(pages[page][0], Dialogs.exprOf(pages[page]));
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
        if (before)
        {
            // sebelum level -> main game
            Greenfoot.setWorld(new GameWorld(level));
        }
        else
        {
            // level n selesai -> tujuan berikutnya = tahap n+1 (tahap 5 = jembatan)
            Greenfoot.setWorld(new MapWorld(level + 1, false));
        }
    }
}
