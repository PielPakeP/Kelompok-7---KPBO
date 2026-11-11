/**
 * Semua percakapan (dialog) di game. Format tiap baris: { "Nama pembicara", "Teks" }.
 * Speaker "" (kosong) = teks catatan / narasi tanpa nama, ditulis dalam kurung siku.
 * Mau ganti isi cerita? Cukup edit file ini.
 *
 * Tips: satu halaman dialog muat sekitar 2 baris teks. Kalau kalimat terlalu panjang, pecah jadi 2 baris data.
 */
public class Dialogs
{
    // ===== Penanda khusus untuk cerita ending (jangan diubah tulisannya kalau tidak perlu) =====
    /** Setelah halaman ini, background berganti jadi jembatan yang sudah diperbaiki. */
    public static final String CUE_REPAIR = "[POPI mulai memperbaiki jembatan.]";
    /** Di halaman ini, semua guru muncul di layar. */
    public static final String CUE_TEACHERS = "[Semua guru datang.]";

    /**
     * Ekspresi tiap baris = elemen ke-3: "netral", "senang", "tertawa", "marah", "sedih", atau "kaget".
     * Kalau elemen ke-3 tidak ada, dianggap "netral". Gambarnya: lihat Characters.java.
     */
    public static String exprOf(String[] row)
    {
        return row.length > 2 ? row[2] : "netral";
    }

    private static final String POPI = "POPI";
    private static final String RITA = "Bu Rita";
    private static final String PIEL = "Pak Piel";
    private static final String AARON = "Pak Aaron";
    private static final String RAFFAEL = "Pak Raffael";
    private static final String STEVEN = "Pak Steven";
    private static final String HATI = "POPI (dalam hati)";

    // =====================================================================
    //  OPENING - terjadi di peta saat game dimulai (dua bagian, ada jeda jalan ke jembatan)
    // =====================================================================
    /** BAGIAN 1 - POPI keluar kelas, lalu berjalan menuju jembatan. Setelah ini player HARUS berjalan mendekati jembatan. */
    public static final String[][] MAP_INTRO_1 = {
        { "", "[POPI keluar dari kelas sambil membawa tas.]", "netral" },
        { POPI, "Yesss! Akhirnya pulang juga! Hari ini capek banget… waktunya push rank!", "senang" },
        { "", "[POPI berjalan menuju jembatan.]", "netral" }
    };

    /** BAGIAN 2 - otomatis muncul begitu player sudah dekat dengan jembatan yang rusak. */
    public static final String[][] MAP_INTRO_2 = {
        { POPI, "Eh…? Kaya ada yang berbeda..", "kaget" },
        { "", "[POPI melihat jembatan yang rusak.]", "netral" },
        { POPI, "HAH?! Jembatannya rusak?! Hmm.. Bisa lewat ga ya?", "kaget" },
        { "", "[POPI mencoba melewati jembatan.]", "netral" },
        { POPI, "Kalau lewat sini… bisa sampai rumah sih…", "netral" },
        { POPI, "…tapi kayaknya sekalian sampai rumah Tuhan. T_T", "sedih" },
        { "", "[Bu Rita datang.]", "netral" },
        { RITA, "POPI! Jangan maju dulu!", "kaget" },
        { POPI, "Bu Rita! Jembatannya kenapa?!", "kaget" },
        { RITA, "Jembatan ini rusak sejak tadi pagi. Kalau mau pulang, kita harus memperbaikinya.", "netral" },
        { POPI, "Lho Bu… saya kan murid, bukan tukang jembatan.", "sedih" },
        { RITA, "Makanya kamu tidak akan memperbaikinya sendirian.", "senang" },
        { POPI, "Terus siapa yang bantu?", "netral" },
        { RITA, "Empat guru sudah menyiapkan tugas untukmu.", "senang" },
        { POPI, "Empat guru?! Waduh… pulang sekolah malah sekolah lagi.", "kaget" },
        { RITA, "Kalau kamu menyelesaikan semua tugas mereka, kamu akan mendapatkan bahan untuk memperbaiki jembatan.", "netral" },
        { POPI, "Jadi… demi bisa pulang, aku harus belajar lagi?", "sedih" },
        { RITA, "Betul.", "senang" },
        { POPI, "Kok jadi kaya escape room ya?", "kaget" },
        { RITA, "Hahaha. Anggap saja petualangan.", "tertawa" },
        { RITA, "Pertama, temui Pak Piel. Dia sedang menunggu di laboratorium.", "senang" },
        { POPI, "Baik, Bu! Misi pulang dimulai!", "senang" }
    };

    /** Gabungan bagian 1 + 2 (dipakai kalau intro diputar sekaligus, misalnya lewat StoryWorld). */
    public static final String[][] MAP_INTRO = join(MAP_INTRO_1, MAP_INTRO_2);

    private static String[][] join(String[][] x, String[][] y)
    {
        String[][] r = new String[x.length + y.length][];
        System.arraycopy(x, 0, r, 0, x.length);
        System.arraycopy(y, 0, r, x.length, y.length);
        return r;
    }

    // =====================================================================
    //  SEBELUM LEVEL - percakapan di dalam ruangan, sebelum masuk game
    //  BEFORE[level - 1]
    // =====================================================================
    public static final String[][][] BEFORE = {
        // ----- Level 1 : Laboratorium IPA (Pak Piel) -----
        {
            { PIEL, "Selamat datang, POPI!", "senang" },
            { POPI, "Pak Piel! Saya butuh bantuan. Jembatannya rusak!", "kaget" },
            { PIEL, "Saya sudah tahu. Tapi sebelum mendapatkan bahan pertama, kamu harus menyelesaikan pelajaran IPA.", "netral" },
            { HATI, "Padahal tadi mau push rank...", "sedih" },
            { POPI, "Gak bisa langsung kasih aja ya pak? Sudah mau pulang nih...", "sedih" },
            { PIEL, "Justru karena mau pulang, kamu harus menggunakan ilmu pengetahuan.", "netral" },
            { POPI, "Aduh.. Nilai IPA ku pas pas an lagi..", "sedih" },
            { PIEL, "Hahaha! Tenang. Kalau kamu berhasil, saya akan memberikan bahan untuk memperbaiki jembatan.", "tertawa" },
            { POPI, "Deal!", "senang" }
        },
        // ----- Level 2 : Lapangan Olahraga (Pak Aaron) -----
        {
            { AARON, "POPI! Kamu datang juga!", "senang" },
            { POPI, "Pak Aaron, saya mau minta bahan untuk memperbaiki jembatan.", "netral" },
            { AARON, "Boleh! Tapi kamu harus membuktikan kalau tubuhmu kuat dan sehat seperti saya!", "tertawa" },
            { POPI, "Pak… saya baru selesai IPA..", "sedih" },
            { AARON, "Bagus! Sekarang waktunya bergerak!", "senang" },
            { POPI, "Pak, masih capek nih. Boleh nggak bergeraknya besok aja?", "sedih" },
            { AARON, "Tidak!", "marah" },
            { POPI, "Yah…", "sedih" },
            { HATI, "Kayaknya, hari ini ga bisa push rank deh", "sedih" },
            { AARON, "Selesaikan tantangan olahraga ini. Kalau berhasil, kamu akan mendapatkan bahan kedua.", "netral" },
            { POPI, "Baiklah. Demi pulang!", "senang" }
        },
        // ----- Level 3 : Ruang Musik (Pak Raffael) -----
        {
            { RAFFAEL, "Selamat datang, POPI!", "senang" },
            { POPI, "Pak Raffael! Saya datang untuk meminta bahan jembatan.", "netral" },
            { RAFFAEL, "Tentu. Tapi ada satu syarat.", "netral" },
            { POPI, "Harus mengerjakan tugas lagi?", "kaget" },
            { RAFFAEL, "Tepat sekali!", "tertawa" },
            { POPI, "Kenapa semua guru punya syarat?!", "marah" },
            { RAFFAEL, "Karena tidak ada yang gratis di dunia ini, POPI.", "netral" },
            { POPI, "Pak… ini cuma bahan jembatan.", "sedih" },
            { RAFFAEL, "Bahan jembatan juga dibeli nya pake duit, POPI.", "tertawa" },
            { RAFFAEL, "Tenang saja. Kamu hanya perlu bermain musik dengan baik.", "senang" },
            { POPI, "Baiklah. Let's go!", "senang" }
        },
        // ----- Level 4 : Ruang Matematika (Pak Steven) -----
        {
            { STEVEN, "POPI! Akhirnya kamu sampai.", "senang" },
            { POPI, "Pak Steven… saya sudah melewati IPA, olahraga, dan musik.", "netral" },
            { STEVEN, "Wow, Hebat!", "senang" },
            { POPI, "Sekarang tinggal ambil bahan terakhir, kan?", "senang" },
            { STEVEN, "Betul.", "netral" },
            { POPI, "Gampang dong?", "senang" },
            { STEVEN, "Tentu.", "netral" },
            { POPI, "Serius?", "kaget" },
            { STEVEN, "Tidak.", "netral" },
            { POPI, "…", "kaget" },
            { STEVEN, "Hahaha! Hanya bercanda. Untuk mendapatkan bahan terakhir, kamu harus menyelesaikan pelajaran matematika.", "tertawa" },
            { POPI, "Kenapa matematika selalu muncul di saat-saat genting?", "sedih" },
            { STEVEN, "Karena hidup penuh perhitungan.", "senang" },
            { POPI, "Pak, saya cuma mau pulang, bukan menghitung masa depan.", "sedih" },
            { STEVEN, "Kalau begitu, selesaikan tantangannya!", "senang" }
        }
    };

    // =====================================================================
    //  SESUDAH LEVEL - setelah menang, lalu kembali ke peta
    //  AFTER[level - 1]
    // =====================================================================
    public static final String[][][] AFTER = {
        // ----- Level 1 -----
        {
            { PIEL, "Hebat! Kamu berhasil!", "senang" },
            { POPI, "YES! Satu bahan sudah didapat!", "tertawa" },
            { PIEL, "Ini bahan pertamamu. Tapi perjalananmu masih panjang.", "netral" },
            { POPI, "Masih ada lagi?!", "kaget" },
            { PIEL, "Temui Pak Aaron di lapangan olahraga.", "netral" },
            { POPI, "Dari laboratorium ke lapangan… ini namanya olahraga juga ya, Pak?", "netral" },
            { PIEL, "Bisa jadi.", "tertawa" }
        },
        // ----- Level 2 -----
        {
            { AARON, "Luar biasa! Kamu berhasil!", "tertawa" },
            { POPI, "YES! Tinggal beberapa lagi!", "senang" },
            { AARON, "Bawa bahan ini. Selanjutnya, temui Pak Raffael di ruang musik.", "senang" },
            { POPI, "Ruang musik? Akhirnya ada pelajaran yang santai.", "senang" },
            { AARON, "Belum tentu.", "tertawa" },
            { POPI, "...Perasaan nya udah ga enak nih.", "sedih" }
        },
        // ----- Level 3 -----
        {
            { RAFFAEL, "Bagus! Kayaknya kamu tidak seburuk yang saya pikirkan.", "tertawa" },
            { POPI, "Terima kasih… (kayaknya itu pujian.)", "kaget" },
            { RAFFAEL, "Ini bahan ketiga mu.", "senang" },
            { POPI, "YES! Tinggal satu lagi!", "senang" },
            { RAFFAEL, "Benar. Temui Pak Steven di ruang matematika.", "netral" },
            { POPI, "Matematika?!", "kaget" },
            { RAFFAEL, "Kenapa?", "netral" },
            { POPI, "Pak… bisa nggak level terakhir diganti olahraga lagi?", "sedih" },
            { RAFFAEL, "Tidak.", "marah" },
            { POPI, "Yah…", "sedih" }
        },
        // ----- Level 4 -----
        {
            { STEVEN, "Benar semua! Hebat, POPI!", "senang" },
            { POPI, "YESSS! AKHIRNYA!", "tertawa" },
            { STEVEN, "Ini bahan terakhir.", "senang" },
            { POPI, "Sekarang aku bisa pulang!", "senang" },
            { STEVEN, "Jangan lupa kembali ke jembatan.", "netral" },
            { POPI, "Siap, Pak!", "senang" }
        }
    };

    // =====================================================================
    //  ENDING - dimainkan setelah player sampai lagi di jembatan
    // =====================================================================
    public static final String[][] ENDING = {
        { "", "[POPI kembali ke jembatan. Bu Rita sudah menunggu.]", "netral" },
        { RITA, "POPI! Kamu berhasil!", "senang" },
        { POPI, "Bu Rita! Saya sudah mendapatkan semua bahan!", "senang" },
        { RITA, "Hebat. Sekarang kita bisa memperbaiki jembatan.", "senang" },
        { POPI, "Setelah melewati empat guru, empat tantangan, dan entah berapa banyak tugas…", "sedih" },
        { RITA, "Yang penting kamu tidak menyerah.", "senang" },
        { POPI, "Betul Bu!", "senang" },
        { "", CUE_REPAIR, "netral" },
        { RITA, "Lihat! Jembatannya sudah diperbaiki!", "senang" },
        { POPI, "AKHIRNYA!", "tertawa" },
        { "", CUE_TEACHERS, "netral" },
        { PIEL, "Wow, hebat kamu, POPI", "senang" },
        { AARON, "Kuat dan mahir sekali! Memang murid favorit saya", "tertawa" },
        { RAFFAEL, "Akhirnya bisa keluar dan beli alat musik baru!", "senang" },
        { STEVEN, "Hmm..Panjang nya sekitar 10 meter, berarti luas nya sekitar...", "netral" },
        { POPI, "Aduh, Pak Steven. Udah dong main itung itungan nya..", "kaget" },
        { STEVEN, "(kebingungan)", "kaget" },
        { POPI, "Jadi ternyata semua pelajaran ada gunanya juga ya…", "senang" },
        { STEVEN, "Betul.", "senang" },
        { POPI, "Walaupun saya masih belum tahu gunanya matematika buat memperbaiki jembatan.", "netral" },
        { STEVEN, "Yang penting kamu bisa menghitung jumlah tugasmu.", "tertawa" },
        { POPI, "Pak…", "sedih" },
        { "", "[Semua tertawa.]", "netral" },
        { RITA, "POPI, sekarang kamu sudah bisa pulang.", "senang" },
        { POPI, "YESSS! Rumah, aku datang!", "tertawa" },
        { "", "[POPI berjalan melewati jembatan.]", "netral" },
        { POPI, "Petualangan hari ini ternyata bukan cuma tentang memperbaiki jembatan.", "senang" },
        { POPI, "Tapi tentang belajar, bekerja sama, dan berani menyelesaikan masalah.", "senang" },
        { POPI, "Tapi… kalau besok jembatannya rusak lagi, saya izin sekolah online ya, Bu.", "tertawa" },
        { RITA, "POPI!", "marah" },
        { POPI, "HAHAHA! Bercanda, Bu!", "tertawa" },
        { "Pesan Moral", "Setiap masalah bisa diselesaikan jika kita mau belajar, berusaha, dan tidak menyerah.", "netral" }
    };

    /** Dipakai di peta kalau player menyentuh jembatan sebelum semua material terkumpul. */
    public static final String[] BRIDGE_BROKEN = { RITA, "Jembatannya belum bisa dilewati, POPI. Selesaikan dulu tugas dari empat guru ya!", "marah" };
}
