import greenfoot.*;

/**
 * Percakapan SETELAH level selesai: guru memberi material dan menunjukkan ruangan berikutnya.
 * Setelah itu kembali ke peta (MapWorld) dengan tujuan baru.
 */
public class TalkWorld extends BaseWorld
{
    private final int level;
    private final String[] pages;
    private int page = 0;
    private DialogBox box;
    private Button skipBtn;

    public TalkWorld(int level)
    {
        super();
        this.level = level;
        this.pages = Dialogs.AFTER[level - 1];
        setBackground(Backgrounds.classroom(level));
        addObject(new Teacher(level, 320), 450, 285);
        addObject(new Label("Kelas " + LevelData.SUBJECT[level - 1], 22, Gfx.TEXT_LIGHT, true), 140, 40);
        addObject(new MaterialBar(), 790, 40);
        box = new DialogBox(840, 150);
        addObject(box, 450, 505);
        skipBtn = new Button("btn_skip", 130, 44);
        addObject(skipBtn, 825, 100);
        showPage();
        SoundManager.playMusic("bgm_game.mp3");
    }

    private void showPage()
    {
        String speaker = LevelData.TEACHER[level - 1] + " (Guru " + LevelData.SUBJECT[level - 1] + ")";
        box.show(speaker, pages[page], "klik / SPASI untuk lanjut");
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
        // level n selesai -> tujuan berikutnya = tahap n+1 (tahap 5 = jembatan)
        Greenfoot.setWorld(new MapWorld(level + 1, false));
    }
}
