//
//  ScoreDetailMainTab.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 24.09.25.
//

import SwiftUI
import sharedKit

struct ScoreDetailMainTab: View {
    var gamescore: GameScore

    var body: some View {
        let logos = TeamImageData.fetchCorrectLogos(gamescore: gamescore)

        List {
            Section(header: Text("Main info")) {
                ScoreMainInfo(gamescore: gamescore)
            }
            .textSelection(.enabled)
            Section(header: Text("Score")) {
                VStack {
                    GameResultIndicator(gamescore: gamescore)
                        .font(.title)

                    HStack {
                        VStack {
                            Text(
                                "Road",
                                comment: "reference to the road team"
                            )
                            .bold()
                            logos.road
                                .resizable()
                                .scaledToFit()
                                .frame(
                                    width: 50,
                                    height: 50,
                                    alignment: .center
                                )
                            Text(gamescore.away_team_name)
                                .lineLimit(nil)
                        }
                        Spacer()
                        VStack {
                            Text(
                                "Home",
                                comment: "Reference to the home team"
                            )
                            .bold()
                            logos.home
                                .resizable()
                                .scaledToFit()
                                .frame(
                                    width: 50,
                                    height: 50,
                                    alignment: .center
                                )
                            Text(gamescore.home_team_name)
                                .lineLimit(nil)
                        }.frame(width: teamNameFrame)
                    }
                    .padding(ScoresItemPadding)
                    Divider()
                    HStack {
                        if let awayScore = gamescore.away_runs,
                            let homeScore = gamescore.home_runs
                        {
                            Text(String(awayScore))
                                .font(.largeTitle)
                                .bold()
                                .padding(ScoresNumberPadding)
                                .foregroundColor(
                                    awayScore < homeScore
                                        ? Color.secondary : Color.primary
                                )
                        }
                        Spacer()
                        if let awayScore = gamescore.away_runs,
                            let homeScore = gamescore.home_runs
                        {
                            Text(String(homeScore))
                                .font(.largeTitle)
                                .bold()
                                .padding(ScoresNumberPadding)
                                .foregroundColor(
                                    awayScore > homeScore
                                        ? Color.secondary : Color.primary
                                )
                        }
                    }
                    .padding(ScoresItemPadding)
                }
            }
            Section(header: Text("Location")) {
                BallparkLocation(gamescore: gamescore)
            }
            .textSelection(.enabled)
            Section(header: Text("Status")) {
                ScoresStatusSection(gamescore: gamescore)
            }
            Section(header: Text("Game officials")) {
                UmpireAssignments(gamescore: gamescore)
                ScorerAssignments(gamescore: gamescore)
            }
            .textSelection(.enabled)
        }
    }
}

#Preview {
    ScoreDetailMainTab(gamescore: testGame)
}
