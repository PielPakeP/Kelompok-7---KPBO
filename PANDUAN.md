# PANDUAN GAME "SEKOLAH JEMBATAN" (Greenfoot)

Game memori kartu edukasi: 4 level (IPA, Olahraga, Musik, Matematika), timer 2 menit, kumpulkan 4 material untuk memperbaiki jembatan.

## 1. Cara menjalankan
1. Install Greenfoot (versi 3.8 atau lebih baru).
2. Ekstrak zip, lalu di Greenfoot pilih Scenario > Open, pilih folder `SekolahJembatan`.
3. Klik Compile, lalu Run. Layar pertama adalah MenuWorld.
4. Semua tampilan memakai **file gambar di folder `images/`** (tidak ada yang digambar lewat kode). Folder itu sudah berisi gambar placeholder dengan nama dan ukuran yang benar, jadi game langsung jalan. Tinggal timpa dengan gambar buatanmu (lihat bagian 5).
5. Suara boleh kosong. Kalau folder `sounds/` belum diisi, game tetap jalan tanpa bunyi.

## 2. Alur game (urutan layar)

```
MenuWorld --Start--> MapWorld (percakapan awal dengan Kepala Sekolah)
   |                      |
   |                      v  player jalan ke Ruang IPA, tekan SPASI di pintu
   |-- How to Play        GameWorld(1) --menang--> TalkWorld(1) (guru memberi material + arahan)
   |-- Settings                                        |
   |-- Info (i)                                        v
   |-- Exit (popup)       MapWorld(tujuan 2: Lapangan Olahraga) --> GameWorld(2) --> TalkWorld(2)
                          MapWorld(tujuan 3: Ruang Musik)       --> GameWorld(3) --> TalkWorld(3)
                          MapWorld(tujuan 4: Ruang Matematika)  --> GameWorld(4) --> TalkWorld(4)
                          MapWorld(tujuan 5: Jembatan) --SPASI--> StoryWorld(ending) --> EndingWorld
                          (kalah di level mana pun: popup Coba Lagi / Menu)
```

## 3. Panduan per layar

### A. MenuWorld (Dashboard)
- Isi: tombol Start Game, How to Play, Settings, Exit, dan tombol "i" (information).
- Exit memunculkan popup "Apakah yakin mau keluar dari game?" dengan tombol Yes / No.
- Yes memanggil `Greenfoot.stop()`. Kalau nanti game di-export jadi aplikasi, boleh diganti `System.exit(0);`.
- Start Game memanggil `GameData.newGame()` (reset progres), lalu pindah ke StoryWorld.

### B. SettingsWorld
- Isi: Volume (slider), Music ON/OFF, Sound Effect ON/OFF, Volume Music, Volume SFX, Fullscreen.
- Semua nilai disimpan di `GameData` (variabel static), jadi berlaku di semua layar.
- Klik X (atau tekan Esc) untuk kembali.
- Catatan: Greenfoot tidak punya perintah fullscreen. Toggle Fullscreen hanya menyimpan pilihan. Kalau mau efek nyata, export scenario jadi aplikasi lalu maximize jendelanya.

### C. HowToPlayWorld
- Animasi demo papan 2x2 yang berjalan sendiri dan berulang.
- Setiap ada kartu yang dibalik, kotak petunjuk di bawah berganti teks.
- Teks dan waktu tiap langkah ada di array `MSG` dan `T` di `HowToPlayWorld.java`.

### D. InfoWorld
- Menampilkan developer dan credits. Edit teks `info` di `InfoWorld.java` (isi sumber musik dan ilustrasi).

### E. MapWorld (peta sekolah) - BARU
- Player berjalan dengan tombol **panah atau WASD**. Gedung, pohon, pagar, dan sungai tidak bisa ditembus.
- **Percakapan awal** muncul otomatis di peta (dengan Kepala Sekolah), lalu player bebas berjalan. Klik atau SPASI untuk lanjut dialog.
- Ada **panah kuning** di ruangan tujuan dan tulisan "Tujuan: ..." di pojok kiri atas.
- Berdiri di depan pintu, lalu tekan **SPASI** untuk masuk. Kalau bukan ruangan tujuan, muncul pesan "Belum waktunya masuk ke ...".
- Urutan tujuan: Ruang IPA, Lapangan Olahraga (berdiri di tengah lapangan), Ruang Musik, Ruang Matematika, lalu Jembatan.
- Jembatan hanya bisa dipakai setelah 4 material lengkap. Kalau belum, muncul pesan jembatan masih rusak.
- Cara menyesuaikan area tabrakan dan pintu ada di bagian 5.

### F. TalkWorld (percakapan setelah level) - BARU
- Muncul setelah menang di GameWorld. Guru level itu berbicara: memberi selamat, memberi material, lalu menunjukkan ruangan berikutnya.
- Teks ada di `Dialogs.AFTER`. Setelah dialog selesai, kembali ke MapWorld dengan tujuan baru, dan player muncul di luar ruangan yang baru dikunjungi.

### F2. StoryWorld (cerita penutup)
- Sekarang hanya dipakai untuk cerita ending setelah player sampai di jembatan. Cerita pembuka sudah pindah ke MapWorld.
- Edit teks ending lewat array `ENDING` di `StoryWorld.java`.

### G. GameWorld (papan game)
Urutan di dalam satu level:
1. PREVIEW: semua kartu terbuka beberapa detik (3, 4, 5, 6 detik sesuai level). Guru mengucapkan kalimat pembuka.
2. PLAYING: kartu menutup, timer 2 menit jalan. Klik 2 kartu. Cocok = tetap terbuka (border hijau), tidak cocok = menutup lagi.
3. WON: semua pasangan ketemu. Popup "Level Selesai" menampilkan material dan sisa waktu. Tombol Lanjutkan membawa ke TalkWorld (percakapan guru), lalu kembali ke peta.
4. LOST: waktu habis. Popup dengan tombol Coba Lagi atau Menu.

Jumlah kartu per level (sesuai dokumen: mulai 8 kartu, tiap level +2 pasangan):

| Level | Mapel | Guru | Kartu | Papan |
|-------|-------|------|-------|-------|
| 1 | IPA | Piel | 8 (4 pasang) | 4 x 2 |
| 2 | Olahraga | Aaron | 12 (6 pasang) | 4 x 3 |
| 3 | Musik | Rafael | 16 (8 pasang) | 4 x 4 |
| 4 | Matematika | Steven | 20 (10 pasang) | 5 x 4 |

Catatan: gambar mockup di dokumen punya jumlah kartu yang berbeda-beda (6, 12, 20, 12). Aku mengikuti aturan tertulis di dokumen (8 lalu +2 pasangan).

Material di pojok kanan atas: 4 slot bayangan yang berubah berwarna saat material didapat (Tali, Papan, Paku, Palu). Kalau mau versi teks "01/04", ganti `MaterialBar` dengan `Label` di `GameWorld.buildHud()`.

### H. EndingWorld
- Jembatan terlihat sudah utuh, ada tulisan TAMAT, total sisa waktu, dan tombol Main Lagi / Menu Utama.

## 4. Mengganti soal, nama guru, dan dialog
Semua ada di `LevelData.java`:
- `PAIRS` : pasangan kartu per level. Teks tiap kartu harus unik dalam satu level.
- `TEACHER`, `SUBJECT`, `MATERIAL`, `INTRO` : nama guru, mapel, material, dan kalimat pembuka guru.
- `CORRECT`, `WRONG` : kalimat guru saat jawaban benar atau salah.

Percakapan cerita ada di `Dialogs.java`: `MAP_INTRO` (awal di peta), `AFTER` (setelah tiap level), dan `BRIDGE_BROKEN`.

## 5. Daftar gambar (images/) dan suara (sounds/)

Aturan:
- Tulis nama file **tanpa ekstensi** di kode. Game mencari `.jpg` dulu, lalu `.png`. Jadi kamu bebas pakai JPG.
- **JPG tidak punya bagian transparan.** Untuk karakter, panah, dan knob slider, pakai **PNG** supaya tidak ada kotak putih. Gambar lain (background, tombol, kartu, panel) aman dengan JPG.
- Gambar akan di-scale otomatis ke ukuran di tabel, jadi ukuran asli boleh lebih besar asal proporsinya sama.
- Kalau ada file yang hilang, muncul pesan `[ASET HILANG] ...` di jendela Greenfoot (Show Terminal / Console) dan elemen itu tampil kosong.
- Teks (dialog, angka timer, isi kartu, persen volume) tetap ditulis lewat kode karena isinya berubah-ubah. Warna teks diatur di 3 konstanta paling atas `Gfx.java` (`TEXT_LIGHT`, `TEXT_DARK`, `TEXT_ACCENT`).

### A. Background (900 x 600)
| File | Isi |
|------|-----|
| `bg_menu` | Menu utama: pemandangan sekolah + jembatan rusak. Papan kayu "Jembatan yang rusak..." dan tulisan "Bersama, kita..." ikut digambar di sini. Sisakan ruang untuk judul (tengah atas) dan 4 tombol (tengah, y 240-510) |
| `bg_settings` | Panel Settings lengkap dengan judul "Settings". Baris pengaturan ada di y 160, 225, 290, 355, 420, 485 (label di kiri x 165, kontrol di kanan) |
| `bg_info` | Panel Information lengkap dengan judul. Teks info muncul di tengah panel mulai y 130 |
| `bg_ending` | Pemandangan dengan jembatan sudah utuh |
| `bg_level1` - `bg_level4` | Kelas tiap level. Area papan kartu: x 30-720, y 95-570. Sisi kanan (x 730-900) dipakai guru dan balon bicara |
| `map` | Peta sekolah (sudah berisi MAPS.png milikmu) |

### B. Judul dan tombol
| File | Ukuran | Keterangan |
|------|--------|------------|
| `title` | 520 x 170 | Logo "Sekolah Jembatan" di menu |
| `tamat` | 420 x 130 | Tulisan TAMAT di ending |
| `btn_start`, `btn_howto`, `btn_settings`, `btn_exit` | 260 x 58 | Tombol menu utama (teks "Start Game" dst. ikut digambar) |
| `btn_info` | 52 x 52 | Tombol "i" |
| `btn_close` | 46 x 46 | Tombol X di Settings |
| `btn_back` | 180 x 50 | Kembali (Info, How to Play) |
| `btn_skip` | 130 x 44 | Lewati |
| `btn_yes`, `btn_no` | 170 x 50 | Konfirmasi keluar |
| `btn_continue` | 190 x 50 | Lanjutkan (popup menang) |
| `btn_retry` | 170 x 50 | Coba Lagi (popup kalah) |
| `btn_menu` | 140 x 50 | Menu (popup menang/kalah, ending) |
| `btn_again` | 220 x 56 | Main Lagi (ending) |

Opsional: tambahkan `btn_start_hover` (dst.) untuk gambar saat mouse di atas tombol. Kalau tidak ada, tombol hanya membesar sedikit.

### C. Elemen game
| File | Ukuran | Keterangan |
|------|--------|------------|
| `card_back_1` - `card_back_4` | 125 x 150 | Punggung kartu tiap level (di-scale mengikuti ukuran kartu level) |
| `card_front` | 125 x 150 | Muka kartu kosong (teks soal ditulis di atasnya) |
| `card_front_ok` | 125 x 150 | Muka kartu yang sudah cocok |
| `timer_frame` | 340 x 50 | Bingkai timer + ikon jam di kiri. Area isi bar: mulai x 56, y 15, lebar maks 230, tinggi 20 |
| `timer_fill`, `timer_fill_low` | 230 x 20 | Isi bar timer, dan isi bar saat waktu hampir habis (kurang dari 25%) |
| `material_1` - `material_4` | 48 x 48 | Material sudah didapat: Tali, Papan, Paku, Palu |
| `material_1_off` - `material_4_off` | 48 x 48 | Bayangan material (belum didapat) |
| `bubble` | 170 x 100 | Balon bicara guru. Badan balon di bagian atas (sekitar 82 px), ekor di bawah |
| `dialog_box` | 840 x 150 | Kotak dialog. Nama pembicara di kiri atas, teks di bawahnya |
| `popup` | 480 x 300 | Panel popup (judul dan pesan ditulis di atasnya) |
| `popup_dim` | 900 x 600 | Opsional. PNG hitam semi-transparan untuk meredupkan layar di belakang popup |
| `label_bg` | 200 x 40 | Latar teks kecil (banner level, tujuan, jumlah pasangan). Di-stretch sesuai panjang teks |
| `slider_track`, `slider_fill` | 250 x 12 | Jalur slider dan bagian yang terisi |
| `slider_knob` | 28 x 28 | Bulatan slider (PNG transparan disarankan) |
| `toggle_on`, `toggle_off` | 150 x 40 | Saklar ON dan OFF |

### D. Karakter dan peta (PNG transparan disarankan)
| File | Ukuran | Keterangan |
|------|--------|------------|
| `teacher_1` - `teacher_4` | sekitar 150 x 230 | Guru IPA, Olahraga, Musik, Matematika (dipakai di kelas dan percakapan) |
| `teacher_principal` | sekitar 150 x 230 | Kepala Sekolah di peta (otomatis dikecilkan jadi tinggi 50 px) |
| `player_down_1/2`, `player_up_1/2`, `player_left_1/2`, `player_right_1/2` | 30 x 46 | Player menghadap 4 arah. `_1` = berdiri, `_2` = langkah kedua. Kaki di bagian bawah gambar karena tabrakan dihitung dari situ |
| `marker` | 34 x 34 | Panah penanda ruangan tujuan |

### E. Suara (folder sounds/, opsional)
`bgm_menu.mp3`, `bgm_game.mp3`, `click.wav`, `flip.wav`, `correct.wav`, `wrong.wav`, `win.wav`, `lose.wav`

### Menyesuaikan tabrakan dan pintu di peta
Buka `MapWorld.java`. Koordinatnya memakai ukuran gambar asli peta (1536 x 1024), jadi bisa dibaca langsung dari gambar.
- `BLOCK` : daftar kotak yang tidak bisa dilewati, format `{ kiri, atas, kanan, bawah }`. Tambah baris untuk pohon atau benda lain.
- `ZONE` : kotak pintu masuk tiap ruangan (urutan: IPA, Olahraga, Musik, Matematika, Jembatan).
- `SPAWN` : posisi muncul player di tiap tahap.
- Set `DEBUG = true` untuk melihat kotak merah (tabrakan) dan kuning (pintu) langsung di layar. Kembalikan ke `false` kalau sudah pas. Ini satu-satunya bagian kode yang menggambar dengan warna (khusus alat bantu), dan boleh dihapus kalau sudah tidak diperlukan.
- Kalau mengganti `map` dengan peta lain, sesuaikan angka-angka di atas (dan skala `S` kalau ukuran aslinya berbeda).

## 6. Peta kelas (untuk laporan KPBO)
| Konsep OOP | Contoh di kode |
|------------|----------------|
| Inheritance (pewarisan) | Semua layar mewarisi `BaseWorld`; `BaseWorld` mewarisi `World` |
| Encapsulation | `Card` menyimpan status `faceUp` / `matched` sebagai private, diakses lewat method |
| Polymorphism | `act()` di-override oleh Player, Card, Button, Slider, Teacher, dan lainnya |
| Interaksi antar objek | `Player` memanggil `MapWorld.isBlocked()` dan `isLocked()` untuk cek tabrakan dan dialog |
| Static / data global | `GameData`, `LevelData`, `SoundManager`, `Gfx` |
| Reuse (komposisi) | `Button`, `Label`, `Popup`, `DialogBox` dipakai berulang di banyak layar |

## 7. Daftar file
- Layar (World): MenuWorld, SettingsWorld, HowToPlayWorld, InfoWorld, MapWorld, TalkWorld, GameWorld, StoryWorld, EndingWorld (induk: BaseWorld)
- Aktor: Player, Marker, Card, Button, Label, Slider, Toggle, TimerBar, MaterialBar, Teacher, SpeechBubble, DialogBox, Popup, ImageActor
- Pendukung: GameData, LevelData, Dialogs, SoundManager, Gfx (pemuat gambar + teks), Backgrounds

## 8. Masalah yang sering muncul
- Tidak ada suara: normal kalau folder `sounds/` masih kosong.
- Elemen tampil kosong atau ada pesan `[ASET HILANG]`: nama file di folder `images/` tidak sama dengan tabel bagian 5 (perhatikan huruf besar-kecil dan ekstensi `.jpg` / `.png`).
- Ada kotak putih di sekeliling karakter atau knob: file itu JPG. Simpan sebagai PNG transparan.
- Animasi terasa cepat atau lambat: atur slider Speed di bawah layar Greenfoot. Timer 2 menit memakai waktu nyata, jadi tetap akurat.
- Tombol Pause: waktu di GameWorld ikut berhenti.
- Player menembus pohon atau tersangkut: sesuaikan `BLOCK` di `MapWorld.java` (lihat bagian 5).
- Player tidak bergerak: klik dulu di area layar game supaya keyboard terbaca, dan pastikan dialog sudah ditutup (SPASI).
