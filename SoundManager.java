import greenfoot.*;

/**
 * Pengatur musik & efek suara.
 * Aman dipakai walau file suara belum ada: kalau file tidak ketemu, game tetap jalan (tanpa bunyi).
 * Taruh file suara di folder  sounds/  :
 *   bgm_menu.mp3, bgm_game.mp3, click.wav, flip.wav, correct.wav, wrong.wav, win.wav, lose.wav
 */
public class SoundManager
{
    private static GreenfootSound music;
    private static String musicFile = "";

    /** Mainkan musik latar (loop). Kalau sudah memainkan file yang sama, tidak diulang dari awal. */
    public static void playMusic(String file)
    {
        if (file.equals(musicFile) && music != null)
        {
            applyVolume();
            return;
        }
        stopMusic();
        musicFile = file;
        try
        {
            music = new GreenfootSound(file);
        }
        catch (Exception e)
        {
            music = null; // file tidak ada -> diam saja
            return;
        }
        applyVolume();
    }

    public static void stopMusic()
    {
        if (music != null)
        {
            try { music.stop(); } catch (Exception e) { }
        }
        music = null;
        musicFile = "";
    }

    /** Terapkan volume & status ON/OFF musik dari GameData. */
    public static void applyVolume()
    {
        if (music == null) return;
        try
        {
            music.setVolume(GameData.volume * GameData.musicVolume / 100);
            if (GameData.musicOn)
            {
                if (!music.isPlaying()) music.playLoop();
            }
            else
            {
                music.pause();
            }
        }
        catch (Exception e) { }
    }

    public static void pause()
    {
        if (music != null) { try { music.pause(); } catch (Exception e) { } }
    }

    public static void resume()
    {
        applyVolume();
    }

    /** Efek suara singkat. */
    public static void sfx(String file)
    {
        if (!GameData.sfxOn) return;
        try
        {
            GreenfootSound s = new GreenfootSound(file);
            s.setVolume(GameData.volume * GameData.sfxVolume / 100);
            s.play();
        }
        catch (Exception e) { }
    }
}
