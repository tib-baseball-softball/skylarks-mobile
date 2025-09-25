//
//  PitchingTable.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 25.09.25.
//

import Foundation
import SwiftUI
import sharedKit

struct PitchingTable: View {
    var teamName: String
    var matchStats: PitchingMatchStats

    var body: some View {
        ScrollView(.horizontal) {
            Grid(alignment: .center, horizontalSpacing: 4, verticalSpacing: 4) {
                // Header
                GridRow {
                    Text("\(teamName) (Pitchers)")
                        .frame(minWidth: 180, alignment: .leading)
                        .padding(.trailing, 6)
                    Text("IP").frame(minWidth: 56)
                    Text("BF").frame(minWidth: 56)
                    Text("AB").frame(minWidth: 56)
                    Text("H").frame(minWidth: 40)
                    Text("R").frame(minWidth: 40)
                    Text("ER").frame(minWidth: 56)
                    Text("K").frame(minWidth: 40)
                    Text("BB").frame(minWidth: 40)
                    Text("ERA").frame(minWidth: 64)
                }
                .font(.headline)

                Divider()
                // Lineup rows
                ForEach(Array(matchStats.lineup.enumerated()), id: \.offset) {
                    _,
                    player in
                    GridRow {
                        let firstInitial =
                            player.person.firstName.first.map { String($0) }
                            ?? ""
                        let name =
                            firstInitial.isEmpty
                            ? player.person.lastName
                            : "\(player.person.lastName), \(firstInitial)."
                        let wls = player.values.winLossSave ?? ""
                        Text(wls.isEmpty ? name : name + " (\(wls))")
                            .frame(minWidth: 180, alignment: .leading)
                        Text(player.values.inningsPitched).frame(minWidth: 56)
                        Text(String(player.values.battersFaced)).frame(
                            minWidth: 56
                        )
                        Text(String(player.values.atBats)).frame(minWidth: 56)
                        Text(String(player.values.hits)).frame(minWidth: 40)
                        Text(String(player.values.runs)).frame(minWidth: 40)
                        Text(String(player.values.earnedRuns)).frame(
                            minWidth: 56
                        )
                        Text(String(player.values.strikeouts)).frame(
                            minWidth: 40
                        )
                        Text(String(player.values.baseOnBallsAllowed)).frame(
                            minWidth: 40
                        )
                        Text(player.values.earnedRunsAverage ?? "").frame(
                            minWidth: 64
                        )
                    }
                }
                Divider()
                // Totals row
                GridRow {
                    Text("")
                        .frame(minWidth: 180, alignment: .leading)
                    Text(matchStats.sum.inningsPitched).frame(minWidth: 56)
                    Text(String(matchStats.sum.battersFaced)).frame(
                        minWidth: 56
                    )
                    Text(String(matchStats.sum.atBats)).frame(minWidth: 56)
                    Text(String(matchStats.sum.hits)).frame(minWidth: 40)
                    Text(String(matchStats.sum.runs)).frame(minWidth: 40)
                    Text(String(matchStats.sum.earnedRuns)).frame(minWidth: 56)
                    Text(String(matchStats.sum.strikeouts)).frame(minWidth: 40)
                    Text(String(matchStats.sum.baseOnBallsAllowed)).frame(
                        minWidth: 40
                    )
                    Text("").frame(minWidth: 64)
                }
            }
            .padding(.vertical, 4)
        }
    }
}
