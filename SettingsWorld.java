import greenfoot.*;
import java.awt.Color;

/**
 * SETTINGS: Volume (slider), Music ON/OFF, Sound Effect ON/OFF, Volume Music, Volume SFX, Fullscreen.
 * Nilai disimpan di GameData sehingga berlaku di semua layar.
 */
public class SettingsWorld extends BaseWorld
{
    private Slider volSlider;
    private Toggle musicToggle;
    private Label volLbl;
    private Button backBtn;
    private int lastSig = -1;

    public SettingsWorld()
    {
        super();
        setBackground(Backgrounds.settings());   // panel + judul "Settings" sudah ada di gambar bg_settings

        int[] rowY = { 160, 225, 290, 355, 420, 485 };
        String[] names = { "Volume", "Music"};
        for (int i = 0; i < names.length; i++)
        {
            Label l = new Label(names[i], 24, Gfx.TEXT_DARK);
            addObject(l, 165 + l.getImage().getWidth() / 2, rowY[i]);
        }

        volSlider = new Slider(230, GameData.volume);
        musicToggle = new Toggle(GameData.musicOn);

        addObject(volSlider, 560, rowY[0]);
        addObject(musicToggle, 640, rowY[1]);

        volLbl = new Label(GameData.volume + "%", 20, Gfx.TEXT_DARK);

        addObject(volLbl, 725, rowY[0]);



        backBtn = new Button("btn_close", 46, 46);
        addObject(backBtn, 745, 72);

        SoundManager.playMusic("bgm_menu.mp3");
    }

    public void act()
    {
        GameData.volume = volSlider.getValue();
        GameData.musicOn = musicToggle.isOn();

        // update label & suara hanya kalau ada yang berubah
        int sig = GameData.volume * 1000000 + (GameData.musicOn ? 1 : 0) * 7;
        
        if (sig != lastSig)
        {
            lastSig = sig;
            volLbl.setText(GameData.volume + "%");
            SoundManager.applyVolume();
        }

        if (backBtn.clicked() || "escape".equals(Greenfoot.getKey()))
        {
            Greenfoot.setWorld(new MenuWorld());
        }
    }
}
