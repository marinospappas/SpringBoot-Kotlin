package mpdev.springboot.eventforwarder.input

import mpdev.springboot.eventforwarder.TestData
import mpdev.springboot.eventforwarder.TestUtils
import mpdev.springboot.eventforwarder.model.DataRecord
import mpdev.springboot.eventforwarder.model.DataRecordRepository
import mpdev.springboot.eventforwarder.model.InputRecord
import mpdev.springboot.eventforwarder.model.InputRecordAllOk
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestPropertySource
import org.springframework.web.client.RestTemplate


import org.assertj.core.api.Assertions.assertThat
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value

@SpringBootTest
@TestPropertySource(properties = ["app.scheduling.enable=false"])
class RestConsumerTest {

    @Autowired
    private lateinit var restConsumer: RestConsumer

    @MockBean
    @Suppress("unused")
    private lateinit var restTemplate: RestTemplate

    @Value("\${events.provider.url}")
    private lateinit var providerUrl: String

    @Autowired
    private lateinit var dataRecordRepository: DataRecordRepository

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Test
    fun `Input Records are decoded and stored correctly to the In-memory DB`() {
        Mockito.`when`(restTemplate.getForObject(providerUrl, Array<InputRecord>::class.java))
            .thenReturn(TestData.getMockUniqueInputData())
        restConsumer.run()
        val savedRecords: List<DataRecord> = dataRecordRepository.findAll()
        savedRecords.forEach { r -> log.info("data record saved: {}", r) }
        val expected: Array<DataRecord> = TestData.getMockOutputData()
        assertThat(dataRecordRepository.findAll().size).isEqualTo(expected.size)
        for (i in expected.indices)
            assertThat(TestUtils.areDataRecordsEqual(savedRecords[i], expected[i])).isTrue
    }

    @Test
    fun `UniqueId has the same value for duplicate records`() {
        Mockito.`when`(restTemplate.getForObject(providerUrl, Array<InputRecord>::class.java))
            .thenReturn(TestData.getMockDuplicateInputData())
        restConsumer.run()
        val savedRecords: List<DataRecord> = dataRecordRepository.findAll()
        savedRecords.forEach { r -> log.info("data record saved: {}", r) }
        assertThat(savedRecords.size).isEqualTo(2)
    }

    @Test
    fun `Input record with No Details is mapped to ALL OK`() {
        Mockito.`when`(restTemplate.getForObject(providerUrl, Array<InputRecord>::class.java))
            .thenReturn(TestData.getMockNoDetailsInputData())
        restConsumer.run()
        val savedRecords: List<DataRecord> = dataRecordRepository.findAll()
        savedRecords.forEach { r -> log.info("data record saved: {}", r) }
        assertThat(savedRecords.size).isEqualTo(2)
        assertThat(savedRecords[0].description).isEqualTo(InputRecordAllOk.ALL_OK)
        assertThat(savedRecords[1].description).isEqualTo(InputRecordAllOk.ALL_OK)

    }
}
