import java.io.*;
import java.util.Scanner;

public class Pt2 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introdueix la clau del xifrat (un numero enter): ");

        int clau = scanner.nextInt();

        // Processos de xifrat i desxifrat
        xifrar("entrada.txt", "xifrat.txt", clau);
        desxifrar("xifrat.txt", "desxifrat.txt", clau);

        scanner.close();
    }

    private static void xifrar(String entrada, String sortida, int clau) {

        // Obrim BufferedReader per llegir l'arxiu
        try (BufferedReader br = new BufferedReader(
                new FileReader(entrada))) {

            // Obrim BufferedWriter per escriure el text xifrat
            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(sortida))) {

                String linia;
                System.out.println("------------------------------------------------------------------------");
                System.out.println("El contingut de l'arxiu " + entrada + " es: ");

                // Bucle per llegir linia x linia i realitzar el xifrat
                while ((linia = br.readLine()) != null) {
                    System.out.println(linia); // Impresio linia
                    String liniaInvertida = new StringBuilder(linia).reverse().toString(); // Inversio d'ordre linia
                    String liniaXifrada = xifratCesar(liniaInvertida, clau); // Crida al metode de xifratge Cesar

                    bw.write(liniaXifrada); // Escriptura de la linia ja xifrada a xifrat.txt
                    bw.newLine(); // Canviem de linia
                }
                System.out.println("........................................................................");
                System.out.println("Xifrat realitzat i desat a: " + sortida);

                // Gestio d'excepcions d'escriptura
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
            // Gestio d'excepcions de lectura
        } catch (FileNotFoundException e) {
            System.out.println("El fitxer no existeix.");

        } catch (IOException e) {
            System.out.println("S'ha produït un error de lectura.");

        } catch (SecurityException e) {
            System.out.println("No tens permisos per accedir al fitxer.");
        }
    }

    private static void desxifrar(String entrada, String sortida, int clau) {

        // Obrim BufferedReader per llegir l'arxiu
        try (BufferedReader br = new BufferedReader(
                new FileReader(entrada))) {

            // Obrim BufferedWriter per escriure el text desxifrat
            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(sortida))) {

                String linia;
                System.out.println("------------------------------------------------------------------------");
                System.out.println("El contingut de l'arxiu " + entrada + " es: ");

                // Bucle per llegir linia x linia i realitzar el desxifrat
                while ((linia = br.readLine()) != null) {
                    System.out.println(linia); // Impresio linia
                    String liniaDesxifrada = xifratCesar(linia, -clau); // Crida al metode de xifratge Cesar
                    String liniaOriginal = new StringBuilder(liniaDesxifrada).reverse().toString(); // Inversio d'ordre linia

                    bw.write(liniaOriginal); // Escriptura de la linia ja desxifrada a desxifrat.txt
                    bw.newLine(); // Canviem de linia
                }
                System.out.println("........................................................................");
                System.out.println("Desxifrat realitzat i desat a: " + sortida);

                // Gestio d'excepcions d'escriptura
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
            // Gestio d'excepcions de lectura
        } catch (FileNotFoundException e) {
            System.out.println("El fitxer no existeix.");

        } catch (IOException e) {
            System.out.println("S'ha produït un error de lectura.");

        } catch (SecurityException e) {
            System.out.println("No tens permisos per accedir al fitxer.");
        }
    }

    private static String xifratCesar(String linia, int clau) {
        StringBuilder textXifrat = new StringBuilder();
        for (char c : linia.toCharArray()) {

            // Sumem la posicio (Unicode) del caracter amb la clau del xifratge i ho castejem a caracter
            textXifrat.append((char) (c + clau));
        }
        return textXifrat.toString();
    }
}
