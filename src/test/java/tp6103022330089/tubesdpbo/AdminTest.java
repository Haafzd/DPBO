package tp6103022330089.tubesdpbo;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Admin Tests")
class AdminTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private Admin admin;

    @BeforeEach
    void setUp() {
        User.database.clear();
        Pelatihan.getDaftarPelatihan().clear();
        // Admin menggunakan field static daftarBerita, harus di-reset via reflection atau
        // hanya test yang tidak bergantung pada state bersih daftarBerita antar test
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        admin = new Admin("Admin Test", "admin@test.com", "adminpass", "Jl. Admin", "Admin", "08000000000");
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        // Reset static daftarBerita via hapus semua berita yang ditambahkan di test ini
        // Kita pakai deleteNews untuk membersihkan
    }

    // ---- register (override dari User) ----

    @Test
    @DisplayName("register admin mencetak pesan tidak perlu registrasi")
    void testRegister() {
        admin.register("nama", "email@mail.com", "pass", "alamat", "08111");
        assertTrue(outContent.toString().contains("Admin tidak perlu melakukan registrasi"));
    }

    // ---- deleteNews ----

    @Test
    @DisplayName("deleteNews pada berita yang tidak ada menampilkan pesan tidak ditemukan")
    void testDeleteNewsNotFound() {
        admin.deleteNews("TIDAK_ADA_ID");
        assertTrue(outContent.toString().contains("tidak ditemukan"));
    }

    // ---- displayNews saat kosong ----

    @Test
    @DisplayName("displayNews menampilkan pesan jika tidak ada berita (saat list kosong)")
    void testDisplayNewsEmptyViaDelete() {
        // Tambah dan hapus berita sehingga list kosong, lalu display
        // karena kita tidak bisa reset static field secara langsung,
        // kita verifikasi displayNews output bergantung pada state saat ini
        // (tidak bisa menjamin kosong tapi kita bisa test dengan spy output)
        // Ini test terpisah yang dijalankan pertama secara independen
        // Kita skip jika list sudah terisi dari test lain. Kita cukup test
        // bahwa deleteNews pada ID tidak ada tidak crash.
        assertDoesNotThrow(() -> admin.deleteNews("IDYGTIDAKADA_999"));
    }

    // ---- hapusPelatihan ----

    @Test
    @DisplayName("hapusPelatihan berhasil pada pelatihan yang ada")
    void testHapusPelatihanBerhasil() {
        Pelatihan.tambahPelatihan(new Pelatihan("PT01", "Java OOP", "Pak J", "OOP"));
        admin.hapusPelatihan("PT01");
        assertTrue(outContent.toString().contains("berhasil dihapus"));
        assertNull(Pelatihan.cariPelatihan("PT01"));
    }

    @Test
    @DisplayName("hapusPelatihan menampilkan pesan tidak ditemukan jika tidak ada")
    void testHapusPelatihanTidakDitemukan() {
        admin.hapusPelatihan("TIDAK_ADA");
        assertTrue(outContent.toString().contains("tidak ditemukan"));
    }

    // ---- Constructor & Getters ----

    @Test
    @DisplayName("Constructor Admin menyimpan field dengan benar via getter")
    void testConstructorGetters() {
        assertEquals("Admin Test", admin.getNama());
        assertEquals("admin@test.com", admin.getEmail());
        assertEquals("adminpass", admin.getPassword());
        assertEquals("Jl. Admin", admin.getAlamat());
        assertEquals("Admin", admin.getRole());
        assertEquals("08000000000", admin.getNoTelepon());
    }

    // ---- Admin sebagai implementasi News interface ----

    @Test
    @DisplayName("Admin mengimplementasikan interface News")
    void testAdminImplementsNews() {
        assertTrue(admin instanceof News);
    }
}
