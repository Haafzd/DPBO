package tp6103022330089.tubesdpbo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ScannerUtil Tests")
class ScannerUtilTest {

    // ---- isValidEmail ----

    @Test
    @DisplayName("Email valid dikembalikan true")
    void testValidEmail() {
        assertTrue(ScannerUtil.isValidEmail("user@example.com"));
    }

    @Test
    @DisplayName("Email dengan subdomain valid")
    void testValidEmailSubdomain() {
        assertTrue(ScannerUtil.isValidEmail("name@mail.co.id"));
    }

    @Test
    @DisplayName("Email tanpa @ dikembalikan false")
    void testInvalidEmailNoAt() {
        assertFalse(ScannerUtil.isValidEmail("userexample.com"));
    }

    @Test
    @DisplayName("Email tanpa domain dikembalikan false")
    void testInvalidEmailNoDomain() {
        assertFalse(ScannerUtil.isValidEmail("user@"));
    }

    @Test
    @DisplayName("Email null dikembalikan false")
    void testNullEmail() {
        assertFalse(ScannerUtil.isValidEmail(null));
    }

    @Test
    @DisplayName("Email string kosong dikembalikan false")
    void testEmptyEmail() {
        assertFalse(ScannerUtil.isValidEmail(""));
    }

    @Test
    @DisplayName("Email dengan spasi dikembalikan false")
    void testEmailWithSpaces() {
        assertFalse(ScannerUtil.isValidEmail("us er@example.com"));
    }

    // ---- scanString ----

    @Test
    @DisplayName("scanString membaca satu baris dan trim whitespace")
    void testScanString() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("  hello world  \n".getBytes()));
        String result = ScannerUtil.scanString(scanner);
        assertEquals("hello world", result);
    }

    // ---- scanInt ----

    @Test
    @DisplayName("scanInt membaca angka integer yang valid")
    void testScanInt() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("42\n".getBytes()));
        int result = ScannerUtil.scanInt(scanner);
        assertEquals(42, result);
    }

    @Test
    @DisplayName("scanInt melewati input tidak valid lalu membaca angka benar")
    void testScanIntSkipsInvalid() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("abc\n5\n".getBytes()));
        int result = ScannerUtil.scanInt(scanner);
        assertEquals(5, result);
    }

    // ---- scanChar ----

    @Test
    @DisplayName("scanChar mengembalikan 'Y' untuk input y (case-insensitive)")
    void testScanCharY() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("y\n".getBytes()));
        char result = ScannerUtil.scanChar(scanner);
        assertEquals('Y', result);
    }

    @Test
    @DisplayName("scanChar mengembalikan 'N' untuk input n (case-insensitive)")
    void testScanCharN() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("n\n".getBytes()));
        char result = ScannerUtil.scanChar(scanner);
        assertEquals('N', result);
    }

    @Test
    @DisplayName("scanChar melewati input tidak valid lalu menerima Y")
    void testScanCharSkipsInvalid() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("x\nY\n".getBytes()));
        char result = ScannerUtil.scanChar(scanner);
        assertEquals('Y', result);
    }
}
