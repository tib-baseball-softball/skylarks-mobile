//
//  MainTabView.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 16.06.22.
//

import SwiftUI

struct MainTabView: View {
    @Environment(AppContainer.self) var appContainer: AppContainer

    var body: some View {
        TabView {
            Tab("HomeTab", systemImage: "star.square.fill") {
                NavigationStack {
                    UserHomeView()
                }
            }

            //since News is non-functional right now, let's rather have the settings back in the tab bar
            //                NavigationView {
            //                    NewsView()
            //                }
            //                    .tabItem {
            //                        Image(systemName: "newspaper.fill")
            //                        Text("News")
            //                    }

            Tab("Scores", systemImage: "42.square.fill") {
                NavigationStack {
                    ScoresView()
                }
            }

            Tab("Standings", systemImage: "tablecells.fill") {
                NavigationStack {
                    StandingsView()
                }
            }

            Tab("Club", systemImage: "shield.fill") {
                NavigationStack {
                    ClubView()
                }
            }

            Tab("Settings", systemImage: "gearshape.fill") {
                NavigationStack {
                    SettingsListView()
                }
            }
        }
        .tabViewStyle(.sidebarAdaptable)
    }
}

#Preview {
    MainTabView()
        .environment(AppContainer())
}
