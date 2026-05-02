package tp6103022330089.tubesdpbo;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Lowongan Tests")
class LowonganTest {

    @BeforeEach
    void setUp() {
        // Bersihkan daftar lowongan sebelum setiap test
        Lowongan.getDaftarLowongan().clear();
    }

    // ---- Constructor & Getter ----

    @Test
    @DisplayName("Constructor 3-arg: id, posisi, perusahaan tersimpan, deskripsi default kosong")
    void testConstructor3Arg() {
        Lowongan l = new Lowongan("L001", "Backend Dev", "PT Maju");
        assertEquals("L001", l.getId());
        assertEquals("Backend Dev", l.getPosisi());
        assertEquals("PT Maju", l.getPerusahaan());
        assertEquals("", l.getDeskripsi());
        assertNotNull(l.getPelamarList());
        assertTrue(l.getPelamarList().isEmpty());
    }

    @Test
    @DisplayName("Constructor 4-arg menyimpan semua field dengan benar")
    void testConstructor4Arg() {
        Lowongan l = new Lowongan("L002", "Frontend Dev", "PT Maju", "Deskripsi pekerjaan");
        assertEquals("L002", l.getId());
        assertEquals("Frontend Dev", l.getPosisi());
        assertEquals("PT Maju", l.getPerusahaan());
        assertEquals("Deskripsi pekerjaan", l.getDeskripsi());
    }

    // ---- tambahLowongan ----

    @Test
    @DisplayName("tambahLowongan menambah lowongan baru berhasil")
    void testTambahLowonganBerhasil() {
        Lowongan l = new Lowongan("L003", "Data Analyst", "PT Data");
        assertTrue(Lowongan.tambahLowongan(l));
        assertEquals(1, Lowongan.getDaftarLowongan().size());
    }

    @Test
    @DisplayName("tambahLowongan menolak duplikat ID")
    void testTambahLowonganDuplicateId() {
        Lowongan l1 = new Lowongan("L004", "PM", "PT A");
        Lowongan l2 = new Lowongan("L004", "PM Copy", "PT B");
        assertTrue(Lowongan.tambahLowongan(l1));
        assertFalse(Lowongan.tambahLowongan(l2));
        assertEquals(1, Lowongan.getDaftarLowongan().size());
    }

    @Test
    @DisplayName("tambahLowongan menolak null")
    void testTambahLowonganNull() {
        assertFalse(Lowongan.tambahLowongan(null));
        assertTrue(Lowongan.getDaftarLowongan().isEmpty());
    }

    // ---- cariById ----

    @Test
    @DisplayName("cariById menemukan lowongan yang ada")
    void testCariByIdDitemukan() {
        Lowongan l = new Lowongan("L005", "DevOps", "PT Cloud");
        Lowongan.tambahLowongan(l);
        Lowongan result = Lowongan.cariById("L005");
        assertNotNull(result);
        assertEquals("DevOps", result.getPosisi());
    }

    @Test
    @DisplayName("cariById case-insensitive")
    void testCariByIdCaseInsensitive() {
        Lowongan l = new Lowongan("LX01", "QA", "PT Q");
        Lowongan.tambahLowongan(l);
        assertNotNull(Lowongan.cariById("lx01"));
        assertNotNull(Lowongan.cariById("LX01"));
    }

    @Test
    @DisplayName("cariById mengembalikan null untuk ID tidak ada")
    void testCariByIdTidakDitemukan() {
        assertNull(Lowongan.cariById("TIDAK_ADA"));
    }

    // ---- melamar ----

    @Test
    @DisplayName("melamar pelamar baru berhasil")
    void testMelamarBerhasil() {
        Lowongan l = new Lowongan("L006", "SysAdmin", "PT Net");
        Pelamar p = new Pelamar("Ani", "ani@mail.com", "pass", "Jl. A", "Pelamar", "08111");
        assertTrue(l.melamar(p));
        assertEquals(1, l.getPelamarList().size());
    }

    @Test
    @DisplayName("melamar pelamar yang sama dua kali ditolak")
    void testMelamarDuplikat() {
        Lowongan l = new Lowongan("L007", "SysAdmin", "PT Net");
        Pelamar p = new Pelamar("Budi", "budi@mail.com", "pass", "Jl. B", "Pelamar", "08222");
        assertTrue(l.melamar(p));
        assertFalse(l.melamar(p));
        assertEquals(1, l.getPelamarList().size());
    }

    @Test
    @DisplayName("melamar null dikembalikan false")
    void testMelamarNull() {
        Lowongan l = new Lowongan("L008", "Dev", "PT D");
        assertFalse(l.melamar(null));
    }

    // ---- hapusPelamar ----

    @Test
    @DisplayName("hapusPelamar berdasarkan email berhasil")
    void testHapusPelamarByEmail() {
        Lowongan l = new Lowongan("L009", "Dev", "PT D");
        Pelamar p = new Pelamar("Cici", "cici@mail.com", "pass", "Jl. C", "Pelamar", "08333");
        l.melamar(p);
        assertTrue(l.hapusPelamar("cici@mail.com"));
        assertTrue(l.getPelamarList().isEmpty());
    }

    @Test
    @DisplayName("hapusPelamar berdasarkan nama berhasil")
    void testHapusPelamarByNama() {
        Lowongan l = new Lowongan("L010", "Dev", "PT D");
        Pelamar p = new Pelamar("Dodi", "dodi@mail.com", "pass", "Jl. D", "Pelamar", "08444");
        l.melamar(p);
        assertTrue(l.hapusPelamar("Dodi"));
        assertTrue(l.getPelamarList().isEmpty());
    }

    @Test
    @DisplayName("hapusPelamar yang tidak ada dikembalikan false")
    void testHapusPelamarTidakAda() {
        Lowongan l = new Lowongan("L011", "Dev", "PT D");
        assertFalse(l.hapusPelamar("tidakada@mail.com"));
    }

    // ---- equals & hashCode ----

    @Test
    @DisplayName("equals: dua lowongan dengan ID sama dianggap sama")
    void testEquals() {
        Lowongan l1 = new Lowongan("EQ01", "Dev", "PT A");
        Lowongan l2 = new Lowongan("EQ01", "DevOps", "PT B");
        assertEquals(l1, l2);
    }

    @Test
    @DisplayName("equals: dua lowongan dengan ID berbeda tidak sama")
    void testNotEquals() {
        Lowongan l1 = new Lowongan("EQ01", "Dev", "PT A");
        Lowongan l2 = new Lowongan("EQ02", "Dev", "PT A");
        assertNotEquals(l1, l2);
    }

    @Test
    @DisplayName("hashCode: lowongan dengan ID sama memiliki hashCode yang sama")
    void testHashCode() {
        Lowongan l1 = new Lowongan("HC01", "Dev", "PT A");
        Lowongan l2 = new Lowongan("hc01", "Dev", "PT A");
        assertEquals(l1.hashCode(), l2.hashCode());
    }

    // ---- getDaftarLowongan ----

    @Test
    @DisplayName("getDaftarLowongan mengembalikan list yang bisa dioperasikan")
    void testGetDaftarLowongan() {
        ArrayList<Lowongan> daftar = Lowongan.getDaftarLowongan();
        assertNotNull(daftar);
    }
}
