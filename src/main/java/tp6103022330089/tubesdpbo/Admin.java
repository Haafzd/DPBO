package tp6103022330089.tubesdpbo;

import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User implements News {
    private final static ArrayList<Berita> daftarBerita = new ArrayList<>();

    public Admin(String nama, String email, String password, String alamat, String role, String noTelepon) {
        super(nama, email, password, alamat, role, noTelepon);
    }

    @Override
    public void register(String nama, String email, String password, String alamat, String noTelepon) {
        System.out.println("Admin tidak perlu melakukan registrasi.");
    }

    @Override
    public void addNews() {
        Scanner input = User.scan;
        System.out.print("Masukkan ID berita: ");
        String id = ScannerUtil.scanString(input);
        if (cariBerita(id) != null) {
            System.out.println("Berita dengan ID " + id + " sudah ada.");
            return;
        }
        System.out.print("Masukkan judul berita: ");
        String judul = ScannerUtil.scanString(input);
        System.out.print("Masukkan konten berita: ");
        String konten = ScannerUtil.scanString(input);
        Berita beritaBaru = new Berita(id, judul, konten);
        daftarBerita.add(beritaBaru);
        System.out.println("Berita dengan judul " + beritaBaru.getJudul() + " telah ditambahkan.");
    }

    private Berita cariBerita(String id) {
        for (Berita berita : daftarBerita) {
            if (berita.getId().equalsIgnoreCase(id)) {
                return berita;
            }
        }
        return null;
    }

    @Override
    public void deleteNews(String id) {
        for (int i = 0; i < daftarBerita.size(); i++) {
            if (daftarBerita.get(i).getId().equalsIgnoreCase(id)) {
                daftarBerita.remove(i);
                System.out.println("Berita dengan ID " + id + " berhasil dihapus.");
                return;
            }
        }
        System.out.println("Berita dengan ID " + id + " tidak ditemukan.");
    }

    @Override
    public void displayNews() {
        if (daftarBerita.isEmpty()) {
            System.out.println("Tidak ada berita terbaru.");
            return;
        }
        System.out.println("---- Berita Terbaru ----");
        for (Berita berita : daftarBerita) {
            System.out.println("ID: " + berita.getId()
                    + " | Judul: " + berita.getJudul()
                    + "\nKonten: " + berita.getKonten() + "\n");
        }
    }

    public void tambahPelatihan() {
        Scanner scanner = User.scan;
        System.out.print("Masukkan ID Pelatihan: ");
        String idPelatihan = ScannerUtil.scanString(scanner);
        System.out.print("Masukkan Judul Pelatihan: ");
        String judulPelatihan = ScannerUtil.scanString(scanner);
        System.out.print("Masukkan Pemateri: ");
        String pemateri = ScannerUtil.scanString(scanner);
        System.out.print("Masukkan Deskripsi Pelatihan: ");
        String deskripsiPelatihan = ScannerUtil.scanString(scanner);

        Pelatihan pelatihanBaru = new Pelatihan(idPelatihan, judulPelatihan, pemateri, deskripsiPelatihan);
        if (Pelatihan.tambahPelatihan(pelatihanBaru)) {
            System.out.println("Pelatihan berhasil ditambahkan: " + judulPelatihan);
        } else {
            System.out.println("Pelatihan dengan ID/judul tersebut sudah ada.");
        }
    }

    public void hapusPelatihan(String idAtauJudulPelatihan) {
        if (Pelatihan.hapusPelatihan(idAtauJudulPelatihan)) {
            System.out.println("Pelatihan " + idAtauJudulPelatihan + " berhasil dihapus.");
        } else {
            System.out.println("Pelatihan " + idAtauJudulPelatihan + " tidak ditemukan.");
        }
    }
}
