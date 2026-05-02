package tp6103022330089.tubesdpbo;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pelamar Tests")
class PelamarTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        User.database.clear();
        Lowongan.getDaftarLowongan().clear();
        Pelatihan.getDaftarPelatihan().clear();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    // ---- register ----

    @Test
    @DisplayName("register berhasil dengan email valid dan belum terdaftar")
    void testRegisterBerhasil() {
        Pelamar p = new Pelamar("Template", "tmp@mail.com", "pass", "Jl", "Pelamar", "0");
        p.register("Andi", "andi@mail.com", "pass", "Jl. A", "08100001111");
        assertTrue(outContent.toString().contains("berhasil didaftarkan"));
        assertTrue(User.isEmailTerdaftar("andi@mail.com"));
    }

    @Test
    @DisplayName("register gagal jika email tidak valid")
    void testRegisterEmailTidakValid() {
        Pelamar p = new Pelamar("Template", "tmp@mail.com", "pass", "Jl", "Pelamar", "0");
        p.register("Budi", "budi-bukan-email", "pass", "Jl. B", "082");
        assertTrue(outContent.toString().contains("Email tidak valid"));
        assertFalse(User.isEmailTerdaftar("budi-bukan-email"));
    }

    @Test
    @DisplayName("register gagal jika email sudah terdaftar")
    void testRegisterEmailSudahTerdaftar() {
        User.database.add(new Pelamar("Existing", "exist@mail.com", "pass", "Jl.", "Pelamar", "0"));
        Pelamar p = new Pelamar("Template", "tmp@mail.com", "pass", "Jl", "Pelamar", "0");
        p.register("Cici", "exist@mail.com", "pass", "Jl. C", "083");
        assertTrue(outContent.toString().contains("Email sudah terdaftar"));
    }

    // ---- melamarLowongan(String idLowongan) ----

    @Test
    @DisplayName("melamarLowongan berhasil pada lowongan yang ada")
    void testMelamarLowonganBerhasil() {
        Pelamar p = new Pelamar("Dodi", "dodi@mail.com", "pass", "Jl. D", "Pelamar", "084");
        Lowongan.tambahLowongan(new Lowongan("L001", "Backend Dev", "PT Maju"));
        p.melamarLowongan("L001");
        assertTrue(outContent.toString().contains("telah melamar lowongan"));
    }

    @Test
    @DisplayName("melamarLowongan gagal jika ID lowongan tidak ada")
    void testMelamarLowonganTidakAda() {
        Pelamar p = new Pelamar("Eka", "eka@mail.com", "pass", "Jl. E", "Pelamar", "085");
        p.melamarLowongan("TIDAK_ADA");
        assertTrue(outContent.toString().contains("tidak ditemukan"));
    }

    @Test
    @DisplayName("melamarLowongan gagal jika sudah pernah melamar")
    void testMelamarLowonganDuplikat() {
        Pelamar p = new Pelamar("Fani", "fani@mail.com", "pass", "Jl. F", "Pelamar", "086");
        Lowongan.tambahLowongan(new Lowongan("L002", "Frontend Dev", "PT Maju"));
        p.melamarLowongan("L002");
        outContent.reset();
        p.melamarLowongan("L002");
        assertTrue(outContent.toString().contains("sudah melamar"));
    }

    // ---- melamarLowongan(String email, String idLowongan) ----

    @Test
    @DisplayName("melamarLowongan dengan email yang sesuai berhasil")
    void testMelamarLowonganDenganEmailSesuai() {
        Pelamar p = new Pelamar("Gina", "gina@mail.com", "pass", "Jl. G", "Pelamar", "087");
        Lowongan.tambahLowongan(new Lowongan("L003", "QA", "PT Q"));
        p.melamarLowongan("gina@mail.com", "L003");
        assertTrue(outContent.toString().contains("telah melamar lowongan"));
    }

    @Test
    @DisplayName("melamarLowongan dengan email tidak sesuai ditolak")
    void testMelamarLowonganDenganEmailTidakSesuai() {
        Pelamar p = new Pelamar("Hana", "hana@mail.com", "pass", "Jl. H", "Pelamar", "088");
        Lowongan.tambahLowongan(new Lowongan("L004", "DevOps", "PT D"));
        p.melamarLowongan("orang-lain@mail.com", "L004");
        assertTrue(outContent.toString().contains("tidak sesuai"));
    }

    // ---- mendaftarPelatihan ----

    @Test
    @DisplayName("mendaftarPelatihan berhasil pada pelatihan yang ada")
    void testMendaftarPelatihanBerhasil() {
        Pelamar p = new Pelamar("Intan", "intan@mail.com", "pass", "Jl. I", "Pelamar", "089");
        Pelatihan.tambahPelatihan(new Pelatihan("PT01", "Java OOP", "Pak J", "OOP"));
        p.mendaftarPelatihan("PT01");
        assertTrue(outContent.toString().contains("berhasil mendaftar pelatihan"));
    }

    @Test
    @DisplayName("mendaftarPelatihan gagal jika pelatihan tidak ada")
    void testMendaftarPelatihanTidakAda() {
        Pelamar p = new Pelamar("Joko", "joko@mail.com", "pass", "Jl. J", "Pelamar", "090");
        p.mendaftarPelatihan("TIDAK_ADA");
        assertTrue(outContent.toString().contains("tidak ditemukan"));
    }

    @Test
    @DisplayName("mendaftarPelatihan gagal jika sudah terdaftar")
    void testMendaftarPelatihanDuplikat() {
        Pelamar p = new Pelamar("Kiki", "kiki@mail.com", "pass", "Jl. K", "Pelamar", "091");
        Pelatihan.tambahPelatihan(new Pelatihan("PT02", "Spring Boot", "Pak S", "Spring"));
        p.mendaftarPelatihan("PT02");
        outContent.reset();
        p.mendaftarPelatihan("PT02");
        assertTrue(outContent.toString().contains("sudah terdaftar"));
    }
}
