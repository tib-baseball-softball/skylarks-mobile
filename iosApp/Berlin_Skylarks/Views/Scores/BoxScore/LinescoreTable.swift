//
// Created by David Battefeld on 24.09.25.
//

import Foundation
import SwiftUI
import sharedKit

struct LinescoreTable: View {
    var linescore: Linescore

    var body: some View {
        let innings: [Int] = Array(
            1...Int(linescore.playedInnings)
        )

        ScrollView(.horizontal) {
            Grid {
                GridRow {
                    Text("")
                    ForEach(innings, id: \.self) { inning in
                        Text(String(inning))
                    }

                    Text("R")
                        .padding(.leading, 3)
                    Text("H")
                    Text("E")
                }
                .font(.headline)
                .padding(1)
                
                Divider()

                GridRow {
                    Text(linescore.away.leagueEntry.team.name)
                        .padding(.trailing, 5)

                    ForEach(innings, id: \.self) { inning in
                        let awayInnings = linescore.away
                            .innings

                        let index = inning - 1
                        let awayInningScore =
                            awayInnings.indices.contains(index)
                            ? Int(truncating: awayInnings[index]) : 0

                        Text(String(awayInningScore))
                    }

                    Text(String(linescore.away.runs))
                        .bold()
                        .padding(.leading, 3)
                    Text(String(linescore.away.hits))
                    Text(String(linescore.away.errors))
                }
                .padding(1)

                GridRow {
                    Text(linescore.home.leagueEntry.team.name)
                        .padding(.trailing, 5)

                    ForEach(innings, id: \.self) { inning in
                        let homeInnings = linescore.home
                            .innings

                        let index = inning - 1
                        let homeInningScore =
                            homeInnings.indices.contains(index)
                            ? Int(truncating: homeInnings[index]) : 0

                        Text(String(homeInningScore))
                    }

                    Text(String(linescore.home.runs))
                        .bold()
                        .padding(.leading, 3)
                    Text(String(linescore.home.hits))
                    Text(String(linescore.home.errors))
                }
                .padding(1)
            }
        }
    }
}

extension LinescoreTable {
    static var previewData =
        Linescore(
            matchId: "32355-3425",
            playedInnings: 9,
            away: LinescoreEntry(
                leagueEntry: LeagueEntry(
                    team: sharedKit.BSMTeam(
                        id: 0,
                        name: "Test Team",
                        short_name: "TES",
                        league_entries: []
                    ),
                    league: sharedKit.League(
                        id: 0,
                        season: 2025,
                        name: "Testliga",
                        acronym: "TESBB",
                        sport: "baseball",
                        classification: "Kreisliga",
                        age_group: "adults"
                    )
                ),
                innings: [1, 0, 4, 0, 0, 3, 0, 1, 0],
                runs: 11,
                hits: 5,
                errors: 6
            ),
            home: LinescoreEntry(
                leagueEntry: LeagueEntry(
                    team: sharedKit.BSMTeam(
                        id: 0,
                        name: "Test Team",
                        short_name: "TES",
                        league_entries: []
                    ),
                    league: sharedKit.League(
                        id: 0,
                        season: 2025,
                        name: "Testliga",
                        acronym: "TESBB",
                        sport: "baseball",
                        classification: "Kreisliga",
                        age_group: "adults"
                    )
                ),
                innings: [1, 2, 4, 10, 7, 8, 8, 8, 9],
                runs: 11,
                hits: 5,
                errors: 6
            )
        )
}

#Preview {
    List {
        Section(header: Text("Linescore")) {
            LinescoreTable(linescore: LinescoreTable.previewData)
        }
    }

}
