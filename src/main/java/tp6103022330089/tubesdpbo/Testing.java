package tp6103022330089.tubesdpbo;

import static tp6103022330089.tubesdpbo.User.database;

public class Testing {
    public static void main(String[] args) {
        Admin admin = new Admin("Admin", "admin@mail.com", "admin123", "Alamat Admin", "Admin", "08123456789");
        Pelamar pelamar = new Pelamar("Budi", "budi@mail.com", "pelamar123", "Alamat Pelamar", "Pelamar", "08198765432");
        PembukaLowongan penyedia = new PembukaLowongan("PT Maju", "hrd@ptmaju.com", "hrd123", "Alamat Penyedia", "Penyedia", "08111223344");

        database.add(admin);
        database.add(penyedia);
        database.add(pelamar);

        MenuUtil.menuUtama();
    }
}
