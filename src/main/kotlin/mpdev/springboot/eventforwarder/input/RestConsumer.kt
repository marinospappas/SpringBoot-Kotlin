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
                   @Value("\${events.provider.url}") val providerUrl: String,
                   val dataRecordRepository: DataRecordRepository,
                   val inputRecordConverter: InputRecordConverter) {

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
                    dataRecordRepository.save(inputRecordConverter.convert(record, detail))
                    ++dataRecordCount
                }
            }
            log.info("sampler added {} record(s) to db", dataRecordCount)
        }
    }
 }