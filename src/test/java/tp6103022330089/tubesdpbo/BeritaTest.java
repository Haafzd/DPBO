package tp6103022330089.tubesdpbo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Berita Tests")
class BeritaTest {

    @Test
    @DisplayName("Constructor menyimpan id, judul, dan konten dengan benar")
    void testConstructorAndGetters() {
        Berita berita = new Berita("B001", "Judul Uji", "Konten berita uji");
        assertEquals("B001", berita.getId());
        assertEquals("Judul Uji", berita.getJudul());
        assertEquals("Konten berita uji", berita.getKonten());
    }

    @Test
    @DisplayName("getId mengembalikan id yang sesuai")
    void testGetId() {
        Berita berita = new Berita("ID-XYZ", "Judul", "Konten");
        assertEquals("ID-XYZ", berita.getId());
    }

    @Test
    @DisplayName("getJudul mengembalikan judul yang sesuai")
    void testGetJudul() {
        Berita berita = new Berita("1", "Judul Test", "isi");
        assertEquals("Judul Test", berita.getJudul());
    }

    @Test
    @DisplayName("getKonten mengembalikan konten yang sesuai")
    void testGetKonten() {
        Berita berita = new Berita("1", "Judul", "Ini konten berita");
        assertEquals("Ini konten berita", berita.getKonten());
    }
}
