package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class License(
    override var id: Int,
    var number: String,
    var valid_until: String,
    var category: String,
    var level: String,
    var sport_association: String?,
    var sleeve_number: Int?,
    var baseball: Boolean,
    var softball: Boolean,
    var person: Person,

    //var expiryDate: Date?
): JSONDataObject {
    // TODO: add date like in the Swift code below
    /*mutating func getExpiryDate() {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "y-M-dd"

        expiryDate = dateFormatter.date(from: valid_until)
    }*/
}