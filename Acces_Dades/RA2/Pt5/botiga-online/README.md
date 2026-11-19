# Botiga Online (Java + JDBC + MySQL)

Aplicació de consola desenvolupada en Java amb JDBC per gestionar una botiga online sobre MySQL. Permet realitzar el CRUD de clients, productes i descomptes, crear comandes amb control d'estoc mitjançant transaccions (`commit`/`rollback`), aplicar descomptes amb `Savepoint` i consultar l'historial de comandes mitjançant consultes `JOIN`.

---

## Requisits previs

* **Java JDK** (17 o superior).
* **Apache NetBeans** (projecte configurat amb **Maven**; el connector JDBC de MySQL es descarrega automàticament a través del `pom.xml`).
* **MySQL Server** i **MySQL Workbench**.

---

## 1. Importació de la Base de Dades (MySQL Workbench)

1. Obre **MySQL Workbench** i connecta't a la teva instància local (`localhost:3306`).
2. Ves al menú superior **File > Open SQL Script...** i selecciona el fitxer `sql/schema.sql` situat a l'arrel d'aquest projecte.
3. Executa tot l'script fent clic a la icona del llamp (**Execute**). Això crearà la base de dades `botiga`, les taules (`Clients`, `Productes`, `Comandes`, `LiniesComanda`, `Descomptes`) i les dades inicials de prova.
4. Actualitza el panell **Schemas** (icona de refresc) per comprovar que la base de dades `botiga` apareix correctament.

---

## 2. Configuració de l'usuari i contrasenya

Com que la connexió s'ha desenvolupat en un entorn local, cal adaptar les credencials al teu servidor MySQL abans d'executar el programa:

1. Obre el projecte a **Apache NetBeans** (**File > Open Project...**).
2. Obre el fitxer `src/main/java/util/Connexio.java`.
3. Modifica les constants `USER` i `PASSWORD` amb l'usuari i la contrasenya del teu MySQL Workbench (actualment configurades per defecte com `"root"` i `"0000"`):

```java
private static final String URL = "jdbc:mysql://localhost:3306/botiga?useSSL=false&serverTimezone=UTC";
private static final String USER = "el_teu_usuari";
private static final String PASSWORD = "la_teva_contrasenya";
```

---

## 3. Compilació i execució (Apache NetBeans + Maven)

1. Fes clic dret sobre el projecte al panell **Projects** de NetBeans i selecciona **Clean and Build** per descarregar les dependències de Maven i compilar el codi.
2. Obre la classe principal **`com.botiga.botiga.online.BotigaOnline`** (`src/main/java/com/botiga/botiga/online/BotigaOnline.java`). *(Nota: aquesta classe substitueix la classe `App.java` indicada de manera orientativa a l'enunciat).*
3. Fes clic dret sobre el fitxer `BotigaOnline.java` i selecciona **Run File** (o prem `Shift + F6`) per iniciar el menú interactiu a la consola.

---

## Estructura del projecte

```text
botiga-online/
├── pom.xml                                        # Dependències Maven (MySQL Connector/J)
├── README.md                                      # Instruccions de configuració i ús
├── sql/
│   └── schema.sql                                 # Script de creació de la BD i dades de prova
└── src/main/java/
    ├── com/botiga/botiga/online/
    │   └── BotigaOnline.java                      # Classe principal amb el menú de consola
    ├── util/
    │   └── Connexio.java                          # Gestió de la connexió JDBC
    ├── model/
    │   ├── Client.java                            # POJO Client
    │   ├── Producte.java                          # POJO Producte
    │   ├── Comanda.java                           # POJO Comanda
    │   ├── LiniaComanda.java                      # POJO LiniaComanda
    │   └── Descompte.java                         # POJO Descompte
    └── dao/
        ├── ClientDAO.java                         # CRUD de clients
        ├── ProducteDAO.java                       # CRUD de productes
        ├── DescompteDAO.java                      # CRUD de descomptes
        ├── ComandaDAO.java                        # Transaccions, control d'estoc i Savepoints
        └── ConsultesDAO.java                      # Consultes amb JOIN per client
```

---

> **Nota:** La documentació d'aquest `README.md` s'ha redactat amb el suport d'intel·ligència artificial i ha estat revisada, adaptada i validada per l'autor del projecte.