# Berlin Skylarks - Android App

## Project description
This is the Android app for the [Berlin Skylarks Baseball & Softball Club](https://www.tib-baseball.de/). Built in 100% native Kotlin with Jetpack Compose. Main purpose is to display up-to-date data for all team activities, supplied by the [API of the German Baseball Federation](https://bsm.baseball-softball.de) as well as addtional info from the team website (game reports, player rosters and practice times).


## Who this project is for
This project is mainly intended to be used by team members and interested people to get accurate information. I believe in Open Source and collaboration, therefore the whole codebase is open to be used by anyone.

It was not originally designed to be a white label solution, but the BSM part should be adaptable to other teams with little modifications. Class and global constants are used to designate team-specific information.


## Project dependencies (development)
* a reasonably fast computer
* Android Studio in the most recent version
* all necessary plugins
* a BSM API key to access team data
* an access token for the Skylarks-specific website data (app should run fine without it though)

### Instructions for use
1. Clone the repository and build in Android Studio. Gradle should fetch all necessary dependencies.


### Configure
1. Create `API_key.kt` and enter your key (a .dist file is provided to show the constant name).


### Run
1. Click `Run` or `Debug` in the top bar.


## Contributing guidelines
None so far - I do not expect PRs ;-)

## How to get help

* The developer can be reached via an email link in the app.

## Terms of use
This project is licensed under the GPLv3.
