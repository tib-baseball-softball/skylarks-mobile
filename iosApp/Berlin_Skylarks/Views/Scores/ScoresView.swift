//
//  ScoresView.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 26.12.20.
//

import EventKit
import SwiftUI

struct ScoresView: View {
    @Environment(\.colorScheme) var colorScheme
    
    @Environment(CalendarManager.self) var calendarManager: CalendarManager
    
    @Environment(NetworkManager.self) var networkManager: NetworkManager
    @Environment(AppContainer.self) var appContainer: AppContainer
    
    @State private var showAlertNoNetwork = false
    
    @State private var gamescores = [GameScore]()
    @State private var leagueGroups = [LeagueGroup]()
    
    @State private var searchResults = [GameScore]()
    
    @State private var skylarksGamescores = [GameScore]()
    
    @State var showCalendarChooser = false
    @State private var calendar: EKCalendar?
    
    var listData: [GameScore] {
        if showOtherTeams == false && searchText.isEmpty {
            return skylarksGamescores
        } else if showOtherTeams == false && !searchText.isEmpty {
            return searchResults  //should always be the correct filter
        } else if showOtherTeams == true && searchText.isEmpty {
            return gamescores
        } else if showOtherTeams == true && !searchText.isEmpty {
            return searchResults
        }
        //fallback, should never be executed
        return gamescores
    }
    
    @State private var showCalendarDialog = false
    @State private var showEventAlert = false
    @State private var showAlertNoGames = false
    @State private var showAlertNoAccess = false
    @State private var loadingInProgress = false
    @State private var scoresLoaded = false
    
    @AppStorage("showOtherTeams") var showOtherTeams = false
    
    @State private var searchText = ""
    
    @State private var filterDate = Date()
    
    @AppStorage("selectedSeason") var selectedSeason = Calendar(
        identifier: .gregorian
    ).dateComponents([.year], from: .now).year!
    
    //TODO: localise
    @State var selectedTeam = LEAGUEGROUP_ALL
    @State var selectedTeamID: Int = LEAGUEGROUP_ALL.id
    @State var selectedTimeframe = Gameday.current
    
    @State var filterTeams: [LeagueGroup] = [LEAGUEGROUP_ALL]
    
    //---------------------------------------------------------//
    //-----------local funcs-----------------------------------//
    //---------------------------------------------------------//
    
    func loadLeagueGroups() async {
        //reset filter options to default
        filterTeams = [LEAGUEGROUP_ALL]
        
        let leagueGroupsURL = URL(
            string:
                "https://bsm.baseball-softball.de/league_groups.json?filters[seasons][]="
            + "\(selectedSeason)" + "&api_key=" + apiKey)!
        
        do {
            leagueGroups = try await fetchBSMData(
                url: leagueGroupsURL, dataType: [LeagueGroup].self)
        } catch {
            print("Request failed with error: \(error)")
        }
        
        //add teams to filter
        for leagueGroup in leagueGroups {
            filterTeams.append(leagueGroup)
        }
        await loadGamesAndProcess()
    }
    
    func loadGamesAndProcess() async {
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
                url: gameURLSelected!, dataType: [GameScore].self)
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
    
    func refresh() async {
        gamescores = []
        scoresLoaded = false
        await loadGamesAndProcess()
    }
    
    func initialLoad() {
        if gamescores.isEmpty && scoresLoaded == false {
            Task {
                await loadLeagueGroups()
            }
            scoresLoaded = true
        }
    }
    
    func teamChanged() {
        gamescores = []
        scoresLoaded = false
        Task {
            await setTeamID()
            await loadGamesAndProcess()
        }
    }
    
    func timeframeChanged() {
        gamescores = []
        scoresLoaded = false
        Task {
            await loadGamesAndProcess()
        }
    }
    
    func seasonChanged() {
        gamescores = []
        skylarksGamescores = []
        scoresLoaded = false
    }
    
    //---------------------------------------------------------//
    //-------------------calendar funcs------------------------//
    //---------------------------------------------------------//
    
    func checkAccess() async {
        switch EKEventStore.authorizationStatus(for: .event) {
            case .denied, .restricted:
                showAlertNoAccess = true
            case .writeOnly, .fullAccess:
                showCalendarDialog = true
            default:
                let granted = await calendarManager.requestAccess()
                if granted {
                    showCalendarDialog = true
                }
        }
    }
    
    func saveEvents() async {
        let scoresToUse = showOtherTeams ? gamescores : skylarksGamescores
        
        for gamescore in scoresToUse {
            let gameDate = DateTimeUtility.getDatefromBSMString(
                gamescore: gamescore)
            
            await calendarManager.addGameToCalendar(
                gameDate: gameDate, gamescore: gamescore, calendar: calendar)
            showEventAlert = true
        }
    }
    
    var body: some View {
        List {
            Picker(
                selection: $selectedTimeframe,
                label: HStack {
                    Text("Show:")
                },
                content: {
                    ForEach(Gameday.allCases) { gameday in
                        Text(gameday.localizedName)
                            .tag(gameday)
                    }
                    
                }
            )
            .pickerStyle(.segmented)
            .listRowInsets(.init())
            .listRowBackground(Color.clear)
            
            Section(
                header: Text("Selected Season: \(selectedSeason)")
            ) {
                
                //Switch to external games/only our games
                Toggle(
                    String(
                        localized: "Show non-Skylarks Games",
                        comment: "toggle in ScoresView"), isOn: $showOtherTeams
                )
                .tint(.skylarksRed)
                
                //Loading in progress
                if loadingInProgress == true {
                    LoadingView()
                }
                
                //the actual game data
                ForEach(listData, id: \.id) { GameScore in
                    NavigationLink(
                        destination: ScoresDetailView(gamescore: GameScore)
                    ) {
                        ScoresOverView(gamescore: GameScore)
                    }
                    .foregroundColor(.primary)
                    .listRowSeparatorTint(.skylarksRed)
                }
                
                //fallback if there are no games
                if gamescores.isEmpty && loadingInProgress == false {
                    Text(
                        "There are no games scheduled for the chosen time frame."
                    )
                }
                //convoluted conditions, basically just means: we show just our games, there are none, but there are others that have been filtered out
                if skylarksGamescores == [] && !gamescores.isEmpty
                    && showOtherTeams == false && loadingInProgress == false
                {
                    Text(
                        "There are no Skylarks games scheduled for the chosen time frame."
                    )
                }
            }
        }
        .navigationTitle("Scores")
        .animation(.default, value: searchText)
        .animation(.default, value: gamescores)
        .animation(.default, value: showOtherTeams)
        
        .searchable(
            text: $searchText, placement: .automatic, prompt: Text("Filter")
        )  //it doesn't let me change the prompt
        
        .onChange(of: searchText) {
            let searchLC = searchText.lowercased()
            let searchedObjects = showOtherTeams ? gamescores : skylarksGamescores

            searchResults = searchedObjects.filter({ gamescore in
                gamescore.home_team_name.lowercased().contains(
                    searchLC)
                || gamescore.away_team_name.lowercased().contains(
                    searchLC)
                || gamescore.match_id.lowercased().contains(
                    searchLC)
                || gamescore.league.name.lowercased().contains(
                    searchLC)
                || gamescore.home_league_entry.team.clubs.first?.name
                    .localizedCaseInsensitiveContains(searchLC) ?? false
                || gamescore.away_league_entry.team.clubs.first?.name
                    .localizedCaseInsensitiveContains(searchLC) ?? false
                || gamescore.home_league_entry.team.clubs.first?.short_name
                    .localizedCaseInsensitiveContains(searchLC) ?? false
                || gamescore.away_league_entry.team.clubs.first?.short_name
                    .localizedCaseInsensitiveContains(searchLC) ?? false
                || gamescore.home_league_entry.team.clubs.first?.acronym
                    .localizedCaseInsensitiveContains(searchLC) ?? false
                || gamescore.away_league_entry.team.clubs.first?.acronym
                    .localizedCaseInsensitiveContains(searchLC) ?? false
            })
        }
        
        .refreshable {
            await refresh()
        }
        .onAppear {
            initialLoad()
        }
        
        .onChange(of: selectedTeam) {
            teamChanged()
        }
        
        .onChange(of: selectedTimeframe) {
            timeframeChanged()
        }
        
        .onChange(of: selectedSeason) {
            seasonChanged()
        }
        
        .toolbar {
            ToolbarItemGroup(placement: .primaryAction) {
                Button(
                    action: {
                        Task {
                            await checkAccess()
                        }
                    }
                ) {
                    Image(systemName: "calendar.badge.plus")
                }
                
                .sheet(isPresented: $showCalendarDialog) {
                    Form {
                        Section {
                            HStack {
                                Spacer()
                                Button("Select Calendar to save to") {
                                    showCalendarChooser.toggle()
                                }
                                .buttonStyle(.bordered)
                                .padding(.vertical)
                                Spacer()
                            }
                            HStack {
                                Spacer()
                                Text("Selected Calendar:")
                                Text(
                                    calendar?.title
                                    ?? String(localized: "Default"))
                                Spacer()
                            }
                        }
                        Section {
                            HStack {
                                Spacer()
                                
                                Button("Save game data") {
                                    showCalendarDialog = false
                                    Task {
                                        await saveEvents()
                                    }
                                }
                                .buttonStyle(.borderedProminent)
                                
                                Button("Cancel") {
                                    showCalendarDialog = false
                                }
                                Spacer()
                            }
                            .padding(.vertical)
                        }
                    }
                    .presentationDetents([.medium])
                    
                    .sheet(isPresented: $showCalendarChooser) {
                        CalendarChooser(calendar: $calendar)
                    }
                }
                
                .alert("Save to calendar", isPresented: $showEventAlert) {
                    Button("OK") {}
                } message: {
                    Text("All games have been saved.")
                }
                
                .alert("Save to calendar", isPresented: $showAlertNoGames) {
                    Button("OK") {}
                } message: {
                    Text("There is no game data to save.")
                }
                
                .alert("No access to calendar", isPresented: $showAlertNoAccess)
                {
                    Button("OK") {}
                } message: {
                    Text(
                        "You have disabled access to your calendar. To save games please go to your device settings to enable it."
                    )
                }
                
                .alert(
                    "No network connection", isPresented: $showAlertNoNetwork
                ) {
                    Button("OK") {}
                } message: {
                    Text(
                        "No active network connection has been detected. The app needs a connection to download its data."
                    )
                }
                
                Picker("Team", selection: $selectedTeam) {
                    ForEach(filterTeams, id: \.self) { option in
                        HStack {
                            Image(systemName: "person.3")
                            if (option.acronym == "ALL") {
                                Text("\(option.name)")
                            } else {
                                Text("\(option.name) (\(option.acronym))")
                            }
                        }
                        .tag(option)
                    }
                    
                }
            }
        }
    }
}

#Preview {
    ScoresView()
        .environment(CalendarManager())
        .environment(NetworkManager())
}
