package tp6103022330089.tubesdpbo;

import java.util.ArrayList;

public class Pelatihan {
    private String id;
    private String judul;
    private String pemateri;
    private String deskripsi;
    private ArrayList<Pelamar> daftarPelamar;
    private static ArrayList<Pelatihan> daftarPelatihan = new ArrayList<>();

    public Pelatihan(String judul, String pemateri, String deskripsi) {
        this(judul, judul, pemateri, deskripsi);
    }

    public Pelatihan(String id, String judul, String pemateri, String deskripsi) {
        this.id = id;
        this.judul = judul;
        this.pemateri = pemateri;
        this.deskripsi = deskripsi;
        this.daftarPelamar = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getJudul() { return judul; }
    public String getPemateri() { return pemateri; }
    public String getDeskripsi() { return deskripsi; }
    public ArrayList<Pelamar> getDaftarPelamar() { return daftarPelamar; }

    public static ArrayList<Pelatihan> getDaftarPelatihan() {
        return daftarPelatihan;
    }

    public static Pelatihan cariPelatihan(String idAtauJudul) {
        for (Pelatihan pelatihan : daftarPelatihan) {
            if (pelatihan.getId().equalsIgnoreCase(idAtauJudul)
                    || pelatihan.getJudul().equalsIgnoreCase(idAtauJudul)) {
                return pelatihan;
            }
        }
        return null;
    }

    public static boolean tambahPelatihan(Pelatihan pelatihan) {
        if (pelatihan == null || cariPelatihan(pelatihan.getId()) != null
                || cariPelatihan(pelatihan.getJudul()) != null) {
            return false;
        }
        daftarPelatihan.add(pelatihan);
        return true;
    }

    public static boolean hapusPelatihan(String idAtauJudul) {
        for (int i = 0; i < daftarPelatihan.size(); i++) {
            Pelatihan pelatihan = daftarPelatihan.get(i);
            if (pelatihan.getId().equalsIgnoreCase(idAtauJudul)
                    || pelatihan.getJudul().equalsIgnoreCase(idAtauJudul)) {
                daftarPelatihan.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean daftarPelamar(Pelamar pelamar) {
        if (pelamar == null) {
            return false;
        }
        for (Pelamar p : daftarPelamar) {
            if (p.getEmail().equalsIgnoreCase(pelamar.getEmail())) {
                return false;
            }
        }
        daftarPelamar.add(pelamar);
        return true;
    }

    public static void displayPelatihan() {
        if (daftarPelatihan.isEmpty()) {
            System.out.println("Tidak ada pelatihan yang tersedia.");
            return;
        }
        System.out.println("---- Daftar Pelatihan ----");
        for (Pelatihan pelatihan : daftarPelatihan) {
            System.out.println("ID: " + pelatihan.getId()
                    + " | Judul: " + pelatihan.getJudul()
                    + " | Pemateri: " + pelatihan.getPemateri()
                    + " | Deskripsi: " + pelatihan.getDeskripsi()
                    + " | Jumlah Peserta: " + pelatihan.getDaftarPelamar().size());
        }
    }
}
