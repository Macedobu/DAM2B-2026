package model;

import java.sql.Timestamp;

public class Comanda {
    private int id;
    private int clientId;
    private Timestamp data;
    private double total;
    
    // Constructor buit
    public Comanda() {}
    
    // Constructor amb tots els parametres
    public Comanda(int id, int clientId, Timestamp data, double total) {
        this.id = id;
        this.clientId = clientId;
        this.data = data;
        this.total = total;
    }
    
    //Getters i Setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getClientId() {return clientId;}

    public void setClientId(int clientId) {this.clientId = clientId;}

    public Timestamp getData() {return data;}

    public void setData(Timestamp data) {this.data = data;}

    public double getTotal() {return total;}

    public void setTotal(double total) {this.total = total;}
    
    @Override
    public String toString() {
        return "ID Comanda: " + id + " | ID Client: " + clientId + " | Data: " + data + " | Total: " + total + "€";
    }
}
