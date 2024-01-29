package de.davidbattefeld.berlinskylarks.testdata

import model.Game
import model.League
import model.LeagueTable

val testEntry = Game.LeagueEntry(
    team = Game.Team(name = "Test Team")
)

val testLeague = League(
    id = 9999,
    name = "Kreisliga",
    season = 2099,
    classification = "Kreisliga",
    sport = "Baseball",
    acronym = "KRL",
    age_group = "Erwachsene"
)

val testGame = Game(
    id = 9999,
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

val testRow = LeagueTable.Row(
    rank = "1.",
    team_name = "Puppeteers",
    short_team_name = "PUP",
    match_count = 7,
    wins_count = 5.0,
    losses_count = 2.0,
    quota = ".500",
    games_behind = "2",
    streak = "W2"
)

val testRow2 = LeagueTable.Row(
    rank = "1.",
    team_name = "Skylarks",
    short_team_name = "SKY",
    match_count = 7,
    wins_count = 5.0,
    losses_count = 2.0,
    quota = ".500",
    games_behind = "2",
    streak = "W2"
)

val testTable = LeagueTable(
    league_id = 9999,
    league_name = "Kreisliga Baseball",
    season = 1970,
    rows = listOf(testRow, testRow, testRow2)
)