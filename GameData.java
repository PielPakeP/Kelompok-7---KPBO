/**
 * Data global game (static) - dipakai oleh semua World.
 * Menyimpan pengaturan (Settings) dan progres pemain.
 */
public class GameData
{
    // ===== Pengaturan (Settings) =====
    public static boolean musicOn = true;
    public static int volume = 70;       // volume utama 0-100

    // ===== Progres permainan =====
    public static int currentLevel = 1;
    public static boolean[] materials = new boolean[4]; // material yang sudah didapat
    public static int totalSecondsLeft = 0;             // total sisa waktu semua level

    /** Reset progres untuk memulai permainan baru (pengaturan tidak direset). */
    public static void newGame()
    {
        currentLevel = 1;
        materials = new boolean[4];
        totalSecondsLeft = 0;
    }
}
