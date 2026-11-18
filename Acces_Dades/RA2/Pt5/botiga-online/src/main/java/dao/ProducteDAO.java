package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Producte;
import util.Connexio;

public class ProducteDAO {
    
    // INSERIR PRODUCTE
    public void inserir(Producte producte) throws SQLException {
        String sql = "INSERT INTO Productes (nom, preu, estoc) VALUES (?, ?, ?)";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, producte.getNom());
            ps.setDouble(2, producte.getPreu());
            ps.setInt(3, producte.getEstoc());
            
            ps.executeUpdate();
            System.out.println("Producte inserit correctament.");
        }
    }
    
    // LLISTAR PRODUCTES
    public List<Producte> llistar() throws SQLException {
        List<Producte> productes = new ArrayList<>();
        String sql = "SELECT * FROM Productes";
        
        try (Connection con = Connexio.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Producte p = new Producte();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setPreu(rs.getDouble("preu"));
                p.setEstoc(rs.getInt("estoc"));
                productes.add(p);
            }
        }
        return productes;
    }
    
    // ACTUALITZAR PRODUCTE
    public void actualitzar(Producte producte) throws SQLException {
        String sql = "UPDATE Productes SET nom = ?, preu = ?, estoc = ? WHERE id = ?";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, producte.getNom());
            ps.setDouble(2, producte.getPreu());
            ps.setInt(3, producte.getEstoc());
            ps.setInt(4, producte.getId());
            
            /* Ens assegurem que s'ha trobat l'id. 
            Si executeUpdate retorna 1, l'ha trobat.
            Si retorna 0, no */
            int updateRealitzat = ps.executeUpdate();
            if (updateRealitzat > 0) {
                System.out.println("Producte actualitzat correctament.");
            } else {
                System.out.println("No s'ha trobat cap producte amb aquesta ID");
            }
        }
    }
    
    // ELIMINAR UN PRODUCTE
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Productes WHERE id = ?";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);

            /* Ens assegurem que s'ha trobat l'id. 
            Si executeUpdate retorna 1, l'ha trobat.
            Si retorna 0, no */
            int updateRealitzat = ps.executeUpdate();
            if (updateRealitzat > 0) {
                System.out.println("Producte eliminat correctament.");
            } else {
                System.out.println("No s'ha trobat cap producte amb aquesta ID");
            }
        }
    }
}
