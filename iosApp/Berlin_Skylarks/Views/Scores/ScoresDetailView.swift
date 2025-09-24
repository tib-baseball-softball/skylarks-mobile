//
//  ScoresDetailView.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 27.12.20.
//

import EventKit
import MapKit
import SwiftUI
import sharedKit

struct ScoresDetailView: View {
    @Environment(ScoresViewModel.self) var vm: ScoresViewModel

    var gamescore: GameScore

    @State private var showingSheet = false
    @State private var showCalendarDialog = false
    @State private var showEventAlert = false
    @State private var showAlertNoGames = false
    @State private var showAlertNoAccess = false

    @State var showCalendarChooser = false
    @State private var calendar: EKCalendar?

    var body: some View {
        ScoresDetailContent(gamescore: gamescore, gameBoxScore: vm.currentBoxScore)
        .navigationTitle("Game Details")
        .task(id: gamescore.id) {
            await vm.loadGameBoxScore(gameID: gamescore.id)
        }

        .toolbar {
            ToolbarItemGroup(placement: .primaryAction) {
                Spacer()

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
                                        ?? String(localized: "Default")
                                )
                                Spacer()
                            }
                        }
                        Section {
                            HStack {
                                Spacer()

                                Button("Save game data") {
                                    showCalendarDialog = false
                                    Task {
                                        await saveEvent()
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
                    Text("Game has been saved to calendar")
                }

                .alert("No access to calendar", isPresented: $showAlertNoAccess)
                {
                    Button("OK") {}
                } message: {
                    Text(
                        "You have disabled access to your calendar. To save games please go to your device settings to enable it."
                    )
                }

                Spacer()

                let shareItem = createShareGameData()
                ShareLink(item: shareItem)
            }
        }
    }

    func createShareGameData() -> String {
        let formatter1 = DateFormatter()
        let formatter2 = DateFormatter()
        formatter1.dateStyle = .short
        formatter2.timeStyle = .short

        var date: String = "date"
        var time: String = "time"

        if let gameDate = gamescore.gameDate {
            date = formatter1.string(from: gameDate)
            time = formatter2.string(from: gameDate)
        }

        //TODO: localise
        let data = """
            Game data - sent from Skylarks app

            League: \(gamescore.league.name)
            Match Number: \(gamescore.match_id)
            Date: \(date)
            Time: \(time)

            Status: \(gamescore.human_state)
            Road Team: \(gamescore.away_team_name) - \(gamescore.away_runs ?? 0)
            Home Team: \(gamescore.home_team_name) - \(gamescore.home_runs ?? 0)

            Field: \(gamescore.field?.name ?? "No data")
            Address: \(gamescore.field?.street ?? ""), \(gamescore.field?.postal_code ?? "") \(gamescore.field?.city ?? "")

            Link to Scoresheet: \(gamescore.scoresheet_url ?? "Not available yet")
            """

        return data
    }

    func checkAccess() async {
        switch EKEventStore.authorizationStatus(for: .event) {
        case .denied, .restricted:
            showAlertNoAccess = true
        case .writeOnly, .fullAccess:
            showCalendarDialog = true
        default:
            let granted = await vm.calendarManager.requestAccess()
            if granted {
                showCalendarDialog = true
            }
        }
    }

    func saveEvent() async {
        let gameDate = DateTimeUtility.getDatefromBSMString(
            gamescore: gamescore
        )
        await vm.calendarManager.addGameToCalendar(
            gameDate: gameDate,
            gamescore: gamescore,
            calendar: calendar
        )
        showEventAlert = true
    }
}

#Preview {
    ScoresDetailView(gamescore: testGame)
}
