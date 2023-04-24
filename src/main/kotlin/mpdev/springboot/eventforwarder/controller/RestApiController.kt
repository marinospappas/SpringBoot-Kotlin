package mpdev.springboot.eventforwarder.controller

import mpdev.springboot.eventforwarder.model.DataRecord
import mpdev.springboot.eventforwarder.model.DataRecordRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/allevents")
class RestApiController(var dataRecordRepository: DataRecordRepository) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @GetMapping("/")
    fun getAllEvents(): Iterable<DataRecord> {
        val dataList: List<DataRecord> = dataRecordRepository.findAll()
        log.info("controller read {} record(s) from db", dataList.size)
        return dataList
    }

    @GetMapping("/region/{region}")
    fun getEventsByRegion(@PathVariable region: String) = dataRecordRepository.findAllByRegion(region)

    @GetMapping("/department/{department}")
    fun getEventsByDepartment(@PathVariable department: String) = dataRecordRepository.findAllByDepartment(department)
}
