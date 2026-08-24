# AGENTS.md - SHIFT Weather App

## Build & Commands

### Essential Commands

- `./gradlew compileDebugKotlin test` - Build the project without lint
- `./gradlew test` - Run unit tests only
- `./gradlew build` (CI) - Full build including tests
- Java 11 required for CI, Java 17 for local development
- Use IntelliJ IDEA MCP tools (`intelij-idea_*`)
- Only use `bash` for git operations and commands not covered by IDEA tools

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

### Testing

- Unit tests in `app/src/test/java/`
- MockK for mocking, Coroutines Test Rule for async tests
- Test UseCases directly without full app setup

### Dependencies

- Google Play Services Location for GPS
- Accompanist Permissions for runtime permissions

## Important Configuration

- Min SDK 26, Target SDK 36, Compile SDK 36
- Application ID: `ru.mrfiring.shiftweatherapp`
- Proguard enabled for release builds
- Compose compiler version: 1.5.6

## Coding Principles

- Method names should be descriptive and convey intent (e.g., `getDarkTheme()` not `get()`)
- Follow existing patterns exactly (use Onboarding as reference)
- Keep DI modules separate for repositories vs use cases
- Use proper scoping: single for SharedPreferences, factory for repositories/use cases
- Components should manage global state using Decompose `MutableValue`
- Remove redundant methods and eliminate unnecessary abstractions
- Inline lambda handlers where appropriate
- Don't modify UI components unless absolutely necessary
- Components depend on use cases, not repositories
- Separate composables located on the same level by one empty line