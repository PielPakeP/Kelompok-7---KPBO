import greenfoot.*;
import java.util.*;

/**
 * HOW TO PLAY: animasi demo papan 2x2 yang berjalan otomatis (berulang).
 * Setiap kartu yang dibalik disertai teks bantuan/instruksi.
 * Timeline memakai waktu nyata (milidetik) agar tidak tergantung kecepatan Greenfoot.
 */
public class HowToPlayWorld extends BaseWorld
{
    // waktu (ms) tiap langkah demo
    private static final long[] T = { 0, 3500, 6500, 8500, 10500, 12500, 15500, 20000 };
    private static final String[] MSG = {
        "1. Di awal level, semua kartu terbuka sebentar. Hafalkan posisinya!",
        "2. Kartu menutup dan timer 2 menit mulai berjalan.",
        "3. Klik sebuah kartu untuk membukanya.",
        "4. Klik kartu kedua. Kalau berbeda, keduanya akan menutup lagi.",
        "Tidak cocok! Ingat posisinya, lalu coba lagi.",
        "5. Cocok! Pasangan yang benar akan tetap terbuka.",
        "6. Temukan semua pasangan sebelum waktu habis untuk menyelesaikan level!",
        ""
    };

    private final List<Card> cards = new ArrayList<Card>();
    private DialogBox box;
    private Button backBtn;
    private long t0;
    private int step;

    public HowToPlayWorld()
    {
        super();
        setBackground(Backgrounds.classroom(1));
        addObject(new Label("How to Play", 30, Gfx.TEXT_LIGHT, true), 450, 42);
        box = new DialogBox(600, 120);
        addObject(box, 450, 525);
        backBtn = new Button("btn_back", 150, 46);
        addObject(backBtn, 90, 555);
        SoundManager.playMusic("bgm_menu.mp3");
        restartDemo();
    }

    private void restartDemo()
    {
        for (Card c : cards) removeObject(c);
        cards.clear();
        // pasangan 0: "2 + 2" & "4"  |  pasangan 1: "H2O" & "Air"
        cards.add(new Card(0, "2 + 2", 130, 150, 1));
        cards.add(new Card(1, "Air", 130, 150, 1));
        cards.add(new Card(0, "4", 130, 150, 1));
        cards.add(new Card(1, "H2O", 130, 150, 1));
        int[] xs = { 370, 530, 370, 530 };
        int[] ys = { 190, 190, 360, 360 };
        for (int i = 0; i < 4; i++) addObject(cards.get(i), xs[i], ys[i]);
        t0 = System.currentTimeMillis();
        step = 0;
    }

    private long pausedAt = 0;

    public void stopped()
    {
        super.stopped();
        pausedAt = System.currentTimeMillis();
    }

    public void started()
    {
        super.started();
        if (pausedAt > 0)
        {
            t0 += System.currentTimeMillis() - pausedAt;
            pausedAt = 0;
        }
    }

    public void act()
    {
        if (backBtn.clicked() || "escape".equals(Greenfoot.getKey()))
        {
            Greenfoot.setWorld(new MenuWorld());
            return;
        }
        long e = System.currentTimeMillis() - t0;
        while (step < T.length && e >= T[step])
        {
            int s = step;
            step++;
            runStep(s);
            if (s == T.length - 1) break; // langkah terakhir = ulang demo dari awal
        }
    }

    private void runStep(int s)
    {
        if (MSG[s].length() > 0) box.show("Petunjuk", MSG[s], "");
        switch (s)
        {
            case 0:
                for (Card c : cards) c.flip(true);
                break;
            case 1:
                for (Card c : cards) c.flip(false);
                break;
            case 2:
                cards.get(0).flip(true);
                SoundManager.sfx("flip.wav");
                break;
            case 3:
                cards.get(1).flip(true);
                SoundManager.sfx("flip.wav");
                break;
            case 4:
                cards.get(0).flip(false);
                cards.get(1).flip(false);
                SoundManager.sfx("wrong.wav");
                break;
            case 5:
                cards.get(0).flip(true);
                cards.get(2).flip(true);
                cards.get(0).markMatched();
                cards.get(2).markMatched();
                SoundManager.sfx("correct.wav");
                break;
            case 6:
                cards.get(1).flip(true);
                cards.get(3).flip(true);
                cards.get(1).markMatched();
                cards.get(3).markMatched();
                SoundManager.sfx("correct.wav");
                break;
            default:
                restartDemo();
                break;
        }
    }
}
