/**
 * Semua data isi level: guru, tema warna, ukuran papan, dan pasangan kartu.
 * Mau ganti soal / nama guru / dialog? Cukup edit file ini.
 */
public class LevelData
{
    public static final int MAX_LEVEL = 4;

    public static final String[] SUBJECT = { "IPA", "Olahraga", "Musik", "Matematika" };
    public static final String[] TEACHER = { "Piel", "Aaron", "Rafael", "Steven" };
    public static final String[] MATERIAL = { "Batu", "Kayu", "Paku", "Tali" };

    // Kalimat pertama guru (sesuai dokumen konsep)
    public static final String[] INTRO = { "halo", "woi", "bisa main ga?", "hidup bu rita" };

    // Ukuran papan: level 1 = 8 kartu, tiap level +2 pasangan (+4 kartu)
    public static final int[] COLS = { 4, 4, 4, 5 };
    public static final int[] ROWS = { 2, 3, 4, 4 };
    // jumlah pasangan = COLS*ROWS/2  ->  4, 6, 8, 10

    public static final String[] CORRECT = { "Mantap!", "Benar!", "Hebat!", "Nah itu dia!" };
    public static final String[] WRONG = { "Coba lagi!", "Ingat posisinya!", "Hampir!", "Belum cocok!" };
}
