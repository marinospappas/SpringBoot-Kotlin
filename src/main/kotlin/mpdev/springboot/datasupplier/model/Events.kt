package mpdev.springboot.datasupplier.model

data class Events(var id: Int=0, var category: String="", var eventDetails: List<EventDetails>? = null,
                  var region: String="", var department: String="")

data class EventDetails(var host: String="", var description: String="", var effectiveDate: String="", var criticality: String="")
