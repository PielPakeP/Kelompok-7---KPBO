import greenfoot.*;
import java.awt.Color;

/**
 * PETA SEKOLAH. Player berjalan (panah / WASD) ke ruangan tujuan, tekan SPASI di depan pintu untuk masuk.
 *
 * stage = tujuan saat ini:  1 = Ruang IPA, 2 = Lapangan Olahraga, 3 = Ruang Musik,
 *                           4 = Ruang Matematika, 5 = Jembatan (ending).
 * intro = true -> percakapan pembuka tampil di awal (hanya untuk stage 1).
 *
 * Semua koordinat di bawah memakai ukuran gambar ASLI peta (1536 x 1024),
 * lalu otomatis diperkecil ke layar 900 x 600. Mau menyesuaikan tabrakan? Ubah angka di BLOCK / ZONE,
 * dan set DEBUG = true untuk melihat kotaknya.
 */
public class MapWorld extends BaseWorld
{
    private static final boolean DEBUG = false;        // true = tampilkan kotak tabrakan (merah) & pintu (kuning), untuk menyetel peta baru
    private static final double S = 900.0 / 1536.0;    // skala gambar asli -> layar

    // Area yang tidak bisa dilewati: { kiri, atas, kanan, bawah }
    private static final int[][] BLOCK = {
        { 50, 40, 390, 248 },      // Ruang IPA
        { 435, 0, 1090, 240 },     // gedung utama
        { 1185, 35, 1450, 240 },   // Ruang Musik
        { 1175, 475, 1480, 655 },  // Ruang Matematika
        { 0, 0, 72, 860 },         // barisan pohon kiri
        { 1470, 0, 1536, 860 },    // barisan pohon kanan
        { 0, 0, 1536, 25 },        // batas atas
        { 0, 900, 1536, 1024 },    // sungai
        { 0, 845, 690, 1024 },     // pagar bawah (kiri jembatan)
        { 840, 845, 1536, 1024 },  // pagar bawah (kanan jembatan)
        { 585, 375, 660, 465 }, { 880, 375, 955, 465 },    // pohon halaman tengah
        { 585, 565, 660, 655 }, { 875, 565, 950, 655 },
        { 1110, 360, 1290, 450 },                          // pohon kanan atas
        { 350, 360, 412, 440 }, { 350, 585, 415, 665 },    // pohon dekat lapangan
        { 105, 740, 235, 850 },                            // kebun
        { 1318, 775, 1400, 850 },                          // pos kecil
        { 235, 720, 312, 800 }, { 385, 715, 470, 780 }, { 435, 745, 525, 850 },
        { 570, 715, 660, 800 }, { 860, 715, 945, 800 }, { 1005, 745, 1100, 850 },
        { 1175, 750, 1260, 850 }, { 1115, 640, 1195, 725 }  // pohon bagian bawah
    };

    // Zona pintu masuk: 0 = IPA, 1 = Olahraga, 2 = Musik, 3 = Matematika, 4 = Jembatan
    private static final int[][] ZONE = {
        { 170, 238, 275, 285 },
        { 160, 490, 285, 560 },
        { 1265, 235, 1375, 285 },
        { 1270, 650, 1380, 700 },
        { 690, 845, 840, 900 }
    };
    private static final String[] ZONE_NAME = { "Ruang IPA", "Lapangan Olahraga", "Ruang Musik", "Ruang Matematika", "Jembatan" };

    // Posisi muncul player, index = stage - 1 (stage 1 = dekat gedung utama, lainnya = di luar ruangan sebelumnya)
    private static final int[][] SPAWN = { { 745, 330 }, { 222, 300 }, { 335, 548 }, { 1320, 300 }, { 1325, 715 } };

    private final int stage;
    private final int target;           // index zona tujuan = stage - 1
    private final int[][] wallsWorld;   // BLOCK yang sudah diskalakan ke layar
    private final int[][] zonesWorld;
    private Player player;
    private Label prompt;
    private boolean promptShown = false;

    // dialog
    private String[][] pages = null;
    private int page = 0;
    private DialogBox box;

    public MapWorld(int stage, boolean intro)
    {
        super();
        this.stage = stage;
        this.target = stage - 1;
        wallsWorld = scale(BLOCK);
        zonesWorld = scale(ZONE);

        GreenfootImage bg = Backgrounds.map();
        if (DEBUG) drawDebug(bg);
        setBackground(bg);

        // pemain
        player = new Player();
        addObject(player, (int) (SPAWN[target][0] * S), (int) (SPAWN[target][1] * S));

        // penanda tujuan
        int[] z = zonesWorld[target];
        addObject(new Marker(), (z[0] + z[2]) / 2, z[1] - 14);

        // HUD
        addObject(new Label("                                     ", 14, Gfx.TEXT_LIGHT, true), 700, 90);
        addObject(new MaterialBar(), 770, 34);
        addObject(new Label("Panah / WASD: jalan     SPASI: masuk / lanjut", 14, Gfx.TEXT_LIGHT, true), 450, 580);

        prompt = new Label("", 16, Gfx.TEXT_LIGHT, true);

        box = new DialogBox(840, 150);
        if (intro) startDialog(Dialogs.MAP_INTRO);

        SoundManager.playMusic("bgm_menu.mp3");
    }

    // ---------- dipanggil oleh Player ----------
    public boolean isLocked()
    {
        return pages != null;
    }

    /** true jika kotak kaki pemain di titik (px, py) menabrak sesuatu. */
    public boolean isBlocked(int px, int py)
    {
        int l = px - 8, r = px + 8, t = py + 12, b = py + 22;
        for (int[] w : wallsWorld)
            if (l < w[2] && r > w[0] && t < w[3] && b > w[1]) return true;
        if (l < 0 || t < 0 || r > 900 || b > 600) return true;
        return false;
    }

    // ---------- loop utama ----------
    public void act()
    {
        String k = Greenfoot.getKey();
        boolean confirm = (k != null && (k.equals("space") || k.equals("enter")));
        boolean click = Greenfoot.mouseClicked(null);

        if (pages != null)
        {
            if (confirm || click) nextPage();
            return;
        }
        updatePrompt();
        if (confirm) interact();
    }

    /** Index zona tempat kaki pemain berada, atau -1. */
    private int zoneAtPlayer()
    {
        int fx = player.getX();
        int fy = player.getY() + 20;
        for (int i = 0; i < zonesWorld.length; i++)
        {
            int[] z = zonesWorld[i];
            if (fx >= z[0] && fx <= z[2] && fy >= z[1] && fy <= z[3]) return i;
        }
        return -1;
    }

    private void updatePrompt()
    {
        int z = zoneAtPlayer();
        if (z < 0)
        {
            if (promptShown)
            {
                removeObject(prompt);
                promptShown = false;
            }
            return;
        }
        String t;
        if (z == target) t = (z == 4) ? "SPASI: perbaiki jembatan" : "SPASI: masuk " + ZONE_NAME[z];
        else t = (z == 4) ? "Jembatan rusak" : ZONE_NAME[z] + " (bukan tujuan)";
        prompt.setText(t);
        int px = Math.max(110, Math.min(790, player.getX()));
        if (!promptShown)
        {
            addObject(prompt, px, player.getY() - 42);
            promptShown = true;
        }
        else
        {
            prompt.setLocation(px, player.getY() - 42);
        }
    }

    private void interact()
    {
        int z = zoneAtPlayer();
        if (z < 0) return;
        SoundManager.sfx("click.wav");
        if (z == target)
        {
            if (z < 4) Greenfoot.setWorld(new GameWorld(stage));      // masuk level
            else Greenfoot.setWorld(new StoryWorld(true));             // jembatan -> ending
        }
        else if (z == 4)
        {
            startDialog(new String[][] { Dialogs.BRIDGE_BROKEN });
        }
        else
        {
            String goal = (target < 4) ? ZONE_NAME[target] : "Jembatan";
            startDialog(new String[][] { { "Narator", "Belum waktunya masuk ke " + ZONE_NAME[z] + ". Tujuanmu sekarang: " + goal + "." } });
        }
    }

    // ---------- dialog di peta ----------
    private void startDialog(String[][] p)
    {
        pages = p;
        page = 0;
        if (promptShown)
        {
            removeObject(prompt);
            promptShown = false;
        }
        addObject(box, 450, 505);
        showPage();
    }

    private void showPage()
    {
        box.show(pages[page][0], pages[page][1], "klik / SPASI untuk lanjut");
    }

    private void nextPage()
    {
        page++;
        if (page >= pages.length)
        {
            removeObject(box);
            pages = null;
        }
        else
        {
            SoundManager.sfx("click.wav");
            showPage();
        }
    }

    // ---------- bantu ----------
    private static int[][] scale(int[][] src)
    {
        int[][] out = new int[src.length][4];
        for (int i = 0; i < src.length; i++)
            for (int j = 0; j < 4; j++)
                out[i][j] = (int) Math.round(src[i][j] * S);
        return out;
    }

    /** ALAT BANTU DEVELOPER (hanya jalan kalau DEBUG = true). Satu-satunya tempat di game yang menggambar dengan kode. */
    private void drawDebug(GreenfootImage bg)
    {
        bg.setColor(Gfx.toGreenfootColor(Color.RED));
        for (int[] w : wallsWorld) bg.drawRect(w[0], w[1], w[2] - w[0], w[3] - w[1]);
        bg.setColor(Gfx.toGreenfootColor(Color.YELLOW));
        for (int[] z : zonesWorld) bg.drawRect(z[0], z[1], z[2] - z[0], z[3] - z[1]);
    }
}
