package dao;

import java.sql.*;
import util.Connexio;

public class ConsultesDAO {
    
    // Llistar comandes d'un client
    public void llistarComandesClient(int clientId) {
        String sql = "SELECT c.id AS comanda_id, c.data, c.total, " +
                "p.nom AS producte_nom, l.quantitat, l.preuUnitari, " +
                "d.tipus, d.valor " +
                "FROM Comandes c " +
                "JOIN LiniesComanda l ON c.id = l.comanda_id " +
                "JOIN Productes p ON l.producte_id = p.id " +
                "LEFT JOIN Descomptes d ON p.id = d.producte_id " +
                "WHERE c.client_id = ?";
        
        try (Connection con = Connexio.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, clientId);
            
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n----Comandes del client amb id " + clientId + "----");
                
                int comandaActual = -1;
                boolean teComandes = false;
                
                while (rs.next()) {
                    teComandes = true;
                    int idComanda = rs.getInt("comanda_id");
                    
                    // Info de la comanda
                    if (idComanda != comandaActual) {
                        comandaActual = idComanda;
                        System.out.println("\n------------------------------------------------");
                        System.out.println("Comanda ID: " + idComanda +
                                " | Data: " + rs.getTimestamp("data") +
                                " | Total Final: " + rs.getDouble("total") + " €");
                        System.out.println("Productes:");
                    }
                    
                    // Mostrem descompte si en te
                    String tipus = rs.getString("tipus");
                    String infoDescompte = (tipus != null)
                            ? " | Descompte: " + rs.getDouble("valor") + " " + tipus
                            : " | Sense descompte";
                    
                    // Mostrem totes les linies de la comanda
                    System.out.println("- Producte: " + rs.getString("producte_nom") +
                            " | Quantitat: " + rs.getInt("quantitat") +
                            " | Preu Unitari: " + rs.getDouble("preuUnitari") + " €" +
                            infoDescompte);
                }
                
                if (!teComandes) {
                    System.out.println("Client sense comandes.");
                }
                System.out.println("------------------------------------------------");
            }
        } catch (SQLException e) {
                System.out.println("Error al fer la consulta JOIN: " + e.getMessage());
        }
    }
}
