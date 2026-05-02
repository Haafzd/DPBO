package tp6103022330089.tubesdpbo;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pelatihan Tests")
class PelatihanTest {

    @BeforeEach
    void setUp() {
        Pelatihan.getDaftarPelatihan().clear();
    }

    // ---- Constructor & Getter ----

    @Test
    @DisplayName("Constructor 3-arg: judul menjadi id, judul, pemateri, deskripsi tersimpan")
    void testConstructor3Arg() {
        Pelatihan p = new Pelatihan("Java Dasar", "Pak Budi", "Belajar Java");
        assertEquals("Java Dasar", p.getId());  // id = judul pada konstruktor ini
        assertEquals("Java Dasar", p.getJudul());
        assertEquals("Pak Budi", p.getPemateri());
        assertEquals("Belajar Java", p.getDeskripsi());
        assertNotNull(p.getDaftarPelamar());
        assertTrue(p.getDaftarPelamar().isEmpty());
    }

    @Test
    @DisplayName("Constructor 4-arg menyimpan id terpisah dari judul")
    void testConstructor4Arg() {
        Pelatihan p = new Pelatihan("P001", "Python Lanjut", "Bu Ani", "Deskripsi Python");
        assertEquals("P001", p.getId());
        assertEquals("Python Lanjut", p.getJudul());
        assertEquals("Bu Ani", p.getPemateri());
        assertEquals("Deskripsi Python", p.getDeskripsi());
    }

    // ---- tambahPelatihan ----

    @Test
    @DisplayName("tambahPelatihan berhasil menambah pelatihan baru")
    void testTambahPelatihanBerhasil() {
        Pelatihan p = new Pelatihan("P002", "Web Dev", "Pak Cici", "HTML CSS JS");
        assertTrue(Pelatihan.tambahPelatihan(p));
        assertEquals(1, Pelatihan.getDaftarPelatihan().size());
    }

    @Test
    @DisplayName("tambahPelatihan menolak duplikat berdasarkan ID")
    void testTambahPelatihanDuplicateId() {
        Pelatihan p1 = new Pelatihan("P003", "Android Dev", "Pak A", "Kotlin");
        Pelatihan p2 = new Pelatihan("P003", "iOS Dev", "Pak B", "Swift");
        assertTrue(Pelatihan.tambahPelatihan(p1));
        assertFalse(Pelatihan.tambahPelatihan(p2));
        assertEquals(1, Pelatihan.getDaftarPelatihan().size());
    }

    @Test
    @DisplayName("tambahPelatihan menolak duplikat berdasarkan judul")
    void testTambahPelatihanDuplicateJudul() {
        Pelatihan p1 = new Pelatihan("P004", "Data Science", "Pak D", "Data");
        Pelatihan p2 = new Pelatihan("P005", "Data Science", "Pak E", "ML");
        assertTrue(Pelatihan.tambahPelatihan(p1));
        assertFalse(Pelatihan.tambahPelatihan(p2));
    }

    @Test
    @DisplayName("tambahPelatihan menolak null")
    void testTambahPelatihanNull() {
        assertFalse(Pelatihan.tambahPelatihan(null));
    }

    // ---- cariPelatihan ----

    @Test
    @DisplayName("cariPelatihan menemukan berdasarkan ID")
    void testCariPelatihanById() {
        Pelatihan p = new Pelatihan("P006", "Cloud Computing", "Pak F", "AWS");
        Pelatihan.tambahPelatihan(p);
        assertNotNull(Pelatihan.cariPelatihan("P006"));
    }

    @Test
    @DisplayName("cariPelatihan menemukan berdasarkan judul")
    void testCariPelatihanByJudul() {
        Pelatihan p = new Pelatihan("P007", "DevSecOps", "Pak G", "Security");
        Pelatihan.tambahPelatihan(p);
        assertNotNull(Pelatihan.cariPelatihan("DevSecOps"));
    }

    @Test
    @DisplayName("cariPelatihan case-insensitive")
    void testCariPelatihanCaseInsensitive() {
        Pelatihan p = new Pelatihan("P008", "Kubernetes", "Pak H", "K8s");
        Pelatihan.tambahPelatihan(p);
        assertNotNull(Pelatihan.cariPelatihan("kubernetes"));
        assertNotNull(Pelatihan.cariPelatihan("p008"));
    }

    @Test
    @DisplayName("cariPelatihan mengembalikan null jika tidak ditemukan")
    void testCariPelatihanTidakDitemukan() {
        assertNull(Pelatihan.cariPelatihan("TIDAK_ADA"));
    }

    // ---- hapusPelatihan ----

    @Test
    @DisplayName("hapusPelatihan berdasarkan ID berhasil")
    void testHapusPelatihanById() {
        Pelatihan p = new Pelatihan("P009", "React", "Pak I", "Frontend");
        Pelatihan.tambahPelatihan(p);
        assertTrue(Pelatihan.hapusPelatihan("P009"));
        assertTrue(Pelatihan.getDaftarPelatihan().isEmpty());
    }

    @Test
    @DisplayName("hapusPelatihan berdasarkan judul berhasil")
    void testHapusPelatihanByJudul() {
        Pelatihan p = new Pelatihan("P010", "Vue JS", "Pak J", "Vue");
        Pelatihan.tambahPelatihan(p);
        assertTrue(Pelatihan.hapusPelatihan("Vue JS"));
        assertTrue(Pelatihan.getDaftarPelatihan().isEmpty());
    }

    @Test
    @DisplayName("hapusPelatihan yang tidak ada dikembalikan false")
    void testHapusPelatihanTidakAda() {
        assertFalse(Pelatihan.hapusPelatihan("TIDAK_ADA"));
    }

    // ---- daftarPelamar (instance method) ----

    @Test
    @DisplayName("daftarPelamar mendaftarkan pelamar baru berhasil")
    void testDaftarPelamarBerhasil() {
        Pelatihan p = new Pelatihan("P011", "Angular", "Pak K", "TS");
        Pelamar pelamar = new Pelamar("Eko", "eko@mail.com", "pass", "Jl E", "Pelamar", "08555");
        assertTrue(p.daftarPelamar(pelamar));
        assertEquals(1, p.getDaftarPelamar().size());
    }

    @Test
    @DisplayName("daftarPelamar menolak pelamar yang sudah terdaftar")
    void testDaftarPelamarDuplikat() {
        Pelatihan p = new Pelatihan("P012", "Spring Boot", "Pak L", "Java Spring");
        Pelamar pelamar = new Pelamar("Fani", "fani@mail.com", "pass", "Jl F", "Pelamar", "08666");
        assertTrue(p.daftarPelamar(pelamar));
        assertFalse(p.daftarPelamar(pelamar));
        assertEquals(1, p.getDaftarPelamar().size());
    }

    @Test
    @DisplayName("daftarPelamar menolak null")
    void testDaftarPelamarNull() {
        Pelatihan p = new Pelatihan("P013", "Hibernate", "Pak M", "ORM");
        assertFalse(p.daftarPelamar(null));
    }

    // ---- getDaftarPelatihan ----

    @Test
    @DisplayName("getDaftarPelatihan mengembalikan list yang tidak null")
    void testGetDaftarPelatihan() {
        ArrayList<Pelatihan> daftar = Pelatihan.getDaftarPelatihan();
        assertNotNull(daftar);
    }
}
