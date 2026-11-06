/**
 * Semua percakapan (dialog) di game. Format tiap baris: { "Nama pembicara", "Teks" }.
 * Mau ganti isi cerita? Cukup edit file ini.
 */
public class Dialogs
{
    /** Percakapan di awal game (terjadi di peta). */
    public static final String[][] MAP_INTRO = {
        { "Narator", "Di sebuah desa kecil, berdiri sebuah SMP yang dikelilingi sungai. Satu-satunya jembatan menuju sekolah kini rusak parah." },
        { "Kamu", "Aku murid baru di sini... tapi aku tidak mau teman-temanku susah menyeberang setiap hari!" },
        { "Kepala Sekolah", "Semangat yang bagus! Ingat, jembatan yang rusak bukan akhir, tapi awal dari perubahan." },
        { "Kepala Sekolah", "Para guru menyimpan material untuk memperbaiki jembatan. Tapi material itu hanya diberikan kepada murid yang berhasil melewati tantangan di kelas mereka." },
        { "Kepala Sekolah", "Temukan semua pasangan kartu sebelum waktu 2 menit habis. Ada 4 material: Tali, Papan, Paku, dan Palu." },
        { "Kepala Sekolah", "Mulailah dari Ruang IPA di kiri atas. Berjalanlah dengan tombol panah atau WASD, lalu tekan SPASI di depan pintu." }
    };

    /** Percakapan setelah menyelesaikan level (AFTER[level-1]). Pembicara = guru level tersebut. */
    public static final String[][] AFTER = {
        // Level 1 - IPA
        {
            "Wah, hebat! Kamu berhasil menyelesaikan tantangan IPA.",
            "Ini material pertama untuk memperbaiki jembatan: Tali. Simpan baik-baik ya!",
            "Selanjutnya, temui Aaron di Lapangan Olahraga, di sebelah kiri halaman tengah."
        },
        // Level 2 - Olahraga
        {
            "Mantap! Stamina dan ingatanmu bagus juga.",
            "Ini Papan kayu untuk jembatan. Jangan lupa pemanasan sebelum lanjut!",
            "Berikutnya, pergilah ke Ruang Musik di pojok kanan atas. Rafael sudah menunggumu."
        },
        // Level 3 - Musik
        {
            "Boleh juga! Telingamu tajam dan ingatanmu kuat.",
            "Ini Paku. Kecil, tapi penting untuk menyatukan papan jembatan.",
            "Tinggal satu lagi: Ruang Matematika di kanan bawah. Steven menunggumu di sana."
        },
        // Level 4 - Matematika
        {
            "Luar biasa! Semua soal berhasil kamu pecahkan.",
            "Ini material terakhir: Palu. Sekarang semua material sudah lengkap!",
            "Ayo ke jembatan di bagian bawah sekolah. Bersama, kita bisa memperbaiki jembatan ini!"
        }
    };

    public static final String[] BRIDGE_BROKEN = { "Narator", "Jembatan masih rusak parah. Kumpulkan dulu semua material dari 4 ruangan!" };
}
