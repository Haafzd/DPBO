package tp6103022330089.tubesdpbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PembukaLowongan extends User {
    private ArrayList<Lowongan> lowonganYangDibuat;

    public PembukaLowongan(String nama, String email, String password, String alamat, String role, String noTelepon) {
        super(nama, email, password, alamat, role, noTelepon);
        this.lowonganYangDibuat = new ArrayList<>();
    }

    @Override
    public void register(String nama, String email, String password, String alamat, String noTelepon) {
        if (!ScannerUtil.isValidEmail(email)) {
            System.out.println("Email tidak valid. Registrasi dibatalkan.");
            return;
        }
        if (User.isEmailTerdaftar(email)) {
            System.out.println("Email sudah terdaftar. Registrasi dibatalkan.");
            return;
        }
        User.database.add(new PembukaLowongan(nama, email, password, alamat, "Penyedia", noTelepon));
        System.out.println("Penyedia telah berhasil didaftarkan.");
    }

    public void membuatLowongan() {
        Scanner scan = User.scan;
        System.out.print("Masukkan ID Lowongan: ");
        String idLowongan = ScannerUtil.scanString(scan);
        if (Lowongan.cariById(idLowongan) != null) {
            System.out.println("Lowongan dengan ID " + idLowongan + " sudah ada.");
            return;
        }

        System.out.print("Masukkan Posisi Lowongan: ");
        String posisi = ScannerUtil.scanString(scan);
        System.out.print("Masukkan Deskripsi Lowongan: ");
        String deskripsi = ScannerUtil.scanString(scan);

        Lowongan lowongan = new Lowongan(idLowongan, posisi, this.getNama(), deskripsi);
        lowonganYangDibuat.add(lowongan);
        Lowongan.tambahLowongan(lowongan);
        System.out.println("Lowongan pekerjaan dengan ID " + idLowongan + " telah dibuat.");
    }

    public void melihatDaftarPelamar() {
        if (lowonganYangDibuat.isEmpty()) {
            System.out.println("Belum ada lowongan yang dibuat.");
            return;
        }

        System.out.println("Daftar Lowongan yang Anda Buat:");
        for (int i = 0; i < lowonganYangDibuat.size(); i++) {
            Lowongan lowongan = lowonganYangDibuat.get(i);
            System.out.println((i + 1) + ". " + lowongan.getPosisi()
                    + " | ID: " + lowongan.getId()
                    + " | Jumlah Pelamar: " + lowongan.getPelamarList().size());
        }

        System.out.print("Pilih nomor lowongan untuk melihat daftar pelamar: ");
        int pilihan = ScannerUtil.scanInt(User.scan);
        if (pilihan < 1 || pilihan > lowonganYangDibuat.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        Lowongan lowonganDipilih = lowonganYangDibuat.get(pilihan - 1);
        System.out.println("Daftar Pelamar untuk Lowongan: " + lowonganDipilih.getPosisi());
        lowonganDipilih.tampilkanPelamar();
    }

    // Kompatibilitas dengan versi lama. Parameter tidak dipakai lagi karena sumber data yang benar adalah lowonganYangDibuat.
    public void melihatDaftarPelamar(HashMap<Lowongan, ArrayList<Pelamar>> daftarPelamarLowongan) {
        melihatDaftarPelamar();
    }

    public void menolakPelamar(Pelamar pelamar) {
        if (pelamar == null) {
            System.out.println("Pelamar tidak ditemukan.");
            return;
        }

        boolean terhapus = false;
        for (Lowongan lowongan : lowonganYangDibuat) {
            if (lowongan.hapusPelamar(pelamar.getEmail())) {
                terhapus = true;
            }
        }

        if (terhapus) {
            System.out.println("Pelamar " + pelamar.getNama() + " ditolak.");
        } else {
            System.out.println("Pelamar tidak ditemukan pada lowongan yang Anda buat.");
        }
    }

    public void menolakPelamar(String idLowongan, String identitasPelamar) {
        Lowongan lowongan = null;
        for (Lowongan l : lowonganYangDibuat) {
            if (l.getId().equalsIgnoreCase(idLowongan)) {
                lowongan = l;
                break;
            }
        }

        if (lowongan == null) {
            System.out.println("Lowongan dengan ID " + idLowongan + " tidak ditemukan pada akun Anda.");
            return;
        }

        if (lowongan.hapusPelamar(identitasPelamar)) {
            System.out.println("Pelamar " + identitasPelamar + " ditolak dari lowongan " + lowongan.getPosisi() + ".");
        } else {
            System.out.println("Pelamar " + identitasPelamar + " tidak ditemukan pada lowongan tersebut.");
        }
    }

    public void showLowongan() {
        if (lowonganYangDibuat.isEmpty()) {
            System.out.println("Tidak ada lowongan tersedia.");
            return;
        }
        System.out.println("Daftar Lowongan Kerja yang Anda Buat:");
        for (Lowongan lowongan : lowonganYangDibuat) {
            System.out.println("ID: " + lowongan.getId()
                    + " | Posisi: " + lowongan.getPosisi()
                    + " | Perusahaan: " + lowongan.getPerusahaan()
                    + " | Deskripsi: " + lowongan.getDeskripsi()
                    + " | Jumlah Pelamar: " + lowongan.getPelamarList().size());
        }
    }
}
