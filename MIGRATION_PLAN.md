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
    - Create a new `composeApp` module for shared UI or repurpose `shared` to include it.
    - Setup entry points for Desktop (`main.kt`) and Web (`index.html`).

---

## 2. Dependency Migration

**Difficulty:** Medium

### Checklist:

- [ ] **Dependency Injection:** Replace **Hilt** (Android-only) with **Koin** or **manual DI** in
  the shared module. Hilt's `@HiltViewModel` and `@AssistedInject` will need to be refactored to a
  platform-agnostic approach.
- [x] **Image Loading:** Upgrade **Coil** from 2.x to **3.x** to support CMP.
- [ ] **Logging:** Replace `android.util.Log` with a multiplatform logger like **Napier** or *
  *Kermit**.
- [x] **Markdown:** Switched to a pure Compose Markdown renderer.

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
- [ ] **Platform Views:** Replace `AndroidView` with platform-agnostic Compose implementations.
- [ ] **Permissions:** Abstract calendar permissions used in `ScoresTopBar.kt` for non-Android
  platforms (likely just skipping/mocking on Desktop/Web if not applicable).

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
| **DI Migration**    | Medium     | Hilt to Koin/Manual is straightforward but affects many files.          |
| **ViewModel Logic** | Medium     | Decoupling from Android APIs (Context, Assets, WorkManager).            |
| **UI Migration**    | Medium     | Most Compose code is already compatible; Navigation 3 is the main risk. |
| **Data Layer**      | Low        | Room/DataStore are already KMP-ready.                                   |
| **Web Target**      | Medium     | Wasm/JS can have specific environment and networking challenges.        |
