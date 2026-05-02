package tp6103022330089.tubesdpbo;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class User {
    protected String nama;
    protected String email;
    protected String password;
    protected String alamat;
    protected String role;
    protected String noTelepon;

    protected static ArrayList<User> database = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    public User(String nama, String email, String password, String alamat, String role, String noTelepon) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
        this.role = role;
        this.noTelepon = noTelepon;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

    public static ArrayList<User> getDatabase() {
        return database;
    }

    public static boolean isEmailTerdaftar(String email) {
        for (User user : database) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public static User cariUserByLogin(String email, String password) {
        for (User user : database) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean login(String email, String password) {
        return cariUserByLogin(email, password) != null;
    }

    public abstract void register(String nama, String email, String password, String alamat, String noTelepon);

    public static void showAllUsers() {
        if (database.isEmpty()) {
            System.out.println("Tidak ada pengguna di database.");
            return;
        }
        System.out.println("---- User Terdaftar ----");
        for (User user : database) {
            System.out.println("Nama: " + user.getNama()
                    + " | Email: " + user.getEmail()
                    + " | Role: " + user.getRole());
        }
    }

    public static void hapusUser(String nama, String email) {
        for (int i = 0; i < database.size(); i++) {
            User user = database.get(i);
            if (user.getNama().equalsIgnoreCase(nama) && user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Apakah anda yakin ingin menghapus user " + user.getNama() + "? (Y/N)");
                char confirm = ScannerUtil.scanChar(scan);
                if (confirm != 'Y') {
                    System.out.println("User tidak jadi dihapus.");
                    return;
                }
                database.remove(i);
                System.out.println("User dengan nama " + nama + " dan email " + email + " telah dihapus.");
                return;
            }
        }
        System.out.println("User dengan nama " + nama + " dan email " + email + " tidak ditemukan.");
    }
}
