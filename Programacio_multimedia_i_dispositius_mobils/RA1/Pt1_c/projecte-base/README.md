# Contactes — projecte base (Pràctica 1C)

Projecte Android **mínim** per a la Pràctica 1C del mòdul 0489
(Programació Multimèdia i Dispositius Mòbils).

El projecte **arrenca i compila**, però la llista encara no fa res: és el punt de partida
sobre el qual has de construir la llista de contactes. Inclou l'adaptació bàsica a
l'edge-to-edge perquè les barres del sistema no tapin el contingut.

## Què hi ha

```
projecte-base/
├── app/
│   ├── build.gradle.kts                 Configuració del mòdul app
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml          Permisos i MainActivity (TODO 7)
│       ├── assets/contactes.json        Còpia local dels contactes
│       ├── java/cat/institut/dam/contactes/
│       │   ├── MainActivity.kt          Pantalla de la llista (amb els TODO)
│       │   └── Finestres.kt             Insets i edge-to-edge (ja fet)
│       └── res/
│           ├── drawable/                Icones i fons (ja fets)
│           ├── layout/activity_main.xml Llista de contactes (ja feta)
│           ├── values/
│           │   ├── strings.xml           Textos en català (model)
│           │   ├── colors.xml            Paleta de colors (tema clar)
│           │   └── themes.xml            Tema i estils de l'app
│           ├── values-es/
│           │   └── strings.xml           Traducció al castellà
│           ├── values-en/
│           │   └── strings.xml           Traducció a l'anglès
│           └── values-night/
│               ├── colors.xml            Paleta de colors del tema nocturn
│               └── themes.xml            Tema de l'app en mode fosc
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/
│   ├── libs.versions.toml               Versions (Kotlin, AGP, llibreries)
│   └── wrapper/                         Gradle Wrapper
└── gradlew / gradlew.bat
```

## Què has d'afegir tu

| Fitxer | Què hi has de posar |
|---|---|
| `java/.../Contacte.kt` | Model de dades i lectura del JSON |
| `java/.../ContactesRepository.kt` | Dades compartides, favorits i cerca |
| `java/.../ContactesService.kt` | Petició HTTP, còpia local i baixada d'imatges |
| `java/.../ContacteAdapter.kt` | Adapter del `RecyclerView` |
| `java/.../ContacteDetallActivity.kt` | Fitxa del contacte i Intents implícits |
| `java/.../Notificacions.kt` | Canal i notificació de "contactes carregats" |
| `res/layout/item_contacte.xml` | Fila de la llista |
| `res/layout/activity_detall.xml` | Pantalla de detall |
| `java/.../MainActivity.kt` | Els TODO: carregar, filtrar i obrir el detall |

## Els TODO de `MainActivity.kt`

| TODO | Què has de fer |
|---|---|
| 1 | Crear l'adapter i assignar-lo al `RecyclerView` |
| 2 | Filtrar la llista quan l'usuari escriu al cercador |
| 3 | Recarregar els contactes amb el botó |
| 4 | Demanar el permís de notificacions **després** de carregar les dades |
| 5 | Carregar els contactes (xarxa i, si no hi ha, còpia local) |
| 6 | Avisar l'usuari quan denega el permís de notificacions |

El `TODO 7` és al `AndroidManifest.xml`: declarar `ContacteDetallActivity` amb
`android:exported="false"`.

## Com obrir-lo

1. Obre **Android Studio** → `File` → `Open` → selecciona aquesta carpeta
   (`projecte-base/`), la que conté `settings.gradle.kts`.
2. Espera que acabi el **Gradle Sync**.
3. Tria un emulador (o un mòbil connectat) al desplegable de dispositius.
4. Prem el botó verd **Run** (▶).

## Versions

| Component | Versió |
|---|---|
| Kotlin | 1.9.24 |
| AGP | 8.6.1 |
| Gradle | 8.7 |
| compileSdk / targetSdk | 35 |
| minSdk | 24 |
| Package | `cat.institut.dam.contactes` |
| Dependència nova | `androidx.recyclerview:recyclerview:1.3.2` |

## Notes

- `assets/contactes.json` té 10 contactes, 3 dels quals són a Barcelona. L'API remota també
  torna 10 usuaris, però cap és de Barcelona.
- L'app ja adapta els insets de les barres del sistema amb `Finestres.kt`. No cal tocar-ho per
  resoldre els `TODO`, però has de comprovar que la interfície no queda tapada en API 35.
