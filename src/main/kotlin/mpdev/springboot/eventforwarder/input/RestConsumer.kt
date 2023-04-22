package mpdev.springboot.eventforwarder.input

import mpdev.springboot.eventforwarder.model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Component
class RestConsumer(val restTemplate: RestTemplate,
                   val dataRecordRepository: DataRecordRepository,
                   @Value("\${events.provider.url}") val providerUrl: String) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Scheduled(initialDelay = 2000, fixedRate = 5000)
    @Transactional
    fun run() {
        log.info("executing events lookup")
        val inputRecords: Array<InputRecord>? = restTemplate.getForObject(providerUrl, Array<InputRecord>::class.java)
        var dataRecordCount = 0
        if (inputRecords != null) {
            dataRecordRepository.deleteAll()
            inputRecords.toList().forEach{ record ->

                if (record.inputRecordDetails == null
                ||  record.inputRecordDetails!!.isEmpty())
                    record.inputRecordDetails = listOf(InputRecordAllOk.getDetail())

                for (index in 0 until record.inputRecordDetails!!.size) {
                    val detail: InputRecordDetails = record.inputRecordDetails!![index]
                    dataRecordRepository.save(getDataRecordFromInput(record, detail))
                    ++dataRecordCount
                }
            }
            log.info("sampler added {} record(s) to db", dataRecordCount)
        }
    }

    private fun getDataRecordFromInput(record: InputRecord, detail: InputRecordDetails): DataRecord =
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