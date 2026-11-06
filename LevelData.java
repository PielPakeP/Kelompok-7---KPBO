/**
 * Semua data isi level: guru, tema warna, ukuran papan, dan pasangan kartu.
 * Mau ganti soal / nama guru / dialog? Cukup edit file ini.
 */
public class LevelData
{
    public static final int MAX_LEVEL = 4;

    public static final String[] SUBJECT = { "IPA", "Olahraga", "Musik", "Matematika" };
    public static final String[] TEACHER = { "Piel", "Aaron", "Rafael", "Steven" };
    public static final String[] MATERIAL = { "Tali", "Papan", "Paku", "Palu" };

    // Kalimat pertama guru (sesuai dokumen konsep)
    public static final String[] INTRO = { "halo", "woi", "bisa main ga?", "hidup bu rita" };

    // Ukuran papan: level 1 = 8 kartu, tiap level +2 pasangan (+4 kartu)
    public static final int[] COLS = { 4, 4, 4, 5 };
    public static final int[] ROWS = { 2, 3, 4, 4 };
    // jumlah pasangan = COLS*ROWS/2  ->  4, 6, 8, 10

    public static final String[] CORRECT = { "Mantap!", "Benar!", "Hebat!", "Nah itu dia!" };
    public static final String[] WRONG = { "Coba lagi!", "Ingat posisinya!", "Hampir!", "Belum cocok!" };

    /**
     * Pasangan kartu: PAIRS[level-1][nomorPasangan] = { kartuA, kartuB }.
     * Tiap level butuh 4 / 6 / 8 / 10 pasangan (disediakan 10 per level).
     * Pastikan teks tiap kartu UNIK dalam satu level.
     */
    public static final String[][][] PAIRS = {
        // ===== Level 1 : IPA =====
        {
            { "H2O", "Air" }, { "O2", "Oksigen" }, { "CO2", "Karbon dioksida" },
            { "NaCl", "Garam dapur" }, { "Fe", "Besi" }, { "Au", "Emas" },
            { "Akar", "Menyerap air" }, { "Daun", "Fotosintesis" },
            { "Bulan", "Satelit alami" }, { "Matahari", "Pusat tata surya" }
        },
        // ===== Level 2 : Olahraga =====
        {
            { "Sepak bola", "11 pemain" }, { "Bola basket", "5 pemain" }, { "Bola voli", "6 pemain" },
            { "Bulu tangkis", "Shuttlecock" }, { "Renang", "Gaya bebas" }, { "Lari 100 m", "Sprint" },
            { "Lompat jauh", "Bak pasir" }, { "Tenis meja", "Bet dan bola" },
            { "Senam lantai", "Matras" }, { "Lempar lembing", "Atletik" }
        },
        // ===== Level 3 : Musik =====
        {
            { "Gitar", "6 senar" }, { "Piano", "88 tuts" }, { "Drum", "Stik" },
            { "Biola", "Digesek" }, { "Seruling", "Ditiup" }, { "Angklung", "Jawa Barat" },
            { "Sasando", "NTT" }, { "Tifa", "Papua" },
            { "Gamelan", "Jawa" }, { "Kolintang", "Sulawesi Utara" }
        },
        // ===== Level 4 : Matematika =====
        {
            { "3 x 4", "12" }, { "7 x 8", "56" }, { "Akar 169", "13" },
            { "2 pangkat 5", "32" }, { "15% dari 200", "30" }, { "1/2 + 1/4", "3/4" },
            { "9 kuadrat", "81" }, { "100 : 4", "25" },
            { "Keliling persegi s=5", "20" }, { "Luas persegi s=6", "36" }
        }
    };
}
