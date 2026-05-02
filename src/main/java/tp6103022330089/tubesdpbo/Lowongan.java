package tp6103022330089.tubesdpbo;

import java.util.ArrayList;
import java.util.Objects;

public class Lowongan {
    private String id;
    private String posisi;
    private String perusahaan;
    private String deskripsi;
    private ArrayList<Pelamar> pelamarList;
    private static ArrayList<Lowongan> daftarLowongan = new ArrayList<>();

    public Lowongan(String id, String posisi, String perusahaan) {
        this(id, posisi, perusahaan, "");
    }

    public Lowongan(String id, String posisi, String perusahaan, String deskripsi) {
        this.id = id;
        this.posisi = posisi;
        this.perusahaan = perusahaan;
        this.deskripsi = deskripsi;
        this.pelamarList = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getPosisi() { return posisi; }
    public String getPerusahaan() { return perusahaan; }
    public String getDeskripsi() { return deskripsi; }
    public ArrayList<Pelamar> getPelamarList() { return pelamarList; }

    public static ArrayList<Lowongan> getDaftarLowongan() {
        return daftarLowongan;
    }

    public static Lowongan cariById(String id) {
        for (Lowongan lowongan : daftarLowongan) {
            if (lowongan.getId().equalsIgnoreCase(id)) {
                return lowongan;
            }
        }
        return null;
    }

    public static boolean tambahLowongan(Lowongan lowongan) {
        if (lowongan == null || cariById(lowongan.getId()) != null) {
            return false;
        }
        daftarLowongan.add(lowongan);
        return true;
    }

    public boolean melamar(Pelamar pelamar) {
        if (pelamar == null) {
            return false;
        }
        for (Pelamar p : pelamarList) {
            if (p.getEmail().equalsIgnoreCase(pelamar.getEmail())) {
                return false;
            }
        }
        pelamarList.add(pelamar);
        return true;
    }

    public boolean hapusPelamar(String identitasPelamar) {
        for (int i = 0; i < pelamarList.size(); i++) {
            Pelamar pelamar = pelamarList.get(i);
            if (pelamar.getNama().equalsIgnoreCase(identitasPelamar)
                    || pelamar.getEmail().equalsIgnoreCase(identitasPelamar)) {
                pelamarList.remove(i);
                return true;
            }
        }
        return false;
    }

    public void tampilkanPelamar() {
        if (pelamarList.isEmpty()) {
            System.out.println("Tidak ada pelamar untuk lowongan ini.");
            return;
        }
        for (Pelamar pelamar : pelamarList) {
            System.out.println("- " + pelamar.getNama() + " (" + pelamar.getEmail() + ")");
        }
    }

    public static void showLowongan() {
        if (daftarLowongan.isEmpty()) {
            System.out.println("Tidak ada lowongan tersedia.");
            return;
        }
        System.out.println("Daftar Lowongan Kerja:");
        for (Lowongan lowongan : daftarLowongan) {
            System.out.println("ID: " + lowongan.getId()
                    + " | Posisi: " + lowongan.getPosisi()
                    + " | Perusahaan: " + lowongan.getPerusahaan()
                    + " | Deskripsi: " + lowongan.getDeskripsi());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lowongan)) return false;
        Lowongan lowongan = (Lowongan) o;
        return id.equalsIgnoreCase(lowongan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.toLowerCase());
    }
}
