//
//  ScoresView.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 26.12.20.
//

import EventKit
import SwiftUI

struct ScoresView: View {
    @Environment(CalendarManager.self) var calendarManager: CalendarManager
    @Environment(NetworkManager.self) var networkManager: NetworkManager
    @Environment(AppContainer.self) var appContainer: AppContainer
    @Environment(ScoresViewModel.self) var vm: ScoresViewModel

    @State var showCalendarChooser = false
    @State private var calendar: EKCalendar?

    var listData: [GameScore] {
        if showOtherTeams == false && searchText.isEmpty {
            return vm.skylarksGamescores
        } else if showOtherTeams == false && !searchText.isEmpty {
            return vm.searchResults  //should always be the correct filter
        } else if showOtherTeams == true && searchText.isEmpty {
            return vm.gamescores
        } else if showOtherTeams == true && !searchText.isEmpty {
            return vm.searchResults
        }
        //fallback, should never be executed
        return vm.gamescores
    }

    @AppStorage("showOtherTeams") var showOtherTeams = false
    @AppStorage("selectedSeason") var selectedSeason = Calendar(
        identifier: .gregorian
    ).dateComponents([.year], from: .now).year!

    @State private var searchText = ""
    @State private var filterDate = Date()

    //---------------------------------------------------------//
    //-------------------calendar funcs------------------------//
    //---------------------------------------------------------//

    func checkAccess() async {
        switch EKEventStore.authorizationStatus(for: .event) {
        case .denied, .restricted:
            vm.showAlertNoAccess = true
        case .writeOnly, .fullAccess:
            vm.showCalendarDialog = true
        default:
            let granted = await calendarManager.requestAccess()
            if granted {
                vm.showCalendarDialog = true
            }
        }
    }

    func saveEvents() async {
        let scoresToUse = showOtherTeams ? vm.gamescores : vm.skylarksGamescores

        for gamescore in scoresToUse {
            let gameDate = DateTimeUtility.getDatefromBSMString(
                gamescore: gamescore
            )

            await calendarManager.addGameToCalendar(
                gameDate: gameDate,
                gamescore: gamescore,
                calendar: calendar
            )
            vm.showEventAlert = true
        }
    }

    var body: some View {
        @Bindable var vm = vm
        List {
            Picker(
                selection: $vm.selectedTimeframe,
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
                        comment: "toggle in ScoresView"
                    ),
                    isOn: $showOtherTeams
                )
                .tint(.skylarksRed)

                if vm.loadingInProgress == true {
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
                if vm.gamescores.isEmpty && vm.loadingInProgress == false {
                    Text(
                        "There are no games scheduled for the chosen time frame."
                    )
                }
                //convoluted conditions, basically just means: we show just our games, there are none, but there are others that have been filtered out
                if vm.skylarksGamescores == [] && !vm.gamescores.isEmpty
                    && showOtherTeams == false && vm.loadingInProgress == false
                {
                    Text(
                        "There are no Skylarks games scheduled for the chosen time frame."
                    )
                }
            }
        }
        .navigationTitle("Scores")
        .animation(.default, value: searchText)
        .animation(.default, value: vm.gamescores)
        .animation(.default, value: showOtherTeams)

        .searchable(
            text: $searchText,
            placement: .automatic,
            prompt: Text("Filter")
        )  //it doesn't let me change the prompt

        .onChange(of: searchText) {
            let searchLC = searchText.lowercased()
            let searchedObjects =
            showOtherTeams ? vm.gamescores : vm.skylarksGamescores

            vm.searchResults = searchedObjects.filter({ gamescore in
                gamescore.home_team_name.lowercased().contains(
                    searchLC
                )
                    || gamescore.away_team_name.lowercased().contains(
                        searchLC
                    )
                    || gamescore.match_id.lowercased().contains(
                        searchLC
                    )
                    || gamescore.league.name.lowercased().contains(
                        searchLC
                    )
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
            await vm.refresh(selectedSeason: selectedSeason)
        }
        .onAppear {
            vm.initialLoad(selectedSeason: selectedSeason)
        }

        .onChange(of: vm.selectedTeam) {
            vm.teamChanged(selectedSeason: selectedSeason)
        }

        .onChange(of: vm.selectedTimeframe) {
            vm.timeframeChanged(selectedSeason: selectedSeason)
        }

        .onChange(of: selectedSeason) {
            vm.seasonChanged()
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

                .sheet(isPresented: $vm.showCalendarDialog) {
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
                                        ?? String(localized: "Default")
                                )
                                Spacer()
                            }
                        }
                        Section {
                            HStack {
                                Spacer()

                                Button("Save game data") {
                                    vm.showCalendarDialog = false
                                    Task {
                                        await saveEvents()
                                    }
                                }
                                .buttonStyle(.borderedProminent)

                                Button("Cancel") {
                                    vm.showCalendarDialog = false
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

                .alert("Save to calendar", isPresented: $vm.showEventAlert) {
                    Button("OK") {}
                } message: {
                    Text("All games have been saved.")
                }

                .alert("Save to calendar", isPresented: $vm.showAlertNoGames) {
                    Button("OK") {}
                } message: {
                    Text("There is no game data to save.")
                }

                .alert("No access to calendar", isPresented: $vm.showAlertNoAccess)
                {
                    Button("OK") {}
                } message: {
                    Text(
                        "You have disabled access to your calendar. To save games please go to your device settings to enable it."
                    )
                }

                .alert(
                    "No network connection",
                    isPresented: $vm.showAlertNoNetwork
                ) {
                    Button("OK") {}
                } message: {
                    Text(
                        "No active network connection has been detected. The app needs a connection to download its data."
                    )
                }

                Picker("Team", selection: $vm.selectedTeam) {
                    ForEach(vm.filterTeams, id: \.self) { option in
                        HStack {
                            Image(systemName: "person.3")
                            if option.acronym == "ALL" {
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
