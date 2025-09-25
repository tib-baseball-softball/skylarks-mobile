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

    var body: some View {
        if let boxscore = vm.currentBoxScore {
            List {
                Section(header: Text("Linescore")) {
                    LinescoreTable(linescore: boxscore.linescore)
                }
                Section(header: Text("Offensive")) {
                    OffensiveTable(teamName: boxscore.linescore.away.leagueEntry.team.name, matchStats: boxscore.offensiveAway)
                    AdditionalStatsSection(stats: boxscore.additionalAway)
                    Divider()
                    OffensiveTable(teamName: boxscore.linescore.home.leagueEntry.team.name, matchStats: boxscore.offensiveHome)
                    AdditionalStatsSection(stats: boxscore.additionalHome)
                }
                Section(header: Text("Pitching")) {
                    PitchingTable(teamName: boxscore.linescore.away.leagueEntry.team.name, matchStats: boxscore.pitchingAway)
                    PitchingTable(teamName: boxscore.linescore.home.leagueEntry.team.name, matchStats: boxscore.pitchingHome)
                }
            }
            .textSelection(.enabled)
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
