//
//  ScoresViewModel.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 24.09.25.
//

import Foundation
import sharedKit

@Observable
class ScoresViewModel {
    private let gameRepository: GameRepository
    
    init(gameRepository: GameRepository) {
        self.gameRepository = gameRepository
    }
}
