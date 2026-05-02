package tp6103022330089.tubesdpbo;

import java.util.Scanner;

public class MenuUtil {
    public static void menuUtama() {
        Scanner scan = User.scan;
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n---- Menu Utama ----");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            int pilihanMenu = ScannerUtil.scanInt(scan);

            switch (pilihanMenu) {
                case 1 -> prosesLogin(scan);
                case 2 -> prosesRegister(scan);
                case 3 -> {
                    System.out.println("Terima kasih! Keluar dari program.");
                    isRunning = false;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void prosesLogin(Scanner scan) {
        System.out.print("Masukkan email: ");
        String email = ScannerUtil.scanString(scan);
        System.out.print("Masukkan password: ");
        String password = ScannerUtil.scanString(scan);

        User currentUser = User.cariUserByLogin(email, password);
        if (currentUser == null) {
            System.out.println("Login gagal, email atau password salah.");
            return;
        }

        System.out.println("Login berhasil, selamat datang " + currentUser.getNama() + ".");
        switch (currentUser) {
            case Admin admin -> menuAdmin(scan, admin);
            case Pelamar pelamar -> menuPelamar(scan, pelamar);
            case PembukaLowongan pembukaLowongan -> menuPenyedia(scan, pembukaLowongan);
            default -> {
            }
        }
    }

    private static void prosesRegister(Scanner scan) {
        System.out.println("1. Register sebagai Pelamar");
        System.out.println("2. Register sebagai Pembuka Lowongan");
        System.out.print("Pilih role yang ingin didaftarkan: ");
        int pilihanRegister = ScannerUtil.scanInt(scan);

        if (pilihanRegister != 1 && pilihanRegister != 2) {
            System.out.println("Pilihan role tidak valid.");
            return;
        }

        System.out.print("Masukkan nama: ");
        String nama = ScannerUtil.scanString(scan);
        System.out.print("Masukkan email: ");
        String email = ScannerUtil.scanString(scan);
        System.out.print("Masukkan password: ");
        String password = ScannerUtil.scanString(scan);
        System.out.print("Masukkan alamat: ");
        String alamat = ScannerUtil.scanString(scan);
        System.out.print("Masukkan nomor telepon: ");
        String noTelepon = ScannerUtil.scanString(scan);

        if (pilihanRegister == 1) {
            new Pelamar(nama, email, password, alamat, "Pelamar", noTelepon)
                    .register(nama, email, password, alamat, noTelepon);
        } else {
            new PembukaLowongan(nama, email, password, alamat, "Penyedia", noTelepon)
                    .register(nama, email, password, alamat, noTelepon);
        }
    }

    public static void menuAdmin(Scanner scan, Admin admin) {
        boolean lanjut = true;
        while (lanjut) {
            System.out.println("\n---- Menu Admin ----");
            System.out.println("1. Tambah Berita");
            System.out.println("2. Hapus Berita");
            System.out.println("3. Lihat Berita");
            System.out.println("4. Tambah Pelatihan");
            System.out.println("5. Hapus Pelatihan");
            System.out.println("6. Lihat Pelatihan");
            System.out.println("7. Hapus User");
            System.out.println("8. User yang terdaftar");
            System.out.println("9. Logout");
            System.out.print("Pilih menu: ");
            int pilihan = ScannerUtil.scanInt(scan);

            switch (pilihan) {
                case 1 -> admin.addNews();
                case 2 -> {
                    System.out.print("Masukkan ID Berita yang ingin dihapus: ");
                    admin.deleteNews(ScannerUtil.scanString(scan));
                }
                case 3 -> admin.displayNews();
                case 4 -> admin.tambahPelatihan();
                case 5 -> {
                    System.out.print("Masukkan ID/Judul Pelatihan yang ingin dihapus: ");
                    admin.hapusPelatihan(ScannerUtil.scanString(scan));
                }
                case 6 -> Pelatihan.displayPelatihan();
                case 7 -> {
                    System.out.print("Masukkan nama user yang ingin dihapus: ");
                    String namaUserHapus = ScannerUtil.scanString(scan);
                    System.out.print("Masukkan email user yang ingin dihapus: ");
                    String emailUserHapus = ScannerUtil.scanString(scan);
                    User.hapusUser(namaUserHapus, emailUserHapus);
                }
                case 8 -> User.showAllUsers();
                case 9 -> lanjut = false;
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void menuPelamar(Scanner scan, Pelamar pelamar) {
        boolean lanjut = true;
        while (lanjut) {
            System.out.println("\n---- Menu Pelamar ----");
            System.out.println("1. Melihat Lowongan");
            System.out.println("2. Melamar Lowongan");
            System.out.println("3. Melihat Daftar Pelatihan");
            System.out.println("4. Mendaftar Pelatihan");
            System.out.println("5. Logout");
            System.out.print("Pilih menu: ");
            int pilihan = ScannerUtil.scanInt(scan);

            switch (pilihan) {
                case 1 -> Lowongan.showLowongan();
                case 2 -> {
                    Lowongan.showLowongan();
                    System.out.print("Masukkan ID Lowongan: ");
                    pelamar.melamarLowongan(ScannerUtil.scanString(scan));
                }
                case 3 -> Pelatihan.displayPelatihan();
                case 4 -> {
                    Pelatihan.displayPelatihan();
                    System.out.print("Masukkan ID/Judul Pelatihan: ");
                    pelamar.mendaftarPelatihan(ScannerUtil.scanString(scan));
                }
                case 5 -> lanjut = false;
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void menuPenyedia(Scanner scan, PembukaLowongan penyedia) {
        boolean lanjut = true;
        while (lanjut) {
            System.out.println("\n---- Menu Penyedia Lowongan ----");
            System.out.println("1. Membuat Lowongan");
            System.out.println("2. Menolak Pelamar");
            System.out.println("3. Melihat Daftar Pelamar");
            System.out.println("4. Melihat Daftar Lowongan yang Saya Buat");
            System.out.println("5. Logout");
            System.out.print("Pilih menu: ");
            int pilihan = ScannerUtil.scanInt(scan);

            switch (pilihan) {
                case 1 -> penyedia.membuatLowongan();
                case 2 -> {
                    penyedia.showLowongan();
                    System.out.print("Masukkan ID Lowongan: ");
                    String idLowongan = ScannerUtil.scanString(scan);
                    System.out.print("Masukkan nama/email pelamar yang akan ditolak: ");
                    String identitasPelamar = ScannerUtil.scanString(scan);
                    penyedia.menolakPelamar(idLowongan, identitasPelamar);
                }
                case 3 -> penyedia.melihatDaftarPelamar();
                case 4 -> penyedia.showLowongan();
                case 5 -> lanjut = false;
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
