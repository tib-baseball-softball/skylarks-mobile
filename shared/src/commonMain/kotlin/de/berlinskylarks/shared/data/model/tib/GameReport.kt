package de.berlinskylarks.shared.data.model.tib

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameReport(
    val uid: Int,
    val author: String,
    @SerialName("game_id") val gameId: String,
    val league: TibLeague,
    @SerialName("game_toggle") val gameToggle: GameToggle,
    @SerialName("teaser_text") val teaserText: String,
    val introduction: String,
    @SerialName("introduction_plain") val introductionPlain: String,
    @SerialName("report_first") val reportFirst: String,
    @SerialName("report_first_plain") val reportFirstPlain: String,
    @SerialName("report_second") val reportSecond: String? = null,
    @SerialName("report_second_plain") val reportSecondPlain: String? = null,
    val preview: String? = null,
    @SerialName("preview_plain") val previewPlain: String? = null,
    @SerialName("teaser_image") val teaserImage: List<Media>,
    @SerialName("header_image") val headerImage: List<Media>? = null,
    val gallery: List<Media>? = null,
    val video: Media? = null,
    val date: String,
    val title: String,
    val team: Int,
    val slug: String,
)




