
import greenfoot.*;
import java.util.*;

public class HowToPlayWorld extends BaseWorld
{
    private final List<Card> cards = new ArrayList<Card>();

    private DialogBox box;
    private Button backBtn;

    private final int tutorialLevel = 1;
    private final int cw = 130;
    private final int ch = 150;

    // Status tutorial
    private int step = 0;
    private int matchedPairs = 0;

    private Card firstCard = null;
    private Card secondCard = null;

    private boolean waitingForInput = false;
    private boolean waitingForCard = false;

    public HowToPlayWorld()
    {
        super();

        setBackground(Backgrounds.classroom(1));

        addObject(
            new Label("How to Play", 30, Gfx.TEXT_LIGHT, true),
            450, 42
        );

        box = new DialogBox(880, 130);
        addObject(box, 450, 525);

        backBtn = new Button("btn_back", 180, 50);
        addObject(backBtn, 120, 50);

        createCards();

        SoundManager.playMusic("bgm_menu.mp3");

        showStep();
    }

    // =====================================
    // MEMBUAT KARTU
    // =====================================

    private void createCards()
    {
        String[][] pairs = LevelData.PAIRS[tutorialLevel - 1];

        // Dua pasangan kartu
        for (int i = 0; i < 2; i++)
        {
            String[] p = pairs[i];

            cards.add(new Card(
                i, p[0], cw, ch, tutorialLevel, 'a'
            ));

            cards.add(new Card(
                i, p[1], cw, ch, tutorialLevel, 'b'
            ));
        }

        // Posisi papan 2x2
        int[] xs = {370, 530, 370, 530};
        int[] ys = {190, 190, 360, 360};

        for (int i = 0; i < cards.size(); i++)
        {
            addObject(cards.get(i), xs[i], ys[i]);
        }
    }

    // =====================================
    // ACT
    // =====================================

    public void act()
    {
        String key = Greenfoot.getKey();

        if (backBtn.clicked() || "escape".equals(key))
        {
            Greenfoot.setWorld(new MenuWorld());
            return;
        }

        // Menunggu SPACE atau klik dialog
        if (waitingForInput)
        {
            if ("space".equals(key) || Greenfoot.mouseClicked(box))
            {
                waitingForInput = false;
                nextStep();
            }

            return;
        }

        // Menunggu player memilih kartu
        if (waitingForCard)
        {
            for (Card c : cards)
            {
                if (Greenfoot.mouseClicked(c))
                {
                    selectCard(c);
                    break;
                }
            }
        }
    }

    // =====================================
    // MENAMPILKAN INSTRUKSI
    // =====================================

    private void showStep()
    {
        waitingForInput = false;
        waitingForCard = false;

        switch (step)
        {
            // STEP 0
            case 0:

                for (Card c : cards)
                {
                    c.flip(true);
                }

                box.show(
                    "Tutorial",
                    "1. Di awal permainan, semua kartu terbuka sebentar. Hafalkan posisi dan isi kartunya!",
                    "SPACE / Klik untuk lanjut"
                );

                waitingForInput = true;
                break;

            // STEP 1
            case 1:

                for (Card c : cards)
                {
                    c.flip(false);
                }

                box.show(
                    "Tutorial",
                    "2. Sekarang semua kartu tertutup. Kamu harus mengingat posisi kartu yang memiliki pasangan!",
                    "SPACE / Klik untuk lanjut"
                );

                waitingForInput = true;
                break;

            // STEP 2
            case 2:

                box.show(
                    "Tutorial",
                    matchedPairs == 0
                    ? "3. Sekarang coba klik salah satu kartu untuk membukanya!"
                    : "Bagus! Sekarang cari pasangan kartu berikutnya. Klik kartu pertama!",
                    ""
                );

                waitingForCard = true;
                break;

            // STEP 3
            case 3:

                box.show(
                    "Tutorial",
                    "4. Pilih satu kartu lainnya. Apakah kedua kartu tersebut memiliki isi yang cocok?",
                    ""
                );

                waitingForCard = true;
                break;

            // STEP 4 - SALAH
            case 4:

                box.show(
                    "Tidak Cocok!",
                    "Kedua kartu berbeda. Kartu akan tertutup kembali. Ingat posisi dan isi kartu tersebut!",
                    "SPACE / Klik untuk lanjut"
                );

                waitingForInput = true;
                break;

            // STEP 5 - BENAR
            case 5:

                box.show(
                    "Cocok!",
                    "Hebat! Kedua kartu memiliki pasangan yang sama. Kartu yang cocok akan tetap terbuka!",
                    "SPACE / Klik untuk lanjut"
                );

                waitingForInput = true;
                break;

            // STEP 6 - SELESAI
            case 6:

                box.show(
                    "Tutorial Selesai!",
                    "Kamu sudah memahami cara bermain! Temukan semua pasangan sebelum waktu habis untuk menyelesaikan level.",
                    "SPACE / Klik untuk kembali"
                );

                waitingForInput = true;
                break;
        }
    }

    // =====================================
    // MEMILIH KARTU
    // =====================================

    private void selectCard(Card c)
    {
        // Tidak boleh memilih kartu yang sudah terbuka
        // atau sudah menjadi pasangan
        if (c.isFaceUp() || c.isMatched())
        {
            return;
        }

        c.flip(true);
        SoundManager.sfx("flip.wav");

        if (firstCard == null)
        {
            firstCard = c;

            step = 3;
            showStep();
        }
        else
        {
            secondCard = c;

            checkPair();
        }
    }

    // =====================================
    // CEK PASANGAN
    // =====================================

    private void checkPair()
    {
        if (firstCard.getPairId() == secondCard.getPairId())
        {
            // PASANGAN BENAR

            firstCard.markMatched();
            secondCard.markMatched();

            matchedPairs++;

            SoundManager.sfx("correct.wav");

            step = 5;
            showStep();
        }
        else
        {
            // PASANGAN SALAH

            SoundManager.sfx("wrong.wav");

            step = 4;
            showStep();
        }
    }

    // =====================================
    // PINDAH STEP
    // =====================================

    private void nextStep()
    {
        switch (step)
        {
            case 0:
                step = 1;
                break;

            case 1:
                step = 2;
                break;

            case 4:
                // Tutup kembali kartu yang salah

                firstCard.flip(false);
                secondCard.flip(false);

                firstCard = null;
                secondCard = null;

                step = 2;
                break;

            case 5:
                // Cek apakah semua pasangan sudah ditemukan

                firstCard = null;
                secondCard = null;

                if (matchedPairs == 2)
                {
                    step = 6;
                }
                else
                {
                    step = 2;
                }
                break;

            case 6:
                Greenfoot.setWorld(new MenuWorld());
                return;
        }

        showStep();
    }
}
