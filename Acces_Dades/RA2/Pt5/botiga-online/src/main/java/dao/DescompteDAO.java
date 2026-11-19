package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Descompte;
import util.Connexio;

public class DescompteDAO {
    
    // INSERIR DESCOMPTE
    public void inserir(Descompte descompte) throws SQLException {
        String sql = "INSERT INTO Descomptes (producte_id, tipus, valor) VALUES (?, ?, ?)";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, descompte.getProducteId());
            ps.setString(2, descompte.getTipus());
            ps.setDouble(3, descompte.getValor());
            
            ps.executeUpdate();
            System.out.println("Descompte inserit correctament.");
        }
    }
    
    // LLISTAR DESCOMPTES
    public List<Descompte> llistar() throws SQLException {
        List<Descompte> descomptes = new ArrayList<>();
        String sql = "SELECT * FROM Descomptes";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Descompte d = new Descompte();
                d.setId(rs.getInt("id"));
                d.setProducteId(rs.getInt("producte_id"));
                d.setTipus(rs.getString("tipus"));
                d.setValor(rs.getDouble("valor"));
                descomptes.add(d);
            }
        }
        return descomptes;
    }
    
    // ACTUALITZAR DESCOMPTE
    public void actualitzar(Descompte descompte) throws SQLException {
        String sql = "UPDATE Descomptes SET producte_id = ?, tipus = ?, valor = ? WHERE id = ?";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, descompte.getProducteId());
            ps.setString(2, descompte.getTipus());
            ps.setDouble(3, descompte.getValor());
            ps.setInt(4, descompte.getId());
            
            /* Ens assegurem que s'ha trobat l'id. 
            Si executeUpdate retorna 1, l'ha trobat.
            Si retorna 0, no */
            int updateRealitzat = ps.executeUpdate();
            if (updateRealitzat > 0) {
                System.out.println("Descompte actualitzat correctament.");
            } else {
                System.out.println("No s'ha trobat cap descompte amb aquesta ID");
            }
        }
    }
    
    // ELIMINAR UN DESCOMPTE
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Descomptes WHERE id = ?";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);

            /* Ens assegurem que s'ha trobat l'id. 
            Si executeUpdate retorna 1, l'ha trobat.
            Si retorna 0, no */
            int updateRealitzat = ps.executeUpdate();
            if (updateRealitzat > 0) {
                System.out.println("Descompte eliminat correctament.");
            } else {
                System.out.println("No s'ha trobat cap descompte amb aquesta ID");
            }
        }
    }
}
