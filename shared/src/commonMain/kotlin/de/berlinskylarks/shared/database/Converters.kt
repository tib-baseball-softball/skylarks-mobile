package de.berlinskylarks.shared.database

import androidx.room.TypeConverter
import de.berlinskylarks.appconfigclient.models.ConfigurationDTOApiURLS
import de.berlinskylarks.appconfigclient.models.FlagWithStatusDTO
import de.berlinskylarks.shared.data.model.Game
import de.berlinskylarks.shared.data.model.HomeDataset
import de.berlinskylarks.shared.data.model.LeagueEntry
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

    @TypeConverter
    fun leagueEntryListFromJSONString(value: String): List<LeagueEntry> {
        return jsonBuilder.decodeFromString<List<LeagueEntry>>(value)
    }

    @TypeConverter
    fun leagueEntryListToJSONString(leagueEntries: List<LeagueEntry>): String {
        return jsonBuilder.encodeToString(leagueEntries)
    }

    @TypeConverter
    fun homeDatasetFromJSOnString(value: String): HomeDataset {
        return jsonBuilder.decodeFromString<HomeDataset>(value)
    }

    @TypeConverter
    fun homeDatasetToJSONString(dataset: HomeDataset): String {
        return jsonBuilder.encodeToString(dataset)
    }

    @TypeConverter
    fun configAPIURLsToJSONString(urls: ConfigurationDTOApiURLS): String {
        return jsonBuilder.encodeToString(urls)
    }

    @TypeConverter
    fun configAPIURLsFromJSONString(value: String): ConfigurationDTOApiURLS {
        return jsonBuilder.decodeFromString<ConfigurationDTOApiURLS>(value)
    }

    @TypeConverter
    fun flagRelationsToJSONString(rels: Map<String, FlagWithStatusDTO>): String {
        return jsonBuilder.encodeToString(rels)
    }

    @TypeConverter
    fun flagRelationsFromJSONString(value: String): Map<String, FlagWithStatusDTO> {
        return jsonBuilder.decodeFromString<Map<String, FlagWithStatusDTO>>(value)
    }
}