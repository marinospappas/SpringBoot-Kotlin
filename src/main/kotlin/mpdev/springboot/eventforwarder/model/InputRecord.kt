package mpdev.springboot.eventforwarder.model

import com.fasterxml.jackson.annotation.JsonProperty

data class InputRecord(
    @JsonProperty("id")
    var id: Int=0,
    @JsonProperty("category")
    var category: String="",
    @JsonProperty("eventDetails")
    var inputRecordDetails: List<InputRecordDetails>? = null,
    @JsonProperty("region")
    var region: String="",
    @JsonProperty("department")
    var department: String=""
)

data class InputRecordDetails(
    var host: String="",
    var description: String="",
    var effectiveDate: String="",
    var criticality: String=""
)
