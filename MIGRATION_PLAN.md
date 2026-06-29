# Migration Plan: Porting Berlin Skylarks to Compose Multiplatform

This document outlines the strategy for porting the existing Kotlin Multiplatform project to use *
*Compose Multiplatform (CMP)** for UI sharing across Android, Desktop (JVM), and Web (Wasm/JS)
targets.

## Overview

The project currently has a solid KMP foundation in the `shared` module for networking and data
models. However, the UI and business logic (ViewModels) are currently locked within the `androidApp`
module.

### Primary Goals

- **Target Platforms:** Desktop (JVM) and Web (Wasm/JS).
- **Secondary Goal:** Keep Android fully functional.
- **Out of Scope:** iOS (legacy implementation in `iosApp` will be ignored).

---

## 1. Infrastructure & Build Configuration

**Difficulty:** Low

### Steps:

1. **Root Build Config:** Add the Compose Multiplatform Gradle plugin.
2. **Shared Module Update:**
    - Add `jvm()` and `wasmJs()` targets to `shared/build.gradle.kts`.
    - Configure `commonMain` to include Compose dependencies.
3. **Module Restructuring:**
    - Create a new `composeApp` module for shared UI.
    - Setup entry points for Desktop (`main.kt`) and Web (`index.html`).

---

## 2. Dependency Migration

**Difficulty:** Medium

### Checklist:

- [ ] **Dependency Injection:** Replace **Hilt** (Android-only) with **manual DI** in the shared
  module. Hilt's `@HiltViewModel` and `@AssistedInject` will need to be refactored to a
  platform-agnostic approach.
- [x] **Image Loading:** Upgrade **Coil** from 2.x to **3.x** to support CMP.
- [ ] **Logging:** Replace `android.util.Log` with a multiplatform logger like **Napier** or *
  *Kermit**.
- [x] **Markdown:** Switched to a pure Compose Markdown renderer.

### Manual Dependency Injection Strategy

The migration should prefer **manual dependency injection** over introducing another DI framework.
The goal is to keep the dependency graph explicit, multiplatform-friendly, and easy to understand
without adding annotation processing, generated code, or platform-specific DI integrations.

#### Core Principles

- Use **constructor injection** for ViewModels, repositories, services, use cases, and platform
  abstractions.
- Keep production object creation in a small number of explicit **composition roots**.
- Do not let ordinary business/data/UI classes access a global service locator.
- Keep Android-specific dependencies out of `commonMain`.
- Prefer interfaces or small platform abstractions where common code needs platform-specific
  behavior.

#### Composition Roots

Manual DI should be organized around explicit containers:

- `AppContainer`
    - Owns app-wide dependencies such as database instances, Ktor clients, repositories,
      preferences, sync abstractions, and configuration.
- Feature-level containers/factories where useful:
    - `NewsContainer`
    - `ScoresContainer`
    - `ClubContainer`
    - `SettingsContainer`
- Platform-specific container creation:
    - Android creates the Android implementation of the container from the Android entry point.
    - Desktop creates the Desktop implementation from its `main` entry point.
    - Web creates the Web implementation from its application bootstrap.

The container should be used at the app/navigation boundary to create screens and ViewModels.
Domain, repository, and ViewModel classes should receive dependencies through constructors instead
of looking them up from the container directly.

#### ViewModel Creation

ViewModels should be refactored away from Hilt annotations and assisted injection.

Target shape:

- ViewModels expose ordinary constructors.
- Runtime navigation arguments are passed explicitly by the navigation layer or by a small manual
  factory.
- Shared ViewModels live in `commonMain` where possible.
- Platform-specific ViewModels should only be used when the logic cannot be reasonably abstracted.

#### Dependency Lifetime Rules

Manual DI should make lifetimes explicit:

- **Application-scoped**
    - Database
    - Ktor clients
    - Repositories
    - Preferences/DataStore wrappers
    - App configuration
    - Sync coordinators
- **Screen/ViewModel-scoped**
    - ViewModels
    - UI state holders
    - Use cases that hold screen-specific state
- **Transient**
    - Mappers
    - Formatters
    - Small pure helper objects

Avoid accidentally creating multiple instances of stateful dependencies such as databases,
repositories, clients, or preference stores.

#### Constructor Defaults Policy

Constructor defaults may be used only for simple, pure, stateless dependencies.

Acceptable examples:

- Pure mappers
- Simple formatters
- Stateless validators
- Constant configuration values

#### Migration Steps

1. Introduce a minimal manual `AppContainer` for existing production dependencies.
2. Move dependency construction from Hilt modules into explicit container properties/factories.
3. Refactor ViewModels to ordinary constructors.
4. Replace `hiltViewModel()` usage with manual ViewModel creation at the navigation boundary.
5. Replace assisted injection with small factory functions for route-dependent ViewModels.
6. Move platform-neutral ViewModels and business logic to `shared/commonMain`.
7. Add platform-specific container creation for Android, Desktop, and Web.
8. Remove Hilt annotations and Gradle dependencies once no longer used.
9. Add test containers or direct fake dependency construction for unit/integration tests.

---

## 3. Data Layer Migration

**Difficulty:** Low to Medium

### Checklist:

- [ ] **Room Database:** Implement platform-specific database builders for Desktop (using `SQLite`
  driver) and Web (using `Wasm` driver).
- [ ] **DataStore:** Move the `UserPreferencesRepository` and DataStore configuration to `shared`.
  Implement platform-specific `producePath` for Desktop and Web.
- [ ] **DateTime:** Refactor `DateTimeUtility` to avoid `java.time` in `commonMain` or provide a
  Wasm-compatible `actual` implementation.

---

## 4. Business Logic (ViewModels)

**Difficulty:** Medium

### Checklist:

- [ ] **Move ViewModels:** Relocate all ViewModels from `androidApp` to `shared` (commonMain).
- [ ] **Context Removal:** ViewModels currently use `Context` for asset reading. Replace this with *
  *CMP Resources** (`Res.readBytes()`).
- [ ] **Background Sync:** Abstract the **WorkManager** dependency. For Desktop and Web, implement a
  simple coroutine-based sync trigger.

---

## 5. UI Layer Migration

**Difficulty:** Medium

### Checklist:

- [ ] **Compose Components:** Move all Composable screens from `androidApp` to `shared` (
  commonMain).
- [ ] **Navigation:** Migrate from `androidx.navigation3` to the multiplatform-ready parts of
  Navigation 3 or a stable CMP alternative like **Voyager** if needed.
- [ ] **Resources:** Migrate hardcoded strings and Material Icons. Consider moving them to CMP
  Resources for better localization support.
- [x] **Platform Views:** Replace `AndroidView` with platform-agnostic Compose implementations.
- [ ] **Permissions:** Abstract calendar permissions used in `ScoresTopBar.kt` for non-Android
  platforms (for now: just skipping/mocking on Desktop/Web).

---

## 6. Target Specifics

### Desktop (JVM)

- **Window Management:** Define window size, title, and application icon.
- **Lifecycle:** Handle window close events and persistence of window state.

### Web (Wasm/JS)

- **Canvas Setup:** Configure the HTML canvas and loading screen.
- **Networking:** Ensure Ktor client is configured for browser environments (handling CORS).
- **Resources:** Ensure assets are correctly served and accessible.

---

## Difficulty Summary

| Task                | Difficulty | Notes                                                                   |
|:--------------------|:-----------|:------------------------------------------------------------------------|
| **Infrastructure**  | Low        | Standard KMP/CMP setup.                                                 |
| **DI Migration**    | Medium     | Hilt to Manual is straightforward but affects many files.               |
| **ViewModel Logic** | Medium     | Decoupling from Android APIs (Context, Assets, WorkManager).            |
| **UI Migration**    | Medium     | Most Compose code is already compatible; Navigation 3 is the main risk. |
| **Data Layer**      | Low        | Room/DataStore are already KMP-ready.                                   |
| **Web Target**      | Medium     | Wasm/JS can have specific environment and networking challenges.        |
