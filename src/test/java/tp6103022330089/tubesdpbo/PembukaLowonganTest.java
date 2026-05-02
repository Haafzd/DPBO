package tp6103022330089.tubesdpbo;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PembukaLowongan Tests")
class PembukaLowonganTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private PembukaLowongan penyedia;

    @BeforeEach
    void setUp() {
        User.database.clear();
        Lowongan.getDaftarLowongan().clear();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        penyedia = new PembukaLowongan("PT Maju", "hrd@ptmaju.com", "hrd123", "Jl. Industri", "Penyedia", "08111223344");
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    // ---- Constructor & Getters ----

    @Test
    @DisplayName("Constructor PembukaLowongan menyimpan field dengan benar")
    void testConstructorGetters() {
        assertEquals("PT Maju", penyedia.getNama());
        assertEquals("hrd@ptmaju.com", penyedia.getEmail());
        assertEquals("hrd123", penyedia.getPassword());
        assertEquals("Jl. Industri", penyedia.getAlamat());
        assertEquals("Penyedia", penyedia.getRole());
        assertEquals("08111223344", penyedia.getNoTelepon());
    }

    // ---- register ----

    @Test
    @DisplayName("register berhasil dengan email valid dan belum terdaftar")
    void testRegisterBerhasil() {
        penyedia.register("PT Baru", "hrd@ptbaru.com", "pass", "Jl. Baru", "08222");
        assertTrue(outContent.toString().contains("berhasil didaftarkan"));
        assertTrue(User.isEmailTerdaftar("hrd@ptbaru.com"));
    }

    @Test
    @DisplayName("register gagal jika email tidak valid")
    void testRegisterEmailTidakValid() {
        penyedia.register("PT X", "email-tidak-valid", "pass", "Jl. X", "08333");
        assertTrue(outContent.toString().contains("Email tidak valid"));
        assertFalse(User.isEmailTerdaftar("email-tidak-valid"));
    }

    @Test
    @DisplayName("register gagal jika email sudah terdaftar")
    void testRegisterEmailSudahTerdaftar() {
        User.database.add(new PembukaLowongan("Existing", "exist@mail.com", "pass", "Jl.", "Penyedia", "0"));
        penyedia.register("PT Y", "exist@mail.com", "pass", "Jl. Y", "08444");
        assertTrue(outContent.toString().contains("Email sudah terdaftar"));
    }

    // ---- menolakPelamar(Pelamar pelamar) ----

    @Test
    @DisplayName("menolakPelamar null menampilkan pesan tidak ditemukan")
    void testMenolakPelamarNull() {
        penyedia.menolakPelamar((Pelamar) null);
        assertTrue(outContent.toString().contains("tidak ditemukan"));
    }

    @Test
    @DisplayName("menolakPelamar yang tidak ada di lowongan menampilkan pesan tidak ditemukan")
    void testMenolakPelamarTidakAdaDiLowongan() {
        Pelamar pelamar = new Pelamar("Andi", "andi@mail.com", "pass", "Jl. A", "Pelamar", "08555");
        // Penyedia belum membuat lowongan apapun
        penyedia.menolakPelamar(pelamar);
        assertTrue(outContent.toString().contains("tidak ditemukan"));
    }

    // ---- menolakPelamar(String idLowongan, String identitasPelamar) ----

    @Test
    @DisplayName("menolakPelamar(id, identitas): lowongan tidak ada menampilkan pesan tidak ditemukan")
    void testMenolakPelamarByIdLowonganTidakAda() {
        penyedia.menolakPelamar("TIDAK_ADA_ID", "andi@mail.com");
        assertTrue(outContent.toString().contains("tidak ditemukan pada akun Anda"));
    }

    // ---- showLowongan saat kosong ----

    @Test
    @DisplayName("showLowongan menampilkan pesan jika belum ada lowongan yang dibuat")
    void testShowLowonganKosong() {
        penyedia.showLowongan();
        assertTrue(outContent.toString().contains("Tidak ada lowongan tersedia"));
    }

    // ---- melihatDaftarPelamar saat kosong ----

    @Test
    @DisplayName("melihatDaftarPelamar menampilkan pesan jika belum ada lowongan dibuat")
    void testMelihatDaftarPelamarKosong() {
        penyedia.melihatDaftarPelamar();
        assertTrue(outContent.toString().contains("Belum ada lowongan yang dibuat"));
    }

    // ---- Skenario terintegrasi: buat lowongan, lamar, tolak ----

    @Test
    @DisplayName("Skenario: penyedia buat lowongan, pelamar melamar, penyedia tolak pelamar")
    void testSkenarioLengkap() {
        // Buat lowongan langsung tanpa Scanner dengan cara inject ke list
        Lowongan lowongan = new Lowongan("L099", "Software Engineer", penyedia.getNama(), "Deskripsi SE");
        // Simulasikan membuatLowongan dengan menambah ke daftar internal (workaround tanpa Scanner)
        Lowongan.tambahLowongan(lowongan);

        // Pelamar melamar
        Pelamar pelamar = new Pelamar("Budi", "budi@mail.com", "pass", "Jl. B", "Pelamar", "08666");
        boolean berhasil = lowongan.melamar(pelamar);
        assertTrue(berhasil, "Pelamar berhasil melamar");
        assertEquals(1, lowongan.getPelamarList().size());

        // Tolak pelamar melalui Lowongan langsung (hapusPelamar)
        boolean terhapus = lowongan.hapusPelamar("budi@mail.com");
        assertTrue(terhapus, "Pelamar berhasil ditolak");
        assertTrue(lowongan.getPelamarList().isEmpty());
    }
}
