package de.berlinskylarks.shared.database

import androidx.room.TypeConverter
import de.berlinskylarks.shared.data.model.Game
import de.berlinskylarks.shared.data.model.LeagueTable
import de.berlinskylarks.shared.data.model.MatchBoxScore
import kotlinx.serialization.json.Json
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class Converters {
    val jsonBuilder = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        isLenient = true
    }

    @TypeConverter
    fun gameFromJSONString(value: String): Game {
        return jsonBuilder.decodeFromString<Game>(value)
    }

    @TypeConverter
    fun gameToJSONString(game: Game): String {
        return jsonBuilder.encodeToString(game)
    }

    @TypeConverter
    fun boxScoreFromJSONString(value: String): MatchBoxScore {
        return jsonBuilder.decodeFromString<MatchBoxScore>(value)
    }

    @TypeConverter
    fun boxScoreToJSONString(boxScore: MatchBoxScore): String {
        return jsonBuilder.encodeToString(boxScore)
    }

    @TypeConverter
    fun tableFromJSONString(value: String): LeagueTable {
        return jsonBuilder.decodeFromString<LeagueTable>(value)
    }

    @TypeConverter
    fun tableToJSONString(table: LeagueTable): String {
        return jsonBuilder.encodeToString(table)
    }
}