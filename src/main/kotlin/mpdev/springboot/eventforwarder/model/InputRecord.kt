package mpdev.springboot.eventforwarder.model

import com.fasterxml.jackson.annotation.JsonProperty

data class InputRecord(
    var id: Int=0,
    var category: String="",
    @JsonProperty("eventDetails") var inputRecordDetails: List<InputRecordDetails>? = null,
    var region: String="",
    var department: String=""
)

data class InputRecordDetails(
    var host: String="",
    var description: String="",
    var effectiveDate: String="",
    var criticality: String=""
)
