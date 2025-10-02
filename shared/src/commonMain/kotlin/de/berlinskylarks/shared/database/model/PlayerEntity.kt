package de.berlinskylarks.shared.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.tib.Media
import de.berlinskylarks.shared.data.model.tib.Player

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey
    var id: Long,
    val bsmID: Long,
    val fullName: String,
    val firstName: String,
    val lastName: String,
    val birthday: Long,
    val admission: String,
    val number: String,
    val throwing: String,
    val batting: String,
    val coach: String,
    val slug: String,
    val teamName: String?,
    @Embedded(prefix = "media_")
    val media: MediaEntity?,
    val positions: String, // concatenated
    val teams: String, // concatenated
) {
    fun toPlayer(): Player {
        return Player(
            id = id,
            bsmID = bsmID,
            fullName = fullName,
            firstName = firstName,
            lastName = lastName,
            birthday = birthday,
            admission = admission,
            number = number,
            throwing = throwing,
            batting = batting,
            coach = coach,
            slug = slug,
            teamName = teamName,
            media = if (media != null) listOf(
                Media(
                    id = media.id,
                    url = media.url,
                    alt = media.alt,
                    title = media.title,
                    caption = media.caption,
                    copyright = media.copyright
                )
            ) else emptyList(),
            positions = positions.split(","),
            teams = emptyList() // TODO
        )
    }
}
