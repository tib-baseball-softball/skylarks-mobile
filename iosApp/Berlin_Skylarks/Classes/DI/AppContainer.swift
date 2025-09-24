//
//  AppContainer.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 24.09.25.
//

import SwiftUI
import sharedKit

@MainActor
@Observable
class AppContainer {
    var matchAPIClient: MatchAPIClient
    var gameRepository: GameRepository
    var networkManager: NetworkManager
    var calendarManager: CalendarManager
    
    init() {
        let matchAPIClient = MatchAPIClient(authKey: apiKey)
        let gameRepository = GameRepository(matchAPIClient: matchAPIClient)
        let networkManager = NetworkManager()
        let calendarManager = CalendarManager()
        
        self.matchAPIClient = matchAPIClient
        self.gameRepository = gameRepository
        self.networkManager = networkManager
        self.calendarManager = calendarManager
    }
}
