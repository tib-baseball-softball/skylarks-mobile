package de.berlinskylarks.shared.database

import androidx.room.TypeConverter
import de.berlinskylarks.shared.data.model.Game
import de.berlinskylarks.shared.data.model.MatchBoxScore
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun gameFromJSONString(value: String): Game {
        return Json.decodeFromString<Game>(value)
    }

    @TypeConverter
    fun gameToJSONString(game: Game): String {
        return Json.encodeToString(game)
    }

    @TypeConverter
    fun boxScoreFromJSONString(value: String): MatchBoxScore {
        return Json.decodeFromString<MatchBoxScore>(value)
    }

    @TypeConverter
    fun boxScoreToJSONString(boxScore: MatchBoxScore): String {
        return Json.encodeToString(boxScore)
    }
}