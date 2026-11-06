import greenfoot.*;
import java.awt.Color;

/**
 * SETTINGS: Volume (slider), Music ON/OFF, Sound Effect ON/OFF, Volume Music, Volume SFX, Fullscreen.
 * Nilai disimpan di GameData sehingga berlaku di semua layar.
 */
public class SettingsWorld extends BaseWorld
{
    private Slider volSlider, musSlider, sfxSlider;
    private Toggle musicToggle, sfxToggle, fullToggle;
    private Label volLbl, musLbl, sfxLbl;
    private Button backBtn;
    private int lastSig = -1;

    public SettingsWorld()
    {
        super();
        setBackground(Backgrounds.settings());   // panel + judul "Settings" sudah ada di gambar bg_settings

        int[] rowY = { 160, 225, 290, 355, 420, 485 };
        String[] names = { "Volume", "Music", "Sound Effect", "Volume Music", "Volume SFX", "Fullscreen" };
        for (int i = 0; i < names.length; i++)
        {
            Label l = new Label(names[i], 24, Gfx.TEXT_DARK);
            addObject(l, 165 + l.getImage().getWidth() / 2, rowY[i]);
        }

        volSlider = new Slider(230, GameData.volume);
        musicToggle = new Toggle(GameData.musicOn);
        sfxToggle = new Toggle(GameData.sfxOn);
        musSlider = new Slider(230, GameData.musicVolume);
        sfxSlider = new Slider(230, GameData.sfxVolume);
        fullToggle = new Toggle(GameData.fullscreen);
        addObject(volSlider, 560, rowY[0]);
        addObject(musicToggle, 640, rowY[1]);
        addObject(sfxToggle, 640, rowY[2]);
        addObject(musSlider, 560, rowY[3]);
        addObject(sfxSlider, 560, rowY[4]);
        addObject(fullToggle, 640, rowY[5]);

        volLbl = new Label(GameData.volume + "%", 20, Gfx.TEXT_DARK);
        musLbl = new Label(GameData.musicVolume + "%", 20, Gfx.TEXT_DARK);
        sfxLbl = new Label(GameData.sfxVolume + "%", 20, Gfx.TEXT_DARK);
        addObject(volLbl, 725, rowY[0]);
        addObject(musLbl, 725, rowY[3]);
        addObject(sfxLbl, 725, rowY[4]);

        backBtn = new Button("btn_close", 46, 46);
        addObject(backBtn, 745, 72);

        SoundManager.playMusic("bgm_menu.mp3");
    }

    public void act()
    {
        GameData.volume = volSlider.getValue();
        GameData.musicVolume = musSlider.getValue();
        GameData.sfxVolume = sfxSlider.getValue();
        GameData.musicOn = musicToggle.isOn();
        GameData.sfxOn = sfxToggle.isOn();
        GameData.fullscreen = fullToggle.isOn();

        // update label & suara hanya kalau ada yang berubah
        int sig = GameData.volume * 1000000 + GameData.musicVolume * 1000 + GameData.sfxVolume
                  + (GameData.musicOn ? 1 : 0) * 7 + (GameData.sfxOn ? 1 : 0) * 13 + (GameData.fullscreen ? 1 : 0) * 29;
        if (sig != lastSig)
        {
            lastSig = sig;
            volLbl.setText(GameData.volume + "%");
            musLbl.setText(GameData.musicVolume + "%");
            sfxLbl.setText(GameData.sfxVolume + "%");
            SoundManager.applyVolume();
        }

        if (backBtn.clicked() || "escape".equals(Greenfoot.getKey()))
        {
            Greenfoot.setWorld(new MenuWorld());
        }
    }
}
