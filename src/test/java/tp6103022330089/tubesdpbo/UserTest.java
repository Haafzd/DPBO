package tp6103022330089.tubesdpbo;

import org.junit.jupiter.api.*;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Tests")
class UserTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        User.database.clear();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    // ---- Constructor & Getter/Setter ----

    @Test
    @DisplayName("Getter mengembalikan nilai yang diset konstruktor")
    void testGetters() {
        Pelamar p = new Pelamar("Andi", "andi@mail.com", "pass123", "Jl. A No.1", "Pelamar", "08100001111");
        assertEquals("Andi", p.getNama());
        assertEquals("andi@mail.com", p.getEmail());
        assertEquals("pass123", p.getPassword());
        assertEquals("Jl. A No.1", p.getAlamat());
        assertEquals("Pelamar", p.getRole());
        assertEquals("08100001111", p.getNoTelepon());
    }

    @Test
    @DisplayName("Setter mengubah nilai field dengan benar")
    void testSetters() {
        Pelamar p = new Pelamar("Budi", "budi@mail.com", "pass", "Jl. B", "Pelamar", "082");
        p.setNama("Budi Santoso");
        p.setEmail("budi.s@mail.com");
        p.setPassword("newpass");
        p.setAlamat("Jl. Baru");
        p.setRole("Penyedia");
        p.setNoTelepon("08999");
        assertEquals("Budi Santoso", p.getNama());
        assertEquals("budi.s@mail.com", p.getEmail());
        assertEquals("newpass", p.getPassword());
        assertEquals("Jl. Baru", p.getAlamat());
        assertEquals("Penyedia", p.getRole());
        assertEquals("08999", p.getNoTelepon());
    }

    // ---- isEmailTerdaftar ----

    @Test
    @DisplayName("isEmailTerdaftar mengembalikan false jika database kosong")
    void testIsEmailTerdaftarEmpty() {
        assertFalse(User.isEmailTerdaftar("test@mail.com"));
    }

    @Test
    @DisplayName("isEmailTerdaftar mengembalikan true untuk email terdaftar")
    void testIsEmailTerdaftarTrue() {
        Pelamar p = new Pelamar("Cici", "cici@mail.com", "pass", "Jl. C", "Pelamar", "083");
        User.database.add(p);
        assertTrue(User.isEmailTerdaftar("cici@mail.com"));
    }

    @Test
    @DisplayName("isEmailTerdaftar case-insensitive")
    void testIsEmailTerdaftarCaseInsensitive() {
        Pelamar p = new Pelamar("Dodi", "dodi@mail.com", "pass", "Jl. D", "Pelamar", "084");
        User.database.add(p);
        assertTrue(User.isEmailTerdaftar("DODI@MAIL.COM"));
    }

    @Test
    @DisplayName("isEmailTerdaftar mengembalikan false untuk email tidak terdaftar")
    void testIsEmailTerdaftarFalse() {
        Pelamar p = new Pelamar("Eka", "eka@mail.com", "pass", "Jl. E", "Pelamar", "085");
        User.database.add(p);
        assertFalse(User.isEmailTerdaftar("tidakada@mail.com"));
    }

    // ---- cariUserByLogin ----

    @Test
    @DisplayName("cariUserByLogin menemukan user dengan email dan password yang cocok")
    void testCariUserByLoginBerhasil() {
        Pelamar p = new Pelamar("Fani", "fani@mail.com", "mypass", "Jl. F", "Pelamar", "086");
        User.database.add(p);
        User result = User.cariUserByLogin("fani@mail.com", "mypass");
        assertNotNull(result);
        assertEquals("Fani", result.getNama());
    }

    @Test
    @DisplayName("cariUserByLogin mengembalikan null jika password salah")
    void testCariUserByLoginPasswordSalah() {
        Pelamar p = new Pelamar("Gina", "gina@mail.com", "benar", "Jl. G", "Pelamar", "087");
        User.database.add(p);
        assertNull(User.cariUserByLogin("gina@mail.com", "salah"));
    }

    @Test
    @DisplayName("cariUserByLogin mengembalikan null jika email tidak ada")
    void testCariUserByLoginEmailTidakAda() {
        assertNull(User.cariUserByLogin("tidakada@mail.com", "pass"));
    }

    // ---- login ----

    @Test
    @DisplayName("login berhasil dengan kredensial yang benar")
    void testLoginBerhasil() {
        Pelamar p = new Pelamar("Hana", "hana@mail.com", "hanapass", "Jl. H", "Pelamar", "088");
        User.database.add(p);
        assertTrue(p.login("hana@mail.com", "hanapass"));
    }

    @Test
    @DisplayName("login gagal dengan password salah")
    void testLoginGagal() {
        Pelamar p = new Pelamar("Intan", "intan@mail.com", "intanpass", "Jl. I", "Pelamar", "089");
        User.database.add(p);
        assertFalse(p.login("intan@mail.com", "salah"));
    }

    // ---- showAllUsers ----

    @Test
    @DisplayName("showAllUsers menampilkan pesan jika database kosong")
    void testShowAllUsersEmpty() {
        User.showAllUsers();
        assertTrue(outContent.toString().contains("Tidak ada pengguna di database."));
    }

    @Test
    @DisplayName("showAllUsers menampilkan data user yang ada")
    void testShowAllUsers() {
        Pelamar p = new Pelamar("Joko", "joko@mail.com", "pass", "Jl. J", "Pelamar", "090");
        User.database.add(p);
        User.showAllUsers();
        String output = outContent.toString();
        assertTrue(output.contains("Joko"));
        assertTrue(output.contains("joko@mail.com"));
        assertTrue(output.contains("Pelamar"));
    }

    // ---- hapusUser (via Y/N input) ----

    @Test
    @DisplayName("hapusUser: user tidak ada menampilkan pesan tidak ditemukan")
    void testHapusUserTidakAda() {
        // Tidak perlu input scanner karena user tidak ditemukan
        User.hapusUser("TidakAda", "tidakada@mail.com");
        assertTrue(outContent.toString().contains("tidak ditemukan"));
    }
}
