package com.botiga.botiga.online;

import dao.*;
import model.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BotigaOnline {
    
    private static Scanner sc = new Scanner(System.in);
        
    // Instancies dels DAO
    private static ClientDAO clientDao = new ClientDAO();
    private static ProducteDAO producteDao = new ProducteDAO();
    private static ComandaDAO comandaDao = new ComandaDAO();
    private static ConsultesDAO consultesDao = new ConsultesDAO();

    
    // MENÚ PRINCIPAL
    public static void main(String[] args) {
        int opcio = -1;
        
        do {
            System.out.println("\n===== BOTIGA ONLINE =====");
            System.out.println("1. Gestionar Productes");
            System.out.println("2. Gestionar Clients");
            System.out.println("3. Crear Comanda");
            System.out.println("4. Llistar Comandes");
            System.out.println("0. Sortir");
            System.out.print("Tria una opció: ");
            
            if (sc.hasNextInt()) {
                opcio = sc.nextInt();
                sc.nextLine(); // netegem buffer
                
                switch (opcio) {
                    case 1:
                        // Gestionar productes
                        gestionarProductes();
                        break;
                        
                    case 2:
                        // Cridar ClientDAO
                        gestionarClients();
                        break;
                        
                    case 3:
                        // Crear Comanda amb transacció
                        crearComanda();
                        break;
                        
                    case 4:
                        // Consultar JOIN
                        llistarComandes();
                        break;
                    
                    case 0:
                        // Sortir de l'app
                        System.out.println("Sortint...");
                        break;
                        
                    default:
                        // 
                        System.out.println("Opció no vàlida.");
                }
                
                // Gestió d'errors (valor d'"opció" incorrecte)
            } else {
                System.out.println("Error. Introdueix un número vàlid.");
                sc.next(); // Netejem entrada incorrecta
            }
        } while(opcio != 0);
        sc.close();
    }
    
    // MÈTODE GESTIONAR PRODUCTES
    private static void gestionarProductes() {
        int opcio = -1;
        do {
            System.out.println("\n--- GESTIÓ DE PRODUCTES ---");
            System.out.println("1. Inserir producte");
            System.out.println("2. Llistar productes");
            System.out.println("3. Actualitzar producte");
            System.out.println("4. Eliminar producte");
            System.out.println("0. Tornar al menú principal");
            System.out.print("Tria una opció: ");
            
            if (sc.hasNextInt()) {
                opcio = sc.nextInt();
                sc.nextLine();
                
                try {
                    switch (opcio) {
                        case 1:
                            // Inserir nou producte
                            System.out.println("\n--- INSERIR NOU PRODUCTE ---");
                            System.out.print("Nom: ");
                            String nom = sc.nextLine();
                            System.out.print("Preu: ");
                            double preu = sc.nextDouble();
                            System.out.print("Estoc: ");
                            int estoc = sc.nextInt();
                            sc.nextLine();
                            producteDao.inserir(new Producte(nom, preu, estoc));
                            break;
                            
                        case 2:
                            // Llistar productes
                            List<Producte> productes = producteDao.llistar();
                            System.out.println("\n--- LLISTA DE PRODUCTES ---");
                            for (Producte p : productes) {
                                System.out.println(p); // Mètode toString
                            }
                            break;
                            
                        case 3:
                            // Actualitzar producte
                            System.out.println("\n--- ACTUALITZAR PRODUCTE ---");
                            System.out.print("ID del producte: ");
                            int idUpd = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Nou nom: ");
                            String nouNom = sc.nextLine();
                            System.out.print("Nou preu: ");
                            double nouPreu = sc.nextDouble();
                            System.out.print("Nou estoc: ");
                            int nouEstoc = sc.nextInt();
                            sc.nextLine();
                            producteDao.actualitzar(new Producte(idUpd, nouNom, nouPreu, nouEstoc));
                            break;
                            
                        case 4:
                            // Eliminar producte
                            System.out.println("\n--- ELIMINAR PRODUCTE ---");
                            System.out.print("ID del producte: ");
                            int idDel = sc.nextInt();
                            sc.nextLine();
                            producteDao.eliminar(idDel);
                            break;
                            
                        case 0:
                            System.out.println("Tornant...");
                            break;
                            
                        default:
                            System.out.println("Opció no vàlida.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error amb la base de dades: " + e.getMessage());
                }
            } else {
                System.out.println("Error. Introdueix un número vàlid.");
                sc.next();
            }
        } while (opcio != 0);
    }
    
    // MÈTODE GESTIONAR CLIENTS
    private static void gestionarClients() {
        int opcio = -1;
        do {
            System.out.println("\n--- GESTIÓ DE CLIENTS ---");
            System.out.println("1. Inserir client");
            System.out.println("2. Llistar clients");
            System.out.println("3. Actualitzar client");
            System.out.println("4. Eliminar client");
            System.out.println("0. Tornar al menú principal");
            System.out.print("Tria una opció: ");
            
            if (sc.hasNextInt()) {
                opcio = sc.nextInt();
                sc.nextLine();
                
                try {
                    switch (opcio) {
                        case 1:
                            // Inserir client
                            System.out.println("\n--- INSERIR NOU CLIENT ---");
                            System.out.print("Nom: ");
                            String nom = sc.nextLine();
                            System.out.print("Correu: ");
                            String correu = sc.nextLine();
                            clientDao.inserir(new Client(nom, correu));
                            break;
                            
                        case 2:
                            // Llistar clients
                            List<Client> clients = clientDao.llistar();
                            System.out.println("\n--- LLISTA DE CLIENTS ---");
                            for (Client c : clients) {
                                System.out.println(c);
                            }
                            break;
                            
                        case 3:
                            // Actualitzar client
                            System.out.println("\n--- ACTUALITZAR CLIENT ---");
                            System.out.print("ID del client: ");
                            int idUpd = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Nou nom: ");
                            String nouNom = sc.nextLine();
                            System.out.print("Nou correu: ");
                            String nouCorreu = sc.nextLine();
                            clientDao.actualitzar(new Client(idUpd, nouNom, nouCorreu));
                            break;
                            
                        case 4:
                            // Eliminar client
                            System.out.println("\n--- ELIMINAR CLIENT ---");
                            System.out.print("ID del client: ");
                            int idDel = sc.nextInt();
                            sc.nextLine();
                            clientDao.eliminar(idDel);
                            break;
                            
                        case 0:
                            System.out.println("Tornant...");
                            break;
                            
                        default:
                            System.out.println("Opció no vàlida.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error amb la base de dades: " + e.getMessage());
                }
            } else {
                System.out.println("Error. Introdueix un número vàlid.");
                sc.next();
            }
        } while (opcio != 0);
    }
    
    // MÈTODE CREAR COMANDES
    private static void crearComanda() {
        System.out.println("\n--- CREAR NOVA COMANDA ---");
        System.out.print("Introdueix l'ID del client: ");
        int clientId = sc.nextInt();
        sc.nextLine();
        
        List<LiniaComanda> linies = new ArrayList<>();
        char continuar = 'S';
        
        while (continuar == 'S') {
            System.out.print("ID del producte: ");
            int producteId = sc.nextInt();
            System.out.print("Quantitat: ");
            int quantitat = sc.nextInt();
            sc.nextLine();
            
            linies.add(new LiniaComanda(producteId, quantitat));
            
            System.out.print("Vols afegir un altre producte (s/n): ");
            continuar = sc.nextLine().toUpperCase().charAt(0);
        }
        
        try {
            comandaDao.crearComanda(clientId, linies);
        } catch (SQLException e) {
            System.out.println("Error al fer la comanda.");
        }
    }
     
    // MÈTODE PER LLISTAR COMANDES (JOIN)
    private static void llistarComandes() {
        System.out.println("\n--- LLISTAR COMANDES D'UN CLIENT ---");
        System.out.print("Introdueix l'ID del client: ");
        int idClient = sc.nextInt();
        sc.nextLine();
        
        consultesDao.llistarComandesClient(idClient);
    }
}
