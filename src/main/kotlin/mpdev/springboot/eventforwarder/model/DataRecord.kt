package mpdev.springboot.eventforwarder.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class DataRecord(
    @Id
    var id: String,
    var category: String,
    var host: String,
    var description: String,
    var effectiveDate: String,
    var criticality: String,
    var region: String,
    var department: String
)
