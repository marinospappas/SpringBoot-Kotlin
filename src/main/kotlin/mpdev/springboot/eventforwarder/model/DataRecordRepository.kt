package mpdev.springboot.eventforwarder.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
@Suppress("unused")
interface DataRecordRepository: JpaRepository<DataRecord, String> {

    fun findAllByRegion(region: String): List<DataRecord>
    fun findAllByDepartment(department: String): List<DataRecord>

    fun findAllByCategory(category: String): List<DataRecord>
    fun findAllByCriticality(criticality: String): List<DataRecord>
    fun findAllByDescription(description: String): List<DataRecord>
}
