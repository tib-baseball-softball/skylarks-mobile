//
//  AppContainer.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 24.09.25.
//

import SwiftUI
import sharedKit

@Observable
class AppContainer {
    var matchAPIClient: MatchAPIClient
    var gameRepository: GameRepository
    
    init() {
        let matchAPIClient = MatchAPIClient(authKey: apiKey)
        let gameRepository = GameRepository(matchAPIClient: matchAPIClient)
        
        self.matchAPIClient = matchAPIClient
        self.gameRepository = gameRepository
    }
}
