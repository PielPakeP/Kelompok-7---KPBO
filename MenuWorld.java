import greenfoot.*;
import java.awt.Color;

/** DASHBOARD: Start Game, How to Play, Settings, Exit (+ konfirmasi) dan tombol Information. */
public class MenuWorld extends BaseWorld
{
    private Button startBtn, howBtn, settingsBtn, exitBtn, infoBtn;
    private Button yesBtn, noBtn;
    private Popup confirm;

    public MenuWorld()
    {
        super();
        setBackground(Backgrounds.bridge(false));   // papan kayu & tulisan semangat sudah ada di gambar bg_menu

        // judul (gambar title)
        addObject(new ImageActor(Gfx.img("title", 520, 170)), 450, 115);

        startBtn = new Button("btn_start", 260, 58);
        howBtn = new Button("btn_howto", 260, 58);
        settingsBtn = new Button("btn_settings", 260, 58);
        exitBtn = new Button("btn_exit", 260, 58);
        infoBtn = new Button("btn_info", 52, 52);
        addObject(startBtn, 450, 270);
        addObject(howBtn, 450, 340);
        addObject(settingsBtn, 450, 410);
        addObject(exitBtn, 450, 480);
        addObject(infoBtn, 860, 555);

        SoundManager.playMusic("bgm_menu.mp3");
        prepare();
    }

    public void act()
    {
        if (confirm != null)
        {
            // dialog konfirmasi keluar sedang tampil
            if (yesBtn.clicked())
            {
                Greenfoot.stop(); // untuk aplikasi hasil export, boleh diganti System.exit(0);
            }
            else if (noBtn.clicked())
            {
                removeObject(confirm);
                removeObject(yesBtn);
                removeObject(noBtn);
                confirm = null;
            }
            return;
        }

        if (startBtn.clicked())
        {
            GameData.newGame();
            Greenfoot.setWorld(new MapWorld(1, true));
        }
        else if (howBtn.clicked())
        {
            Greenfoot.setWorld(new HowToPlayWorld());
        }
        else if (settingsBtn.clicked())
        {
            Greenfoot.setWorld(new SettingsWorld());
        }
        else if (infoBtn.clicked())
        {
            Greenfoot.setWorld(new InfoWorld());
        }
        else if (exitBtn.clicked())
        {
            confirm = new Popup("KELUAR?", "Apakah yakin mau keluar dari game?");
            addObject(confirm, 450, 300);
            yesBtn = new Button("btn_yes", 170, 50);
            noBtn = new Button("btn_no", 170, 50);
            addObject(yesBtn, 360, 395);
            addObject(noBtn, 540, 395);
        }
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
