//
//  OffensiveTable.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 25.09.25.
//

import Foundation
import SwiftUI
import sharedKit

struct OffensiveTable: View {
    var teamName: String
    var matchStats: OffensiveMatchStats

    var body: some View {
        ScrollView(.horizontal) {
            Grid(alignment: .center, horizontalSpacing: 4, verticalSpacing: 4) {
                // Header
                GridRow {
                    Text("\(teamName) (Batters)")
                        .frame(minWidth: 180, alignment: .leading)
                        .padding(.trailing, 6)
                    Text("AB").frame(minWidth: 48)
                    Text("R").frame(minWidth: 36)
                    Text("H").frame(minWidth: 36)
                    Text("RBI").frame(minWidth: 48)
                    Text("K").frame(minWidth: 36)
                    Text("BB").frame(minWidth: 36)
                    Text("AVG").frame(minWidth: 64)
                    Text("OPS").frame(minWidth: 64)
                }
                .font(.headline)
                
                Divider()
                // Lineup rows
                ForEach(Array(matchStats.lineup.enumerated()), id: \.offset) {
                    _,
                    player in
                    GridRow {
                        HStack(spacing: 6) {
                            let firstInitial =
                                player.person.firstName.first.map { String($0) }
                                ?? ""
                            let name =
                                firstInitial.isEmpty
                                ? player.person.lastName
                                : "\(player.person.lastName), \(firstInitial)."
                            Text(name)
                            let positions = player.humanPositionsShort.joined(
                                separator: "-"
                            ).lowercased()
                            if positions.isEmpty == false {
                                Text(positions).italic()
                            }
                        }
                        .frame(minWidth: 180, alignment: .leading)
                        .padding(.leading, player.starter == true ? 0 : 12)
                        Text(String(player.values.atBats)).frame(minWidth: 48)
                        Text(String(player.values.runs)).frame(minWidth: 36)
                        Text(String(player.values.hits)).frame(minWidth: 36)
                        Text(String(player.values.runsBattedIn)).frame(
                            minWidth: 48
                        )
                        Text(String(player.values.strikeouts)).frame(
                            minWidth: 36
                        )
                        Text(String(player.values.baseOnBalls)).frame(
                            minWidth: 36
                        )
                        Text(player.values.battingAverage ?? "").frame(
                            minWidth: 64
                        )
                        Text(player.values.onBasePlusSlugging ?? "").frame(
                            minWidth: 64
                        )
                    }
                }
                Divider()
                // Totals row
                GridRow {
                    Text("")
                        .frame(minWidth: 180, alignment: .leading)
                    Text(String(matchStats.sum.atBats)).frame(minWidth: 48)
                    Text(String(matchStats.sum.runs)).frame(minWidth: 36)
                    Text(String(matchStats.sum.hits)).frame(minWidth: 36)
                    Text(String(matchStats.sum.runsBattedIn)).frame(
                        minWidth: 48
                    )
                    Text(String(matchStats.sum.strikeouts)).frame(minWidth: 36)
                    Text(String(matchStats.sum.baseOnBalls)).frame(minWidth: 36)
                    Text("").frame(minWidth: 64)
                    Text("").frame(minWidth: 64)
                }
            }
            .padding(.vertical, 4)
        }
    }
}
