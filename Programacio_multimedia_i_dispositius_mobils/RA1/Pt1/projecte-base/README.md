# Conversor — projecte base (Pràctica 1B)

Projecte Android **mínim** per a la Pràctica 1B del mòdul 0489
(Programació Multimèdia i Dispositius Mòbils).

El projecte **arrenca i compila**, però **no fa res**: és el punt de partida
sobre el qual has de construir el conversor de temperatura, longitud i massa.

## Què hi ha

```
projecte-base/
├── app/
│   ├── build.gradle.kts                 Configuració del mòdul app
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml          Només hi és declarada MainActivity
│       ├── java/cat/institut/dam/conversor/
│       │   └── MainActivity.kt          Pantalla d'inici (amb els TODO)
│       └── res/
│           ├── layout/activity_main.xml Layout provisional del menú
│           └── values/
│               ├── strings.xml           Textos en català (model)
│               ├── colors.xml            Paleta de colors (tema clar)
│               └── themes.xml            Tema i estils de l'app
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/
│   ├── libs.versions.toml               Versions (Kotlin, AGP, llibreries)
│   └── wrapper/                         Gradle Wrapper
├── gradlew / gradlew.bat
└── proguard-rules.pro
```

## Què has d'afegir tu

| Fitxer | Què hi has de posar |
|---|---|
| `res/layout/activity_main.xml` | Els tres botons del menú |
| `res/layout/activity_temperatura.xml` | Formulari del conversor de temperatura |
| `res/layout/activity_longitud.xml` | Formulari del conversor de longitud |
| `res/layout/activity_massa.xml` | Formulari del conversor de massa |
| `java/.../MainActivity.kt` | Els tres Intents explícits |
| `java/.../TemperaturaActivity.kt` | Activity i càlculs de temperatura |
| `java/.../LongitudActivity.kt` | Activity i càlculs de longitud |
| `java/.../MassaActivity.kt` | Activity i càlculs de massa |
| `AndroidManifest.xml` | Declaració de les tres Activities |
| `res/values-es/strings.xml` | Traducció al castellà |
| `res/values-en/strings.xml` | Traducció a l'anglès |
| `res/values-night/colors.xml` | Paleta de colors del tema nocturn |
| `res/values-night/themes.xml` | Tema de l'app en mode fosc |

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
| Package | `cat.institut.dam.conversor` |
