package model;

public class Client {
    private int id;
    private String nom;
    private String correu;
    
    // Constructor buit
    public Client() {}
    
    // Constructor amb tots els parametres
    public Client(int id, String nom, String correu) {
        this.id = id;
        this.nom = nom;
        this.correu = correu;
    }
    
    // Constructor sense id
    public Client(String nom, String correu) {
        this.nom = nom;
        this.correu = correu;
    }
    
    //Getters i Setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNom() {return nom;}

    public void setNom(String nom) {this.nom = nom;}

    public String getCorreu() {return correu;}

    public void setCorreu(String correu) {this.correu = correu;}
    
    @Override
    public String toString() {
        return "ID: " + id + " | Nom: " + nom + " | Correu: " + correu;
    }
}
