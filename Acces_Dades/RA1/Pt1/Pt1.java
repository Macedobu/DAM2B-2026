import java.io.*;

public class Pt1 {
    public static void main(String[] args) {
        int numCaracters = 0; // comptador de caracters
        int numLinies = 0; // comptador de linies
        int numParaules = 0; // comptador de paraules
        boolean dinsParaula = false;
        int[] frequencia = new int[65536];
        char charRepetit = ' '; // caracter mes repetit
        int repetit = 0; // frequencia caracter mes repetit
        int ultimCaracter = -1;
        
        // Creem el FileReader
        try (FileReader fr = new FileReader("text.txt")) {
            int c;
            
            System.out.println(" --Contingut de l'arxiu-- ");
            while ((c = fr.read()) != -1) { // -1 = final de fitxer
                char caracter = (char) c;
                System.out.print(caracter); // imprimim el text de l'arxiu caracter per caracter
                
                ultimCaracter = c;
                
                // Comptem els caracters
                if (caracter != '\n' && caracter != '\r') {
                    numCaracters++;
                }
                
                // Comptem les linies
                if (caracter == '\n') {
                    numLinies++;
                }
                
                // Comptem nombre de paraules
                if (Character.isWhitespace(caracter)) { // filtrem els espais
                    dinsParaula = false;
                } else if (!dinsParaula) {
                    dinsParaula = true;
                    numParaules++;
                }
                
                // Comptem i guardem el caracter mes repetit
                if (!Character.isWhitespace(caracter)) { // filtrem els espais
                    frequencia[c]++;
                    
                    // Actualitzem caracter mes repetit
                    if (frequencia[c] > repetit) { 
                        repetit = frequencia[c];
                        charRepetit = caracter;
                    }
                }
            }
            
            // Comptem la ultima linia
                if (ultimCaracter != -1 && ultimCaracter != '\n') {
                    numLinies++;
                }
                    
            System.out.println(" --Fi de l'arxiu-- \n");
            
            // Impresio resultats
            System.out.println("1. Nombre de caracters: " + numCaracters);
            System.out.println("2. Nombre de linies: " + numLinies);
            System.out.println("3. Nombre de paraules: " + numParaules);
            System.out.println("4. Caracter mes repetit: " + charRepetit + " (Frequencia: " + repetit + ")");
        
        // Gestio d'excepcions i tancament de fitxers
        } catch (FileNotFoundException e) {
            System.err.println("El fitxer no existeix." + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error en llegir el fitxer: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("No tens permisos per accedir al fitxer.");
        }
    }
}
