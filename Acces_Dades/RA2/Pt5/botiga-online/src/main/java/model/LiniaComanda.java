package model;

public class LiniaComanda {
    private int id;
    private int comandaId;
    private int producteId;
    private int quantitat;
    private double preuUnitari;
    
    // Constructor buit
    public LiniaComanda() {}
    
    // Constructor amb tots els parametres
    public LiniaComanda(int id, int comandaId, int producteId, int quantitat, double preuUnitari) {
        this.id = id;
        this.comandaId = comandaId;
        this.producteId = producteId;
        this.quantitat = quantitat;
        this.preuUnitari = preuUnitari;
    }
    
    //Getters i Setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getComandaId() {return comandaId;}

    public void setComandaId(int comandaId) {this.comandaId = comandaId;}

    public int getProducteId() {return producteId;}

    public void setProducteId(int producteId) {this.producteId = producteId;}

    public int getQuantitat() {return quantitat;}

    public void setQuantitat(int quantitat) {this.quantitat = quantitat;}

    public double getPreuUnitari() {return preuUnitari;}

    public void setPreuUnitari(double preuUnitari) {this.preuUnitari = preuUnitari;}
    
    @Override
    public String toString() {
        return "ID LiniaComanda: " + id + " | ID Comanda: " + comandaId + " | ID Producte: " + producteId + " | Quantitat: " + quantitat + " | Preu Unitari: " + preuUnitari + "€";
    }
}
