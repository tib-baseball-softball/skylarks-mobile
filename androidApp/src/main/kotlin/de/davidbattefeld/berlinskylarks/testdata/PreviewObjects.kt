package de.davidbattefeld.berlinskylarks.testdata

import de.berlinskylarks.shared.data.model.BSMTeam
import de.berlinskylarks.shared.data.model.Game
import de.berlinskylarks.shared.data.model.League
import de.berlinskylarks.shared.data.model.LeagueEntry
import de.berlinskylarks.shared.data.model.LeagueGroup
import de.berlinskylarks.shared.data.model.LeagueTable
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID

val testLeague = League(
    id = BOGUS_ID,
    name = "Kreisliga",
    season = 2099,
    classification = "Kreisliga",
    sport = "Baseball",
    acronym = "KRL",
    age_group = "Erwachsene"
)

val testEntry = LeagueEntry(
    team = BSMTeam(
        name = "Test Team",
        id = 0,
        short_name = "TES",
        league_entries = listOf()
    ),
    league = testLeague
)

val testLeagueGroup = LeagueGroup(
    id = BOGUS_ID,
    name = "All Leagues",
    acronym = "KBD",
    season = 2099,
    league = testLeague
)

val testGame = Game(
    id = BOGUS_ID,
    matchID = "12345",
    plannedInnings = 7,
    leagueID = 5555,
    awayRuns = 5,
    homeRuns = 7,
    awayTeamName = "Road Team",
    homeTeamName = "Home Team",
    humanState = "in limbo",
    time = "2022-45-67 12:00",
    homeLeagueEntry = testEntry,
    awayLeagueEntry = testEntry,
    scoresheetURL = null,
    league = testLeague,
    field = null,
    scorerAssignments = listOf(),
    umpireAssignments = listOf(),
    state = "played",
    season = 2025,
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
    league_id = BOGUS_ID,
    league_name = "Kreisliga Baseball",
    season = 1970,
    rows = listOf(testRow, testRow, testRow2)
)