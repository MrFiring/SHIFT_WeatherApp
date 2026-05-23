# AGENTS.md - SHIFT Weather App

## Build & Commands

### Essential Commands

- `./gradlew build` - Build the project
- `./gradlew test` - Run unit tests only
- `./gradlew build` (CI) - Full build including tests
- Java 11 required for CI, Java 17 for local development

### Project Structure

- Single Android module in `app/`
- Root namespace: `ru.mrfiring.shiftweatherapp`
- Package boundaries: `domain/`, `data/`, `presentation/`

## Key Dependencies & Architecture

### Core Stack

- **Compose 1.5.4** + Kotlin 1.9.21
- **Decompose 2.2.3** for navigation/state management
- **Koin 3.5.3** for DI
- **Room 2.6.1** with KSP codegen
- **Retrofit 2.9.0** + Moshi 1.15.0 for networking
- **Paging 3.2.1** with Room integration
- **Coroutines** for async operations

### Build Tools

- KSP (Kotlin Symbol Processing) for code generation
- Gradle with version catalogs (`gradle/libs.versions.toml`)
- Android Gradle Plugin 8.10.1

## Development Notes

### Code Generation

- KSP processes Moshi and Room annotations
- Build artifacts in `app/build/kspCaches/`
- Regenerate after changing data classes or database schema

### Testing

- Unit tests in `app/src/test/java/`
- MockK for mocking, Coroutines Test Rule for async tests
- Test UseCases directly without full app setup

### Dependencies

- Google Play Services Location for GPS
- Accompanist Permissions for runtime permissions
- Glide mentioned in README but not in build config (verify if needed)

## Important Configuration

- Min SDK 26, Target SDK 35, Compile SDK 35
- Application ID: `ru.mrfiring.shiftweatherapp`
- Proguard enabled for release builds
- Compose compiler version: 1.5.6