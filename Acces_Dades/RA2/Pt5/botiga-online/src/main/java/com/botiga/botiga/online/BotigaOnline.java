package com.botiga.botiga.online;

import dao.*;
import model.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Imports per forçar UTF-8
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class BotigaOnline {
    
    // A l'escaner afegeixo el parametre "StandardCharsets.UTF_8" per forçar UTF-8
    private static Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        
    // Instancies dels DAO
    private static ClientDAO clientDao = new ClientDAO();
    private static ProducteDAO producteDao = new ProducteDAO();
    private static DescompteDAO descompteDao = new DescompteDAO();
    private static ComandaDAO comandaDao = new ComandaDAO();
    private static ConsultesDAO consultesDao = new ConsultesDAO();

    
    // MENÚ PRINCIPAL
    public static void main(String[] args) {
        
        // Forcem  UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        
        //Comencem amb el menú principal de la botiga
        int opcio = -1;
        
        do {
            System.out.println("\n===== BOTIGA ONLINE =====");
            System.out.println("1. Gestionar Productes");
            System.out.println("2. Gestionar Clients");
            System.out.println("3. Gestionar Descomptes");
            System.out.println("4. Crear Comanda");
            System.out.println("5. Llistar Comandes");
            System.out.println("0. Sortir");
            
            // Metode que gestiona que el valor introduit sigui vàlid (LlegirEnter)
                opcio = llegirEnter("Tria una opció: ", 0);
                
                switch (opcio) {
                    case 1:
                        // Gestionar productes
                        gestionarProductes();
                        break;
                        
                    case 2:
                        // Gestionar clients
                        gestionarClients();
                        break;
                        
                    case 3:
                        // Gestionar descomptes
                        gestionarDescomptes();
                        break;
                        
                    case 4:
                        // Crear Comanda amb transacció
                        crearComanda();
                        break;
                        
                    case 5:
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
            
            opcio = llegirEnter("Tria una opció: ", 0);

            try {
                switch (opcio) {
                    case 1:
                        // Inserir nou producte
                        System.out.println("\n--- INSERIR NOU PRODUCTE ---");
                        System.out.print("Nom: ");
                        String nom = sc.nextLine();
                        double preu = llegirDecimal("Preu: ", 0.0);
                        int estoc = llegirEnter("Estoc: ", 0);
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
                        int idUpd = llegirEnter("ID del producte: ", 1);
                        System.out.print("Nou nom: ");
                        String nouNom = sc.nextLine();
                        double nouPreu = llegirDecimal("Nou preu: ", 0.0);
                        int nouEstoc = llegirEnter("Nou estoc: ", 0);
                        producteDao.actualitzar(new Producte(idUpd, nouNom, nouPreu, nouEstoc));
                        break;

                    case 4:
                        // Eliminar producte
                        System.out.println("\n--- ELIMINAR PRODUCTE ---");
                        int idDel = llegirEnter("ID del producte: ", 1);
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
            
            opcio = llegirEnter("Tria una opció: ", 0);

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
                        int idUpd = llegirEnter("ID del client: ", 1);
                        System.out.print("Nou nom: ");
                        String nouNom = sc.nextLine();
                        System.out.print("Nou correu: ");
                        String nouCorreu = sc.nextLine();
                        clientDao.actualitzar(new Client(idUpd, nouNom, nouCorreu));
                        break;

                    case 4:
                        // Eliminar client
                        System.out.println("\n--- ELIMINAR CLIENT ---");
                        int idDel = llegirEnter("ID del client: ", 1);
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
        } while (opcio != 0);
    }
    
    private static void gestionarDescomptes() {
        int opcio = -1;
        do {
            System.out.println("\n--- GESTIÓ DE DESCOMPTES ---");
            System.out.println("1. Inserir descompte");
            System.out.println("2. Llistar descomptes");
            System.out.println("3. Actualitzar descompte");
            System.out.println("4. Eliminar descompte");
            System.out.println("0. Tornar al menú principal");
            
            opcio = llegirEnter("Tria una opció: ", 0);

            try {
                switch (opcio) {
                    case 1:
                        // Inserir descompte
                        System.out.println("\n--- INSERIR NOU DESCOMPTE ---");
                        int prodId = llegirEnter("ID del producte: ", 1);
                        String tipus = llegirTipusDescompte("Tipus(% o €): ");
                        double valor = llegirDecimal("Valor: ", 0.0);
                        descompteDao.inserir(new Descompte(prodId, tipus, valor));
                        break;

                    case 2:
                        // Llistar descomptes
                        List<Descompte> descomptes = descompteDao.llistar();
                        System.out.println("\n--- LLISTA DE DESCOMPTES ---");
                        for (Descompte d : descomptes) {
                            System.out.println(d);
                        }
                        break;

                    case 3:
                        // Actualitzar descompte
                        System.out.println("\n--- ACTUALITZAR DESCOMPTE ---");
                        int idUpd = llegirEnter("ID del descompte: ", 1);
                        int nouProdId = llegirEnter("Nou ID del producte: ", 1);
                        String nouTipus = llegirTipusDescompte("Tipus(% o €): ");
                        double nouValor = llegirDecimal("Nou valor: ", 0.0);
                        descompteDao.actualitzar(new Descompte(idUpd, nouProdId, nouTipus, nouValor));
                        break;

                    case 4:
                        // Eliminar descompte
                        System.out.println("\n--- ELIMINAR DESCOMPTE ---");
                        int idDel = llegirEnter("ID del descompte: ", 1);
                        descompteDao.eliminar(idDel);
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
        } while (opcio != 0);
    }
    
    // MÈTODE CREAR COMANDES
    private static void crearComanda() {
        System.out.println("\n--- CREAR NOVA COMANDA ---");
        int clientId = llegirEnter("Introdueix l'ID del client: ", 1);
        
        List<LiniaComanda> linies = new ArrayList<>();
        char continuar = 'S';
        
        while (continuar == 'S') {
            int producteId = llegirEnter("ID del producte: ", 1);
            int quantitat = llegirEnter("Quantitat: ", 1);
            
            linies.add(new LiniaComanda(producteId, quantitat));
            
            // Preguntem a l'usuari si vol afegir més productes
            String resposta;
            do {
                System.out.print("Vols afegir un altre producte (s/n): ");
                resposta = sc.nextLine().trim().toUpperCase();
                
                // Ens assegurem que l'usuari ha escrit un valor vàlid
                if (!resposta.equals("S") && !resposta.equals("N")) {
                    System.out.println("Error. Resposta no vàlida. Introdueix 's' o 'n'.");
                }
            } while (!resposta.equals("S") && !resposta.equals("N"));
            
            continuar = resposta.charAt(0);
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
        int idClient = llegirEnter("Introdueix l'ID del client: ", 1);
        
        consultesDao.llistarComandesClient(idClient);
    }
    
    // Metode per validar ints entrats per teclat
    private static int llegirEnter(String missatge, int minim) {
        while (true) {
            System.out.print(missatge);
            try {
                int valor = Integer.parseInt(sc.nextLine().trim());
                if (valor < minim) {
                    System.out.println("Error. El valor mínim vàlid és " + minim + ".");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error. Introdueix un número enter vàlid.");
            }
        }
    }
    
    // Metode per validar doubles entrats per teclat
    private static double llegirDecimal(String missatge, double minim) {
        while (true) {
            System.out.print(missatge);
            try {
                double valor = Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
                if (valor < minim) {
                    System.out.println("Error. El valor mínim vàlid és " + minim + ".");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error. Introdueix un número decimal vàlid.");
            }
        }
    }
    
    // Metode per validar el tipus de descompte
    private static String llegirTipusDescompte(String missatge) {
        while (true) {
            System.out.print(missatge);
            String tipus = sc.nextLine().trim();
            if (tipus.equals("%") || tipus.equals("€")) {
                return tipus;
            }
            System.out.println("Error. Valor de tipus no vàlid.");
        }
    }
}
