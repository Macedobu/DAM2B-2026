package dao;

import java.sql.*;
import java.util.List;
import model.LiniaComanda;
import util.Connexio;

public class ComandaDAO {
    
    // Metode per crear una comanda
    public void crearComanda(int clientId, List<LiniaComanda> linies) throws SQLException {
        
        double totalComanda = 0.0;
        
        try (Connection con = Connexio.getConnection()) {
            
            // Desactivem autocommit per realitzar transaccio
            con.setAutoCommit(false);
            
            try {
                
                // Comprovem l'estoc i calculem els preus abans d'inserir
                for (LiniaComanda linia : linies) {
                    String sqlEstoc = "SELECT estoc, preu FROM Productes WHERE id = ?";
                    try (PreparedStatement psEstoc = con.prepareStatement(sqlEstoc)) {
                        psEstoc.setInt(1, linia.getProducteId());
                        try (ResultSet rs = psEstoc.executeQuery()) {
                            if (rs.next()) {
                                int estocActual = rs.getInt("estoc");
                                double preuBase = rs.getDouble("preu");
                                
                                if (estocActual < linia.getQuantitat()) {
                                    throw new SQLException("Estoc insuficient per al producte amb ID: " + linia.getProducteId());
                                }
                                linia.setPreuUnitari(preuBase);
                            } else {
                                throw new SQLException("El producte amb ID " + linia.getProducteId() + " no existeix.");
                            }
                        }
                    }
                }
                
                // Inserim la comanda i iniciem el total amb 0.0
                int comandaId = -1;
                String sqlComanda = "INSERT INTO Comandes (client_id, total) VALUES (?, ?)";
                try (PreparedStatement psComanda = con.prepareStatement(sqlComanda, Statement.RETURN_GENERATED_KEYS)) {
                    psComanda.setInt(1, clientId);
                    psComanda.setDouble(2, 0.0);
                    psComanda.executeUpdate();
                    
                    try (ResultSet rsKeys = psComanda.getGeneratedKeys()) {
                        if (rsKeys.next()) {
                            comandaId = rsKeys.getInt(1);
                        }
                    }
                }
                
                // Inserim les linies, reduim l'estoc i sumem al total
                for (LiniaComanda linia : linies) {
                    
                    // Inserir linia
                    String sqlLinia = "INSERT INTO LiniesComanda (comanda_id, producte_id, quantitat, preuUnitari) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement psLinia = con.prepareStatement(sqlLinia)) {
                        psLinia.setInt(1, comandaId);
                        psLinia.setInt(2, linia.getProducteId());
                        psLinia.setInt(3, linia.getQuantitat());
                        psLinia.setDouble(4, linia.getPreuUnitari());
                        psLinia.executeUpdate();
                    }
                    
                    // Reduim l'estoc
                    String sqlUpdateEstoc = "UPDATE Productes SET estoc = estoc - ? WHERE id = ?";
                    try (PreparedStatement psUpdEstoc = con.prepareStatement(sqlUpdateEstoc)) {
                        psUpdEstoc.setInt(1, linia.getQuantitat());
                        psUpdEstoc.setInt(2, linia.getProducteId());
                        psUpdEstoc.executeUpdate();
                    }
                    totalComanda += linia.getPreuUnitari() * linia.getQuantitat();
                }
                
                // Creem un SAVEPOINT abans d'aplicar els descomptes
                Savepoint spDescompte = con.setSavepoint("DespresLinies");
                
                try {
                    
                    // Intentem aplicar descomptes de la taula Descomptes
                    for (LiniaComanda linia : linies) {
                        String sqlDescompte = "SELECT tipus, valor FROM Descomptes WHERE producte_id = ?";
                        try (PreparedStatement psDesc = con.prepareStatement(sqlDescompte)) {
                            psDesc.setInt(1, linia.getProducteId());
                            try (ResultSet rsDesc = psDesc.executeQuery()) {
                                if (rsDesc.next()) {
                                    String tipus = rsDesc.getString("tipus");
                                    double valor = rsDesc.getDouble("valor");
                                    
                                    // Descompte %
                                    if (tipus.equals("%")) {
                                        totalComanda -= (linia.getPreuUnitari() * linia.getQuantitat() * (valor / 100.0));
                                    
                                    // Descompte fix    
                                    } else if (tipus.equals("€")) {
                                        totalComanda -= valor;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    
                    // Si falla el descompte, fem rollback
                    con.rollback(spDescompte);
                    System.out.println("Error. No s'han aplicat els descomptes");
                }
                
                // Actualitzar el total de la comanda
                String sqlUpdateTotal = "UPDATE Comandes SET total = ? WHERE id = ?";
                try (PreparedStatement psTotal = con.prepareStatement(sqlUpdateTotal)) {
                    psTotal.setDouble(1, Math.max(totalComanda, 0.0)); // Evitem totals negatius
                    psTotal.setInt(2, comandaId);
                    psTotal.executeUpdate();
                }
                
                // Fem COMMIT si tot ha anat bé
                con.commit();
                System.out.println("Comanda creada correctament. ID: " + comandaId);
                
            } catch (SQLException e) {
                
                // Si falla algo, com per exemple l'estoc, fem rollback
                con.rollback();
                System.out.println("Error al crear la comanda. Rollback: " + e.getMessage());
                throw e;
            }
        }
    }
}
