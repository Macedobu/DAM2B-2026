package model;

public class Descompte {
    private int id;
    private int producteId;
    private String tipus;
    private double valor;
    
    // Constructor buit
    public Descompte() {}
    
    // Constructor amb tots els parametres
    public Descompte(int id, int producteId, String tipus, double valor) {
        this.id = id;
        this.producteId = producteId;
        this.tipus = tipus;
        this.valor = valor;
    }
    
    //Getters i Setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getProducteId() {return producteId;}

    public void setProducteId(int producteId) {this.producteId = producteId;}

    public String getTipus() {return tipus;}

    public void setTipus(String tipus) {this.tipus = tipus;}

    public double getValor() {return valor;}

    public void setValor(double valor) {this.valor = valor;}
    
    @Override
    public String toString() {
        return "ID Descompte: " + id + " | ID Producte: " + producteId + " | Tipus: " + tipus + " | Valor: " + valor;
    }
}


