//
//  AdditionalStatsSection.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 25.09.25.
//

import Foundation
import SwiftUI
import sharedKit

struct AdditionalStatsSection: View {
    var stats: AdditionalMatchStats

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            // Batting
            Text("Batting").font(.subheadline).bold()
            if !stats.batting.doubles.isEmpty {
                LabeledStatRow(label: "2B:", entries: stats.batting.doubles)
            }
            if !stats.batting.triples.isEmpty {
                LabeledStatRow(label: "3B:", entries: stats.batting.triples)
            }
            if !stats.batting.homeruns.isEmpty {
                LabeledStatRow(label: "HR:", entries: stats.batting.homeruns)
            }
            if !stats.batting.sacrificeHits.isEmpty {
                LabeledStatRow(
                    label: "SH:",
                    entries: stats.batting.sacrificeHits
                )
            }
            if !stats.batting.sacrificeFlys.isEmpty {
                LabeledStatRow(
                    label: "SF:",
                    entries: stats.batting.sacrificeFlys
                )
            }

            // Baserunning
            Text("Baserunning").font(.subheadline).bold().padding(.top, 6)
            if !stats.baserunning.stolenBases.isEmpty {
                LabeledStatRow(
                    label: "SB:",
                    entries: stats.baserunning.stolenBases
                )
            }
            if !stats.baserunning.caughtStealings.isEmpty {
                LabeledStatRow(
                    label: "CS:",
                    entries: stats.baserunning.caughtStealings
                )
            }

            // Fielding
            Text("Fielding").font(.subheadline).bold().padding(.top, 6)
            if !stats.fielding.errors.isEmpty {
                LabeledStatRow(label: "E:", entries: stats.fielding.errors)
            }
            if !stats.fielding.passedBalls.isEmpty {
                LabeledStatRow(
                    label: "PB:",
                    entries: stats.fielding.passedBalls
                )
            }
            if !stats.fielding.doublePlays.isEmpty {
                LabeledStatRow(
                    label: "DP:",
                    entries: stats.fielding.doublePlays
                )
            }
            if !stats.fielding.triplePlays.isEmpty {
                LabeledStatRow(
                    label: "TP:",
                    entries: stats.fielding.triplePlays
                )
            }
        }
    }

    internal struct LabeledStatRow: View {
        var label: String
        var entries: [AdditionalStat]

        var body: some View {
            HStack(alignment: .firstTextBaseline, spacing: 4) {
                Text(label).bold()
                Text(formatAdditionalEntries(entries: entries))
            }
        }
        
        private func formatAdditionalEntries(entries: [AdditionalStat]) -> String {
            entries.map { entry in
                let firstInitial =
                entry.person.firstName.first.map { String($0) } ?? ""
                let countSuffix = entry.count > 1 ? " (\(entry.count))" : ""
                return "\(entry.person.lastName) \(firstInitial).\(countSuffix)"
            }.joined(separator: ", ")
        }
    }
}
