package com.mycompany.pt3;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Programa {
    
    private static final String FITXER = "videojocs.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Videojoc> videojocs = carregarVideojocs();
        int opcio = 0;
        
        do {
            opcio = menu(sc);
            
            switch (opcio) {
                case 1:
                    afegirVideojoc(sc, videojocs);
                    break;
                case 2:
                    llistarVideojocs(videojocs);
                    break;
                case 3:
                    cercarVideojoc(sc, videojocs);
                    break;
                case 4:
                    actualitzarVideojoc(sc, videojocs);
                    break;
                case 5:
                    eliminarVideojoc(sc, videojocs);
                    break;
                case 6:
                    desarVideojocs(videojocs);
                    System.out.println("Sortint del programa...");
                    break;
                default:
                    System.out.println("Opcio no valida!");
            }
            
        } while (opcio != 6);
        
        sc.close();
    }
    
    // ---------- Mètodes de persistència ----------
    
    // Caregar videojocs del fitxer
    @SuppressWarnings("unchecked")
    public static ArrayList<Videojoc> carregarVideojocs() {
        ArrayList<Videojoc> videojocs = new ArrayList<>();
        File fitxer = new File(FITXER);
        if (fitxer.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
                videojocs = (ArrayList<Videojoc>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error carregant videojocs: " + e.getMessage());
            }
        }
        return videojocs;
    }
    
    // Desar videojocs al fitxer
    private static void desarVideojocs(ArrayList<Videojoc> videojocs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER))) {
            oos.writeObject(videojocs);
        } catch (IOException e) {
            System.out.println("Error desant videojocs: " + e.getMessage());
        }
    }
    
    // ---------- Mètodes del programa ----------
    
    private static int menu (Scanner sc) {
        int opcio = 0;
        System.out.println("\n--- Menu ---");
        System.out.println("1. Afegir videojoc");
        System.out.println("2. Llistar tots els videojocs");
        System.out.println("3. Cercar videojocs per titol");
        System.out.println("4. Actualitzar un videojoc");
        System.out.println("5. Eliminar un videojoc");
        System.out.println("6. Sortir del programa");
        System.out.println("------------");
        System.out.print("Tria una opcio: ");
        
        opcio = sc.nextInt();
        sc.nextLine(); // netejar buffer
        return opcio;
    }
    
    // 1. Afegir videojoc
    private static void afegirVideojoc(Scanner sc, ArrayList<Videojoc> videojocs) {
        System.out.println("\n--- Afegir nou videojoc ---");
        
        System.out.println("Titol: ");
        String titol = sc.nextLine();
        
        System.out.println("Genere: ");
        String genere = sc.nextLine();
        
        System.out.print("Any de llançament: ");
        int any = sc.nextInt();
        sc.nextLine(); // Netejar el buffer 
    
        System.out.print("Plataforma: ");
        String plataforma = sc.nextLine();
    
        System.out.print("Preu: ");
        double preu = sc.nextDouble();
        sc.nextLine(); // Netejar el buffer
        
        // Instanciem nou objecte
        Videojoc nouVideojoc = new Videojoc(titol, genere, any, plataforma, preu);
        
        // L'afegim a la llista en memòria
        videojocs.add(nouVideojoc);
        
        // Guardem la llista actualitzada al fitxer
        desarVideojocs(videojocs);
        
        System.out.println("Videojoc afegit i desat correctament");
    }
    
    // 2. Llistar tots els videojocs
    private static void llistarVideojocs(ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("\nNo hi ha cap videojoc registrat.");
        } else {
            System.out.println("\n=== LLISTA DE VIDEOJOCS ===");
            int i = 1;
            for (Videojoc v : videojocs) {
                System.out.println("[" + i + "]");
                System.out.println(v);
                System.out.println("---------------------------");
                i++;
            }
        }
    }
    
    // 3. Cercar videojocs per titol
    private static void cercarVideojoc(Scanner sc, ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("\nNo hi ha cap videojoc registrat.");
            return;
        }
        
        System.out.println("\n--- Cercar videojoc per titol ---");
        System.out.print("Introdueix el titol del joc: ");
        String titolCerca = sc.nextLine().toLowerCase();
        
        boolean trobat = false;
        System.out.println("\n=== RESULTAT ===");
        
        for (Videojoc v : videojocs) {
            // Fem comprovació de que el titol estigui dins la llista
            if (v.getTitol().toLowerCase().contains(titolCerca)) {
                System.out.println(v);
                System.out.println("---------------------------");
                trobat = true;
            }
        }
        
        if (!trobat) {
            System.out.println("La llista no conte el videojoc '" + titolCerca + "'.");
        }
    }
    
    // 4. Actualitzar un videojoc
    private static void actualitzarVideojoc(Scanner sc, ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("\nNo hi ha cap videojoc registrat.");
            return;
        }
        
        // Mostrem la llista de jocs
        System.out.println("\n=== LLISTA DE VIDEOJOCS ===");
        for (int i = 0; i < videojocs.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + videojocs.get(i).getTitol());
        }
        
        // Demanem el num. del joc a modificar
        System.out.println("\nIntrodueix el numero del videojoc a modificar: ");
        int num = sc.nextInt() - 1; // L'array comença amb 0, restem 1 unitat al num que posi usuari
        sc.nextLine(); // Netejem buffer
        
        // Validem que el numero de l'index existeix a la llista
        if (num < 0 || num >= videojocs.size()) {
            System.out.println("Numero del videojoc no valid.");
            return;
        }
        
        Videojoc v = videojocs.get(num);
        System.out.println("\n--- Modificant: " + v.getTitol() + " ---");
        
        //Demanem noves dades i fem servir els setters
        System.out.println("Nou titol: ");
        v.setTitol(sc.nextLine());
        
        System.out.println("Nou genere: ");
        v.setGenere(sc.nextLine());
        
        System.out.println("Nou any de llançament: ");
        v.setAnyLlançament(sc.nextInt());
        sc.nextLine(); // Netejem buffer
        
        System.out.println("Nova plataforma: ");
        v.setPlataforma(sc.nextLine());
        
        System.out.println("Nou preu: ");
        v.setPreu(sc.nextDouble());
        sc.nextLine(); // Netejem buffer
        
        // Guardem la llista actualitzada al fitxer
        desarVideojocs(videojocs);
        
        System.out.println("Videojoc actualitzat i desat correctament");
    }
    
    // 5. Eliminar un videojoc
    private static void eliminarVideojoc(Scanner sc, ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("\nNo hi ha cap videojoc registrat.");
            return;
        }
        
        // Mostrem la llista de jocs
        System.out.println("\n=== LLISTA DE VIDEOJOCS ===");
        for (int i = 0; i < videojocs.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + videojocs.get(i).getTitol());
        }
        
        // Demanem el num. del joc a modificar
        System.out.println("\nIntrodueix el numero del videojoc a eliminar: ");
        int num = sc.nextInt() - 1; // L'array comença amb 0, restem 1 unitat al num que posi usuari
        sc.nextLine(); // Netejem buffer
        
        // Validem que el numero de l'index existeix a la llista
        if (num < 0 || num >= videojocs.size()) {
            System.out.println("Numero del videojoc no valid.");
            return;
        }
        
        // Eliminem l'objecte i guardem canvis
        videojocs.remove(num);
        desarVideojocs(videojocs);
        
        System.out.println("Videojoc eliminat correctament");
    }
}
