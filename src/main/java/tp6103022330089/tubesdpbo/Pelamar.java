package tp6103022330089.tubesdpbo;

public class Pelamar extends User {
    public Pelamar(String nama, String email, String password, String alamat, String role, String noTelepon) {
        super(nama, email, password, alamat, role, noTelepon);
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
        User.database.add(new Pelamar(nama, email, password, alamat, "Pelamar", noTelepon));
        System.out.println("Pelamar telah berhasil didaftarkan.");
    }

    public void melamarLowongan(String email, String idLowongan) {
        if (!this.email.equalsIgnoreCase(email)) {
            System.out.println("Email pelamar tidak sesuai dengan akun yang sedang login.");
            return;
        }
        melamarLowongan(idLowongan);
    }

    public void melamarLowongan(String idLowongan) {
        Lowongan lowongan = Lowongan.cariById(idLowongan);
        if (lowongan == null) {
            System.out.println("Lowongan dengan ID " + idLowongan + " tidak ditemukan.");
            return;
        }
        if (lowongan.melamar(this)) {
            System.out.println("Pelamar " + this.getNama() + " telah melamar lowongan " + lowongan.getPosisi() + ".");
        } else {
            System.out.println("Anda sudah melamar lowongan ini.");
        }
    }

    public void mendaftarPelatihan(String idAtauJudulPelatihan) {
        Pelatihan pelatihan = Pelatihan.cariPelatihan(idAtauJudulPelatihan);
        if (pelatihan == null) {
            System.out.println("Pelatihan dengan ID/judul " + idAtauJudulPelatihan + " tidak ditemukan.");
            return;
        }
        if (pelatihan.daftarPelamar(this)) {
            System.out.println("Pelamar " + this.getNama() + " berhasil mendaftar pelatihan " + pelatihan.getJudul() + ".");
        } else {
            System.out.println("Anda sudah terdaftar pada pelatihan ini.");
        }
    }
}
