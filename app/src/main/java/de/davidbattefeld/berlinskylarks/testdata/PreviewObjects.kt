package de.davidbattefeld.berlinskylarks.testdata

import model.GameScore

val testEntry = GameScore.LeagueEntry(
    team = GameScore.Team(name = "Test Team")
)

val testGame = GameScore(
    id = 0,
    match_id = "12345",
    league_id = 5555,
    away_runs = 5,
    home_runs = 7,
    away_team_name = "Road Team",
    home_team_name = "Home Team",
    human_state = "in limbo",
    time = "2022-45-67 12:00",
    home_league_entry = testEntry,
    away_league_entry = testEntry,
    scoresheet_url = null
)

