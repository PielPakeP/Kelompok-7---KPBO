import greenfoot.*;
import java.util.*;

/**
 * GAME: papan kartu memori satu level.
 * Alur: PREVIEW (kartu terbuka sebentar) -> PLAYING (timer 2 menit) -> WON / LOST (popup).
 * Menang -> TalkWorld (percakapan guru) -> MapWorld (jalan ke ruangan berikutnya).
 * Jumlah kartu: level 1 = 8, lalu tiap level +2 pasangan (12, 16, 20).
 */
public class GameWorld extends BaseWorld
{
    private static final int PREVIEW = 0, PLAYING = 1, WON = 2, LOST = 3;
    private static final long TIME_LIMIT_MS = 120000;   // timer 2 menit
    private static final int CHECK_DELAY = 30;          // jeda (frame) sebelum 2 kartu dicek

    private final int level;
    private final long previewMs;
    private final List<Card> cards = new ArrayList<Card>();
    private Card first = null, second = null;
    private int checkTimer = 0;
    private int pairsFound = 0;
    private int totalPairs = 0;
    private int state = PREVIEW;
    private long phaseStart;
    private long startTime;

    private TimerBar timerBar;
    private MaterialBar materialBar;
    private SpeechBubble bubble;
    private Teacher teacher;
    private int streakOk = 0, streakBad = 0;   // beruntun benar / salah
    private boolean lowWarned = false;        // sudah menunjukkan ekspresi sedih saat waktu hampir habis
    private Label pairLabel;
    private Button nextBtn, retryBtn, menuBtn;

    public GameWorld(int level)
    {
        super();
        this.level = level;
        this.previewMs = 5000 + level * 2000;
        GameData.currentLevel = level;

        setBackground(Backgrounds.classroom(level));
        buildHud();
        buildBoard();

        phaseStart = System.currentTimeMillis();
        for (Card c : cards) c.flip(true);   // semua kartu terbuka dulu
        bubble.setText(LevelData.INTRO[level - 1]);
        SoundManager.playMusic("bgm_game.mp3");
    }

    // ---------- membangun tampilan ----------
    private void buildHud()
    {
        timerBar = new TimerBar();
        addObject(timerBar, 195, 40);

        String banner = "Kelas " + LevelData.SUBJECT[level - 1] + " - Level " + level + "/" + LevelData.MAX_LEVEL;
        addObject(new Label(banner, 22, Gfx.TEXT_LIGHT, true), 520, 38);

        pairLabel = new Label("Pasangan 0/0", 16, Gfx.TEXT_LIGHT, true);
        addObject(pairLabel, 520, 72);

        materialBar = new MaterialBar();
        addObject(materialBar, 790, 40);

        teacher = new Teacher(level);
        addObject(teacher, 815, 470);
        bubble = new SpeechBubble();
        addObject(bubble, 805, 320);
    }

    private void buildBoard()
    {
        int cols = LevelData.COLS[level - 1];
        int rows = LevelData.ROWS[level - 1];
        totalPairs = cols * rows / 2;
        pairLabel.setText("Pasangan 0/" + totalPairs);

        int gap = 12, areaX = 30, areaY = 95, areaW = 690, areaH = 475;
        int cw = Math.min(125, (areaW - (cols - 1) * gap) / cols);
        int ch = Math.min(150, (areaH - (rows - 1) * gap) / rows);

        List<Card> list = new ArrayList<Card>();
        for (int i = 0; i < totalPairs; i++)
        {
            String[] p = LevelData.PAIRS[level - 1][i];
            list.add(new Card(i, p[0], cw, ch, level, 'a'));
            list.add(new Card(i, p[1], cw, ch, level, 'b'));
        }
        Collections.shuffle(list);

        int totalW = cols * cw + (cols - 1) * gap;
        int totalH = rows * ch + (rows - 1) * gap;
        int x0 = areaX + (areaW - totalW) / 2 + cw / 2;
        int y0 = areaY + (areaH - totalH) / 2 + ch / 2;
        for (int i = 0; i < list.size(); i++)
        {
            Card c = list.get(i);
            addObject(c, x0 + (i % cols) * (cw + gap), y0 + (i / cols) * (ch + gap));
            cards.add(c);
        }
    }

    // ---------- pause: waktu tidak ikut berjalan saat tombol Pause ditekan ----------
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
            long d = System.currentTimeMillis() - pausedAt;
            startTime += d;
            phaseStart += d;
            pausedAt = 0;
        }
    }

    // ---------- loop utama ----------
    public void act()
    {
        long now = System.currentTimeMillis();
        switch (state)
        {
            case PREVIEW: doPreview(now); break;
            case PLAYING: doPlaying(now); break;
            case WON:
                if (nextBtn.clicked()) goNext();
                else if (menuBtn.clicked()) Greenfoot.setWorld(new MenuWorld());
                break;
            case LOST:
                if (retryBtn.clicked()) Greenfoot.setWorld(new GameWorld(level));
                else if (menuBtn.clicked()) Greenfoot.setWorld(new MenuWorld());
                break;
        }
    }

    private void doPreview(long now)
    {
        long left = previewMs - (now - phaseStart);
        if (left > 0)
        {
            bubble.setText("Hafalkan! " + (left / 1000 + 1));
        }
        else
        {
            for (Card c : cards) c.flip(false);
            state = PLAYING;
            startTime = now;
            react("netral", "Mulai!");
        }
    }

    private void doPlaying(long now)
    {
        long left = TIME_LIMIT_MS - (now - startTime);
        if (left < 0) left = 0;
        timerBar.update(left / (double) TIME_LIMIT_MS, (int) Math.ceil(left / 1000.0));

        // waktu tinggal 25% -> guru sedih
        if (!lowWarned && left > 0 && left < TIME_LIMIT_MS / 4)
        {
            lowWarned = true;
            react("sedih", Characters.phrase("sedih"));
        }

        if (second != null)
        {
            checkTimer--;
            if (checkTimer <= 0) resolvePair();
        }
        else
        {
            for (Card c : cards)
            {
                if (Greenfoot.mouseClicked(c))
                {
                    onCardClicked(c);
                    break;
                }
            }
        }

        if (pairsFound == totalPairs) win(left);
        else if (left == 0) lose();
    }

    private void onCardClicked(Card c)
    {
        if (c.isFaceUp() || c.isMatched()) return;
        c.flip(true);
        if (first == null)
        {
            first = c;
        }
        else
        {
            second = c;
            checkTimer = CHECK_DELAY;   // tunggu sebentar supaya pemain sempat melihat kartu kedua
        }
    }

    private void resolvePair()
    {
        if (first.getPairId() == second.getPairId())
        {
            first.markMatched();
            second.markMatched();
            pairsFound++;
            streakOk++;
            streakBad = 0;
            String e = (streakOk >= 2) ? "senang" : "netral";   // 2 benar beruntun -> tertawa
            react(e, Characters.phrase(e));
        }
        else
        {
            first.flip(false);
            second.flip(false);
            streakBad++;
            streakOk = 0;
            String e = (streakBad >= 2) ? "marah" : "kaget";     // 2 salah beruntun -> marah
            react(e, Characters.phrase(e));
        }
        first = null;
        second = null;
        pairLabel.setText("Pasangan " + pairsFound + "/" + totalPairs);
    }

    /** Guru berekspresi + balon bicara dengan kata yang sesuai. */
    private void react(String expression, String words)
    {
        teacher.setExpression(expression);
        bubble.setText(words);
    }

    // ---------- menang / kalah ----------
    private void win(long leftMs)
    {
        state = WON;
        int secs = (int) (leftMs / 1000);
        GameData.materials[level - 1] = true;
        GameData.totalSecondsLeft += secs;
        materialBar.refresh();
        react("tertawa", "Luar biasa!");

        String msg = "Material didapat: " + LevelData.MATERIAL[level - 1] + "\nSisa waktu: " + secs + " detik";
        addObject(new Popup("LEVEL SELESAI!", msg), 450, 300);
        nextBtn = new Button("btn_continue", 190, 50);
        menuBtn = new Button("btn_menu", 140, 50);
        addObject(nextBtn, 355, 395);
        addObject(menuBtn, 545, 395);
    }

    private void lose()
    {
        state = LOST;
        react("sedih", "Yah... waktunya habis.");
        addObject(new Popup("WAKTU HABIS!", "Jangan menyerah, coba lagi!"), 450, 300);
        retryBtn = new Button("btn_retry", 170, 50);
        menuBtn = new Button("btn_menu", 140, 50);
        addObject(retryBtn, 360, 395);
        addObject(menuBtn, 545, 395);
    }

    private void goNext()
    {
        // percakapan dengan guru, lalu kembali ke peta menuju ruangan berikutnya
        Greenfoot.setWorld(new TalkWorld(level));
    }
}
