package de.davidbattefeld.berlinskylarks.testdata

import model.GameScore
import model.League
import model.LeagueTable

val testEntry = GameScore.LeagueEntry(
    team = GameScore.Team(name = "Test Team")
)

val testLeague = League(
    id = 420,
    name = "Kreisliga",
    season = 2099,
    classification = "Kreisliga",
    sport = "Baseball",
    acronym = "KRL",
    age_group = "Erwachsene"
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
    scoresheet_url = null,
    league = testLeague,
    field = null,
    scorer_assignments = listOf(),
    umpire_assignments = listOf(),
)

val testTable = LeagueTable(
    league_id = 1234,
    league_name = "Kreisliga Baseball",
    season = 1970,
    rows = emptyList()
)