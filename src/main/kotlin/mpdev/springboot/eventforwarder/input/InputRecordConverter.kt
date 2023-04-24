package mpdev.springboot.eventforwarder.input

import mpdev.springboot.eventforwarder.model.DataRecord
import mpdev.springboot.eventforwarder.model.InputRecord
import mpdev.springboot.eventforwarder.model.InputRecordDetails
import org.springframework.stereotype.Component

@Component
class InputRecordConverter {

    fun convert(record: InputRecord, detail: InputRecordDetails): DataRecord =
        DataRecord(id = getUniqueId(record, detail),
            category = record.category,
            region = record.region,
            department = record.department,
            host = detail.host,
            description = detail.description,
            criticality = detail.criticality,
            effectiveDate = detail.effectiveDate)

    private fun getUniqueId(record: InputRecord, detail: InputRecordDetails): String =
        String.format("%x", ("${record.id}${record.category}${record.region}${record.department}${detail.host}" +
                "${detail.description}${detail.criticality}${detail.effectiveDate}").hashCode())

}