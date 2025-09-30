package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class License(
    override var id: Int,
    var number: String,
    @SerialName("valid_until")
    var validUntil: String?,
    var category: String?,
    var level: String,
    @SerialName("sport_association")
    var sportAssociation: String?,
    @SerialName("sleeve_number")
    var sleeveNumber: Int?,
    var baseball: Boolean?,
    var softball: Boolean?,
    var person: PersonReducedResponse,

    //var expiryDate: Date?
) : JSONDataObject {
    // TODO: add date like in the Swift code below
    /*mutating func getExpiryDate() {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "y-M-dd"

        expiryDate = dateFormatter.date(from: valid_until)
    }*/
}