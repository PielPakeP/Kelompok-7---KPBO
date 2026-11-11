import greenfoot.*;

/** ENDING: jembatan sudah diperbaiki + ringkasan hasil + tombol Main Lagi / Menu. */
public class EndingWorld extends BaseWorld
{
    private Button againBtn, menuBtn;

    public EndingWorld()
    {
        super();
        setBackground(Backgrounds.bridge(true));
        addObject(new ImageActor(Gfx.img("tamat", 420, 130)), 450, 95);
        addObject(new Label("Jembatan berhasil diperbaiki!", 28, Gfx.TEXT_LIGHT, true), 450, 175);
        for (int i = 0; i < 4; i++) GameData.materials[i] = true;
        addObject(new MaterialBar(), 450, 235);
        addObject(new Label("Total sisa waktu: " + GameData.totalSecondsLeft + " detik", 22, Gfx.TEXT_LIGHT, true), 450, 290);

        againBtn = new Button("btn_again", 220, 56);
        menuBtn = new Button("btn_menu_wide", 220, 56);
        addObject(againBtn, 340, 360);
        addObject(menuBtn, 560, 360);
        SoundManager.playMusic("bgm_menu.mp3");
    }

    public void act()
    {
        if (againBtn.clicked())
        {
            GameData.newGame();
            Greenfoot.setWorld(new MapWorld(1, true));
        }
        else if (menuBtn.clicked())
        {
            Greenfoot.setWorld(new MenuWorld());
        }
    }

}
