package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Client;
import util.Connexio;

public class ClientDAO {
    
    // INSERIR CLIENT
    public void inserir(Client client) throws SQLException {
        String sql = "INSERT INTO Clients (nom, correu) VALUES (?, ?)";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, client.getNom());
            ps.setString(2, client.getCorreu());
            
            ps.executeUpdate();
            System.out.println("Client inserit correctament.");
        }
    }
    
    // LLISTAR CLIENTS
    public List<Client> llistar() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Clients";
        
        try (Connection con = Connexio.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setCorreu(rs.getString("correu"));
                clients.add(c);
            }
        }
        return clients;
    }
    
    // ACTUALITZAR CLIENT
    public void actualitzar(Client client) throws SQLException {
        String sql = "UPDATE Clients SET nom = ?, correu = ? WHERE id = ?";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, client.getNom());
            ps.setString(2, client.getCorreu());
            ps.setInt(3, client.getId());
            
            ps.executeUpdate();
            System.out.println("Client actualitzat correctament.");
        }
    }
    
    // ELIMINAR UN CLIENT
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Clients WHERE id = ?";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Client eliminat correctament.");
        }
    }
}
