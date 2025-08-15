# Berlin Skylarks - Mobile App

## Project description
This is the mobile app for the [Berlin Skylarks Baseball & Softball Club](https://www.tib-baseball.de/).
It's built with a focus on modern Android development practices, featuring:
*   100% native Kotlin for the Android-specific parts.
*   Jetpack Compose for the UI layer.
*   **Kotlin Multiplatform (KMP)** for shared business logic, data models, and repository layers, enabling potential future expansion to other platforms (like iOS).

The main purpose is to display up-to-date data for all team activities, supplied by the [API of the German Baseball Federation](https://bsm.baseball-softball.de) as well as additional info from the team website (game reports, player rosters, and practice times).

## Kotlin Multiplatform Structure

This project has recently adopted a Kotlin Multiplatform (KMP) structure to share common code, primarily for data handling and business logic, between different potential platforms.

*   **`shared` module:** This is the heart of the multiplatform logic.
    *   Located at `/shared`.
    *   Contains common Kotlin code (`commonMain`) for data models (like `Player.kt`, `Team.kt`), repository patterns, network service definitions (expectations), and utility functions that are platform-agnostic.
    *   Platform-specific implementations (`androidMain`, `iosMain` - if added later) for `expect` declarations (e.g., for HTTP clients, local storage, date/time utilities before `kotlinx-datetime` was fully adopted for this) reside here.
    *   Uses `kotlinx-datetime` for multiplatform date and time handling.
    *   Uses `kotlinx-serialization` for JSON parsing.
*   **`androidApp` module (formerly `app`):** This is the Android-specific application module.
    *   Located at `/androidApp`
    *   Depends on the `shared` module to access common business logic and data models.
    *   Contains Android-specific UI (Jetpack Compose), `Activity`s, `ViewModel`s, Android SDK interactions, and platform-specific implementations if any `actual` implementations are defined in `androidMain` within the `shared` module.

This structure allows for:
*   Maximizing code reuse.
*   Ensuring consistent business logic across platforms.
*   Easier maintenance of shared functionalities.

## Who this project is for
This project is mainly intended to be used by team members and interested people to get accurate information. I believe in Open Source and collaboration, therefore the whole codebase is open to be used by anyone.

It was not originally designed to be a white label solution, but the BSM part and the shared KMP logic should be adaptable to other teams with appropriate modifications. Class and global constants are used to designate team-specific information.

## Project dependencies (development)
*   A reasonably fast computer
*   Android Studio in the most recent version (with Kotlin Multiplatform Mobile plugin if targeting iOS)
*   All necessary plugins
*   A BSM API key to access team data
*   An access token for the Skylarks-specific website data (app should run fine without it though)
*   (If targeting iOS via KMP) Xcode and relevant iOS development tools.

### Instructions for use
1.  Clone the repository.
2.  Open the project in Android Studio. It should recognize the KMP structure.
3.  Gradle should fetch all necessary dependencies for both the `shared` and `androidApp` modules.
4.  Build the `androidApp` module.

### Configure
1.  Create `API_key.kt` in the appropriate module (likely `androidApp` or a configuration file read by the `shared` module) and enter your key (a `.dist` file or instructions should clarify the constant name and location).

### Run
1.  Select the `androidApp` run configuration in Android Studio.
2.  Click `Run` or `Debug` in the top bar.

## Contributing guidelines
None so far - I do not expect PRs ;-)

## How to get help
*   The developer can be reached via an email link in the app.

## Terms of use
This project is licensed under the GPLv3.

