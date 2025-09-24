//
//  ScoresDetailContent.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 24.09.25.
//

import SwiftUI
import sharedKit

struct ScoresDetailContent: View {
    var gamescore: GameScore
    var gameBoxScore: MatchBoxScore?

    @State var selectedTab: TabState = .main

    var body: some View {
        VStack(alignment: .leading) {
            Picker(
                selection: $selectedTab,
                label:
                    Text("Display")
            ) {
                ForEach(TabState.allCases) { state in
                    Text(state.localizedName)
                        .tag(state)
                }
            }
            .pickerStyle(.segmented)
            .glassEffect()
            .padding()
            
            switch selectedTab {
                case .main:
                    ScoreDetailMainTab(gamescore: gamescore)
                case .boxScore:
                    ScoreDetailBoxScore()
                case .report:
                    Text("game report")
            }
        }
    }
}

extension ScoresDetailContent {
    internal enum TabState: String, Identifiable, CaseIterable {
        case main, boxScore, report

        var displayName: String { rawValue.capitalized }
        var localizedName: LocalizedStringKey {
            LocalizedStringKey(rawValue.capitalized)
        }
        var id: String { self.rawValue }
    }
}

#Preview {
    ScoresDetailContent(gamescore: testGame)
}
