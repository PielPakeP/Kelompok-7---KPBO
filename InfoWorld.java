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
          + "Game memori kartu edukasi - versi 1.0\n\n"
          + "Developer:\n"
          + LevelData.TEACHER[0] + ", " + LevelData.TEACHER[1] + ", " + LevelData.TEACHER[2] + ", " + LevelData.TEACHER[3] + "\n\n"
          + "Credits:\n"
          + "Dibuat dengan Greenfoot\n"
          + "Musik & efek suara: tiktok/youtube\n"
          + "Ilustrasi & karakter: Foto kita ber4 ";
        Gfx.drawParagraph(bg, info, 22, Gfx.TEXT_DARK, 450, 130, 560, true);
        setBackground(bg);

        backBtn = new Button("btn_back", 180, 50);
        addObject(backBtn, 450, 510);
        SoundManager.playMusic("bgm_menu.mp3");
    }

    public void act()
    {
        if (backBtn.clicked() || "escape".equals(Greenfoot.getKey()))
        {
            Greenfoot.setWorld(new MenuWorld());
        }
    }
}
