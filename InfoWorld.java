import greenfoot.*;
import java.awt.Color;

/** INFORMATION / ABOUT: developer, credits, dll. Edit teksnya sesuai kelompokmu. */
public class InfoWorld extends BaseWorld
{
    private Button backBtn;

    public InfoWorld()
    {
        super();
        GreenfootImage bg = Backgrounds.info();   // panel + judul "Information" sudah ada di gambar bg_info
        String info =
            "SEKOLAH JEMBATAN\n"
          + "Game Memori Kartu PPOPIK!!\n\n"
          + "Developer:\n"
          + "Piel, Aaron, Steven dan Raffael\n\n"
          + "Credits:\n"
          + "Dibuat dengan Hati\n"
          + "Musik & efek suara: suara eren nyanyi\n"
          + "Ilustrasi & karakter: rafael yang gambar";
        Gfx.drawParagraph(bg, info, 22, Gfx.TEXT_DARK, 450, 130, 560, true);
        setBackground(bg);

        backBtn = new Button("btn_back", 180, 50);
        addObject(backBtn, 450, 510);
        SoundManager.playMusic("bgm_menu.mp3");
    }

    public void act()
    {
        if (backBtn.clicked())
        {
            Greenfoot.setWorld(new MenuWorld());
            SoundManager.sfx("click.mp3");
        }
    }
}
