package mpdev.springboot.eventforwarder

import mpdev.springboot.eventforwarder.model.DataRecord

object TestUtils {

    fun areDataRecordsEqual(a: DataRecord, b: DataRecord) =
                a.region == b.region &&
                a.category == b.category &&
                a.description == b.description &&
                a.criticality == b.criticality &&
                a.department == b.department &&
                a.effectiveDate == b.effectiveDate
}
