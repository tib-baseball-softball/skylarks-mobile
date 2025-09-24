//
//  ScoresViewModel.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 24.09.25.
//

import Foundation
import sharedKit

@MainActor
@Observable
class ScoresViewModel {
    private let gameRepository: GameRepository
    public let networkManager: NetworkManager
    public let calendarManager: CalendarManager

    init(
        gameRepository: GameRepository,
        networkManager: NetworkManager,
        calendarManager: CalendarManager
    ) {
        self.gameRepository = gameRepository
        self.networkManager = networkManager
        self.calendarManager = calendarManager
    }

    var showCalendarDialog = false
    var showEventAlert = false
    var showAlertNoNetwork = false
    var showAlertNoGames = false
    var showAlertNoAccess = false
    var loadingInProgress = false
    var scoresLoaded = false

    //TODO: localise
    var selectedTeam = LEAGUEGROUP_ALL
    var selectedTeamID: Int = LEAGUEGROUP_ALL.id
    var selectedTimeframe = Gameday.current

    var filterTeams: [LeagueGroup] = [LEAGUEGROUP_ALL]

    var gamescores = [GameScore]()
    var leagueGroups = [LeagueGroup]()
    var searchResults = [GameScore]()
    var skylarksGamescores = [GameScore]()

    func loadLeagueGroups(selectedSeason: Int) async {
        //reset filter options to default
        filterTeams = [LEAGUEGROUP_ALL]

        let leagueGroupsURL = URL(
            string:
                "https://bsm.baseball-softball.de/league_groups.json?filters[seasons][]="
                + "\(selectedSeason)" + "&api_key=" + apiKey
        )!

        do {
            leagueGroups = try await fetchBSMData(
                url: leagueGroupsURL,
                dataType: [LeagueGroup].self
            )
        } catch {
            print("Request failed with error: \(error)")
        }

        //add teams to filter
        for leagueGroup in leagueGroups {
            filterTeams.append(leagueGroup)
        }
        await loadGamesAndProcess(selectedSeason: selectedSeason)
    }

    func loadGamesAndProcess(selectedSeason: Int) async {
        if networkManager.isConnected == false {
            showAlertNoNetwork = true
        }
        loadingInProgress = true
        var gameURLSelected: URL? = nil

        //if we're not filtering by any league, then we do not use the URL parameter at all
        if selectedTeam == LEAGUEGROUP_ALL {
            gameURLSelected = URL(
                string:
                    "https://bsm.baseball-softball.de/clubs/\(AppSettings.SKYLARKS_BSM_ID)/matches.json?filters[seasons][]=\(selectedSeason)&filters[gamedays][]=\(selectedTimeframe.rawValue)&api_key=\(apiKey)"
            )!
        }
        //in any other case we filter the API request by league ID
        else {
            gameURLSelected = URL(
                string:
                    "https://bsm.baseball-softball.de/matches.json?filters[seasons][]=\(selectedSeason)&filters[leagues][]=\(selectedTeamID)&filters[gamedays][]=\(selectedTimeframe.rawValue)&api_key=\(apiKey)"
            )!
        }

        do {
            gamescores = try await fetchBSMData(
                url: gameURLSelected!,
                dataType: [GameScore].self
            )
        } catch {
            print("Request failed with error: \(error)")
        }

        for (index, _) in gamescores.enumerated() {
            gamescores[index].addDates()
            gamescores[index].determineGameStatus()
        }

        //set up separate object for just Skylarks games
        skylarksGamescores = gamescores.filter({ gamescore in
            gamescore.home_team_name.contains("Skylarks")
                || gamescore.away_team_name.contains("Skylarks")
        })

        loadingInProgress = false
    }

    func setTeamID() async {
        //set it back to 0 to make sure it does not keep the former value
        selectedTeamID = LEAGUEGROUP_ALL.id
        for leagueGroup in leagueGroups where leagueGroup == selectedTeam {
            selectedTeamID = leagueGroup.id
        }
    }

    //---------------------------------------------------------//
    //-------------------func shortcuts------------------------//
    //---------------------------------------------------------//

    func refresh(selectedSeason: Int) async {
        gamescores = []
        scoresLoaded = false
        await loadGamesAndProcess(selectedSeason: selectedSeason)
    }

    func initialLoad(selectedSeason: Int) {
        if gamescores.isEmpty && scoresLoaded == false {
            Task {
                await loadLeagueGroups(selectedSeason: selectedSeason)
            }
            scoresLoaded = true
        }
    }

    func teamChanged(selectedSeason: Int) {
        gamescores = []
        scoresLoaded = false
        Task {
            await setTeamID()
            await loadGamesAndProcess(selectedSeason: selectedSeason)
        }
    }

    func timeframeChanged(selectedSeason: Int) {
        gamescores = []
        scoresLoaded = false
        Task {
            await loadGamesAndProcess(selectedSeason: selectedSeason)
        }
    }

    func seasonChanged() {
        gamescores = []
        skylarksGamescores = []
        scoresLoaded = false
    }
}
