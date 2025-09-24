//
//  ScoreDetailBoxScore.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 24.09.25.
//

import SwiftUI
import sharedKit

struct ScoreDetailBoxScore: View {
    @Environment(ScoresViewModel.self) var vm: ScoresViewModel

    internal struct LinescoreRow: Identifiable {
        let id = UUID()
        let team: String
        let runsByInning: [Int]
        let hits: Int
        let errors: Int

        var totalRuns: Int { runsByInning.reduce(0, +) }
    }

    var body: some View {
        if let boxscore = vm.currentBoxScore {
            VStack(alignment: .leading) {
                Text("Linescore")
                // Precompute values to help the type-checker and readability
                let away: LinescoreEntry = boxscore.linescore.away
                let home: LinescoreEntry = boxscore.linescore.home

                let innings: [Int] = Array(
                    1...Int(boxscore.linescore.playedInnings)
                )

                let awayRow = LinescoreRow(
                    team: away.leagueEntry.team.name,
                    runsByInning: away.innings.map { Int(truncating: $0) },
                    hits: Int(away.hits),
                    errors: Int(away.errors)
                )
                let homeRow = LinescoreRow(
                    team: home.leagueEntry.team.name,
                    runsByInning: home.innings.map { Int(truncating: $0) },
                    hits: Int(home.hits),
                    errors: Int(home.errors)
                )

                let linescores: [LinescoreRow] = [awayRow, homeRow]

                Table(linescores) {
                    TableColumn("Team") { row in
                        Text(row.team)
                    }

                    TableColumn("Innings") { row in
                        HStack(spacing: 8) {
                            ForEach(innings, id: \.self) { inning in
                                let index = inning - 1
                                let value =
                                    row.runsByInning.indices.contains(index)
                                    ? row.runsByInning[index] : 0
                                Text("\(value)")
                                    .frame(minWidth: 16, alignment: .trailing)
                            }
                        }
                        .monospacedDigit()
                    }

                    TableColumn("R") { row in
                        Text(String(row.totalRuns))
                            .bold()
                    }

                    TableColumn("H") { row in
                        Text(String(row.hits))
                    }

                    TableColumn("E") { row in
                        Text(String(row.errors))
                    }
                }
            }
        } else {
            ContentUnavailableView(
                "No Box Score available.",
                systemImage: "chart.line.text.clipboard"
            )
        }
    }
}

//#Preview {
//    ScoreDetailBoxScore(gameBoxScore: responseObj)
//}
