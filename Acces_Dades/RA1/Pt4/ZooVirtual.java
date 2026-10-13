import java.util.Scanner;

// Escriptura
import java.io.PrintStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// Lectura i escriptura
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ZooVirtual {

    public static void main(String[] args) {
        
        // Forcem UTF-8 per la consola i la sortida
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            
        }

        // Inicialitzem l'scanner i el fitxer
        Scanner sc = new Scanner(System.in, "UTF-8");
        int opcio = 0;
        File fitxer = new File("zoo.xml");
        Document doc = null;
        
        try{
            // Creem el DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Revisem si el fitxer existeix. Si no existeix el creem i també creem l'arrel zoo
            if (fitxer.exists()) {
                doc = builder.parse(fitxer);
            } else {
                doc = builder.newDocument();
                
                // Element arrel
                Element arrel = doc.createElement ("zoo");
                doc.appendChild(arrel);
            }
            
        } catch (Exception e) {
            System.out.println("Error al carregar o crear el fitxer: " + e.getMessage());
            return;
        }
        
        // Bucle del menú
        do {
            opcio = menu(sc);
            
            switch (opcio) {
                case 1:
                    afegirAnimal(sc, doc);
                    desarXML(fitxer, doc);
                    break;
                case 2:
                    llistarAnimals(doc);
                    break;
                case 3:
                    actualitzarAnimal(sc, doc);
                    desarXML(fitxer, doc);
                    break;
                case 4:
                    comptarAnimals(doc);
                    break;
                case 5:
                    filtrarAnimals(sc, doc);
                    break;
                case 6:
                    eliminarAnimal(sc, doc);
                    desarXML(fitxer, doc);
                    break;
                case 7:
                    desarXML(fitxer, doc);
                    System.out.println("Sortint del programa...");
                    break;
                default:
                    System.out.println("Opció no vàlida!");
            }
            
        } while (opcio != 7);
        
        sc.close();
    }
    
    // Menú
    private static int menu (Scanner sc) {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Afegir animal");
        System.out.println("2. Llistar tots els animals");
        System.out.println("3. Actualitzar un animal");
        System.out.println("4. Comptar animals");
        System.out.println("5. Filtrar animals");
        System.out.println("6. Eliminar un animal");
        System.out.println("7. Sortir del programa");
        System.out.println("------------");
        
        return llegirEnter(sc, "Tria una opció: ");
    }
    
    // Afegir animals
    private static void afegirAnimal(Scanner sc, Document doc) {
        System.out.println("\n--- Afegir nou animal ---");
        
        // Generem una id
        int id = obtenirId(doc);
        
        // Demanem dades per teclat
        System.out.print("Nom: ");
        String nom = sc.nextLine().trim();
        
        System.out.print("Espècie: ");
        String especie = sc.nextLine().trim();
        
        System.out.print("Aliment: ");
        String aliment = sc.nextLine().trim();
        
        int edat = llegirEnter(sc, "Edat: ");
        
         // Creem l'element animal amb el seu atribut id
         Element animal = doc.createElement("animal");
         animal.setAttribute("id", String.valueOf(id));
         
         // Creem els seus subelements
         Element eNom = doc.createElement("nom");
         eNom.setTextContent(nom);
         animal.appendChild(eNom);
         
         Element eEspecie = doc.createElement("especie");
         eEspecie.setTextContent(especie);
         animal.appendChild(eEspecie);
         
         Element eAliment = doc.createElement("aliment");
         eAliment.setTextContent(aliment);
         animal.appendChild(eAliment);
         
         Element eEdat = doc.createElement("edat");
         eEdat.setTextContent(String.valueOf(edat));
         animal.appendChild(eEdat);
         
         // Afegim l'animal a l'arrel zoo
         doc.getDocumentElement().appendChild(animal);
         System.out.println("Animal afegit correctament!");
    }
    
    // Llistar animals
    private static void llistarAnimals (Document doc) {
        
        // Busquem dins del document DOM totes les etiquetes animal i les guardem en una llista
        NodeList llistaAnimals = doc.getElementsByTagName("animal");
        
        // Comprovació de si hi ha animals registrats
        if (llistaAnimals.getLength() == 0) {
            System.out.println("\nNo hi ha animals registrats.");
            return;
        }
        
        // Bucle per llistar els animals
        System.out.println("\n--- Llista d'animals ---");
        for (int i = 0; i < llistaAnimals.getLength(); i++) {
            Node node = llistaAnimals.item(i);
            
            // Ens assegurem de filtrar els tipus de nodes
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                
                // Castejem l'objecte node a element per poder llegir els seus atributs i imprimim
                Element animal = (Element) node;
                imprimirAnimal(animal);
            }
        }
    }
    
    // Actualitzar animal
    private static void actualitzarAnimal (Scanner sc, Document doc) {
        
        // Demanem id
        int idBuscada = llegirEnter(sc, "\nIntrodueix l'ID de l'animal que vols modificar: ");
       
        NodeList llistaAnimals = doc.getElementsByTagName("animal");
        boolean trobat = false;
        
        // Bucle per buscar l'animal i actualitzar-lo
        for (int i = 0; i < llistaAnimals.getLength(); i++) {
            Node node = llistaAnimals.item(i);
            
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element animal = (Element) node;
                
                // Si trobem l'animal demanem les noves dades
                if (animal.getAttribute("id").equals(String.valueOf(idBuscada))) {
                    trobat = true;
                    System.out.println("Animal trobat. Introdueix les noves dades:");
                    
                    System.out.println("Nou nom: ");
                    animal.getElementsByTagName("nom").item(0).setTextContent(sc.nextLine().trim());
                    
                    System.out.println("Nova espècie: ");
                    animal.getElementsByTagName("especie").item(0).setTextContent(sc.nextLine().trim());
                    
                    System.out.println("Nou aliment: ");
                    animal.getElementsByTagName("aliment").item(0).setTextContent(sc.nextLine().trim());
                    
                    int novaEdat = llegirEnter(sc, "Nova edat: ");
                    animal.getElementsByTagName("edat").item(0).setTextContent(String.valueOf(novaEdat));
                    
                    System.out.println("Animal actualitzat correctament!");
                    break;
                }
            }
        }
        
        // Si no trobem l'animal missatge d'error
        if (!trobat) {
            System.out.println("No existeix cap animal amb l'ID " + idBuscada + ".");
        }
    }
    
    // Comptar animals
    private static void comptarAnimals(Document doc) {
        NodeList llistaAnimals = doc.getElementsByTagName("animal");
        System.out.println("\nTotal d'animals al zoo: " + llistaAnimals.getLength());
    }
    
    // Filtrar animals
    private static void filtrarAnimals(Scanner sc, Document doc) {
        
        // Demanem el filtre
        System.out.println("Introdueix la paraula per filtrar (Pot ser el nom, espècie, aliment o edat):");
        String filtre = sc.nextLine().trim().toLowerCase();
        
        NodeList llistaAnimals = doc.getElementsByTagName("animal");
        boolean trobat = false;
        
        System.out.println("\n--- Animals amb '" + filtre + "' ---");
        
        // Bucle per buscar animals que coincideixin amb el filtre
        for (int i = 0; i < llistaAnimals.getLength(); i++) {
            Node node = llistaAnimals.item(i);
            
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element animal = (Element) node;
                
                // Extreiem el contingut de tots els subelements
                String nom = animal.getElementsByTagName("nom").item(0).getTextContent().toLowerCase();
                String especie = animal.getElementsByTagName("especie").item(0).getTextContent().toLowerCase();
                String aliment = animal.getElementsByTagName("aliment").item(0).getTextContent().toLowerCase();
                String edat = animal.getElementsByTagName("edat").item(0).getTextContent().toLowerCase();
                
                // Comprovem si la paraula filtrada es troba en alguns dels subelements
                if (nom.contains(filtre) || especie.contains(filtre) || aliment.contains(filtre) || edat.contains(filtre)) {
                    imprimirAnimal(animal);
                    trobat = true;
                }
            }
        }
        
        // Si no trobem coincidencies missatge d'error
        if (!trobat) {
            System.out.println("No s'ha trobat cap registre amb la paraula introduïda.");
        }
    }
    
    // Eliminar animal
    private static void eliminarAnimal (Scanner sc, Document doc) {
        
        //Demanem id
        int idBuscada = llegirEnter(sc, "\nIntrodueix l'ID de l'animal a eliminar: ");
        NodeList llistaAnimals = doc.getElementsByTagName("animal");
        boolean trobat = false;
        
        for (int i = 0; i < llistaAnimals.getLength(); i++) {
            Node node = llistaAnimals.item(i);
            
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element animal = (Element) node;
                
                // Bucle per eliminar l'animal
                if (animal.getAttribute("id").equals(String.valueOf(idBuscada))) {
                    
                    // removeChild per eliminar el node (en DOM)
                    animal.getParentNode().removeChild(animal);
                    System.out.println("Animal eliminat correctament!");
                    trobat = true;
                    break;
                }
            }
        }
        
        // Si no trobem l'animal missatge d'error
        if (!trobat) {
            System.out.println("No existeix cap animal amb l'ID " + idBuscada + ".");
        }
    }
        
    // Metode per gestionar errors amb int
    private static int llegirEnter(Scanner sc, String missatge) {
        while (true) {
            System.out.print(missatge);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error. Has d'introduir un número enter vàlid.");
            }
        }
    }
    
    // Metode per calcular id autoincremental
    private static int obtenirId(Document doc) {
        NodeList llistaAnimals = doc.getElementsByTagName("animal");
        int maxId = 0;
        
        // Bucle recorrer la llista d'animals
        for (int i = 0; i < llistaAnimals.getLength(); i++) {
            Node node = llistaAnimals.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element animal = (Element) node;
                
                // Llegim les id i guardem la mes alta
                try {
                    int idActual = Integer.parseInt(animal.getAttribute("id"));
                    if (idActual > maxId) {
                        maxId = idActual;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        // Retornem la id mes alta +1
        return maxId +1;
    }
    
    // Metode per la impresio d'un animal
    private static void imprimirAnimal(Element animal) {
        String id = animal.getAttribute("id");
        String nom = animal.getElementsByTagName("nom").item(0).getTextContent();
        String especie = animal.getElementsByTagName("especie").item(0).getTextContent();
        String aliment = animal.getElementsByTagName("aliment").item(0).getTextContent();
        String edat = animal.getElementsByTagName("edat").item(0).getTextContent();
        
        System.out.println("Animal #" + id);
        System.out.println(" Nom: " + nom);
        System.out.println(" Espècie: " + especie);
        System.out.println(" Aliment: " + aliment);
        System.out.println(" Edat: " + edat);
        System.out.println();
    }
    
    // Metode per desar les dades en XML
    private static void desarXML (File fitxer, Document doc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fitxer);
            
            // Desem l'arxiu utilitzant tabulacions + UTF-8
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println("Error al desar el fitxer: " + e.getMessage());
        }
    }
}


