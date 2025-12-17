package app.model;

public class User {
    private String id;
    private String nom;
    private String email;
    private String password;
    private String permis;
    private String telephone;
    private String adresse;
    private boolean isAdmin;

    public User(String id, String nom, String email, String password, String permis, String telephone, String adresse) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.permis = permis;
        this.telephone = telephone;
        this.adresse = adresse;
        this.isAdmin = false;
    }

    // Getters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPermis() { return permis; }
    public String getTelephone() { return telephone; }
    public String getAdresse() { return adresse; }
    public boolean isAdmin() { return isAdmin; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPermis(String permis) { this.permis = permis; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    @Override
    public String toString() {
        return nom + " (" + email + ")";
    }
}