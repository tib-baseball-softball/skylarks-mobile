//
//  Berlin_SkylarksApp.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 23.12.20.
//

import SwiftUI
import sharedKit

@main
struct Berlin_SkylarksApp: App {
    @State var calendarManager = CalendarManager()
    @State var networkManager = NetworkManager()
    @State var appContainer = AppContainer()
    

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(calendarManager)
                .environment(networkManager)
                .environment(appContainer)
                .environment(
                    ScoresViewModel(
                        gameRepository: appContainer.gameRepository,
                        networkManager: appContainer.networkManager,
                        calendarManager: appContainer.calendarManager
                    )
                )
        }
    }
}

//this makes views immediately appear (instead of being hidden behind back buttons)
#if !os(macOS)
extension UISplitViewController {
    open override func viewDidLoad() {
        super.viewDidLoad()
        self.show(.primary)
    }
}
#endif
