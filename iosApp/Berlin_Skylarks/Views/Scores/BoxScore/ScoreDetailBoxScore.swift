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
                Section(header: Text("Batting")) {

                }
                Section(header: Text("Pitching")) {

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
