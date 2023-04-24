package mpdev.springboot.eventforwarder.controller

import mpdev.springboot.eventforwarder.TestData
import mpdev.springboot.eventforwarder.TestUtils
import mpdev.springboot.eventforwarder.model.DataRecord
import mpdev.springboot.eventforwarder.model.DataRecordRepository
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.TestPropertySource


import org.assertj.core.api.Assertions.assertThat
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = ["app.scheduling.enable=false"])
class RestApiControllerTest {

    @Autowired
    private lateinit var dataRecordRepository: DataRecordRepository

    @Autowired
    private lateinit var restApiController: RestApiController

    @Value(value="\${local.server.port}")
    private lateinit var port: Integer

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    private lateinit var BASE_URL: String

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @BeforeEach
    fun setup() {
        BASE_URL = "http://localhost:$port/allevents/"
        loadTestDataToDb()
    }

    @Test
    fun contextLoads() {
        assertThat(restApiController).isNotNull
    }

    @Test
    fun `Rest API returns all events`() {
        val retrievedRecords: Array<DataRecord> = getDataRecordsFromRestApi("")
        log.info("retrieved from Rest API")
        retrievedRecords.forEach {r -> log.info("data record: {}", r)}
        val expected: Array<DataRecord> = TestData.getMockOutputData()
        assertThat(retrievedRecords.size).isEqualTo(expected.size)
        for (i in expected.indices)
            assertThat(TestUtils.areDataRecordsEqual(retrievedRecords[i], expected[i])).isTrue
    }

    @ParameterizedTest
    @ValueSource(strings = ["CH", "UK", "AS"])
    fun `Rest API call by Region returns the correct events`(region: String) {
        val retrievedRecords: Array<DataRecord> = getDataRecordsFromRestApi("region/$region")
        log.info("retrieved from Rest API")
        retrievedRecords.forEach {r -> log.info("data record: {}", r)}
        val expected: Array<DataRecord> = TestData.getMockOutputData().filter { r -> r.region == region }.toTypedArray()
        assertThat(retrievedRecords.size).isEqualTo(expected.size)
        for (i in expected.indices)
            assertThat(TestUtils.areDataRecordsEqual(retrievedRecords[i], expected[i])).isTrue
    }

    @ParameterizedTest
    @ValueSource(strings = ["IB", "WM"])
    fun `Rest API call by Department returns the correct events`(department: String) {
        val retrievedRecords: Array<DataRecord> = getDataRecordsFromRestApi("department/$department")
        log.info("retrieved from Rest API")
        retrievedRecords.forEach {r -> log.info("data record: {}", r)}
        val expected: Array<DataRecord> = TestData.getMockOutputData().filter { r -> r.department == department }
            .toTypedArray()
        assertThat(retrievedRecords.size).isEqualTo(expected.size)
        for (i in expected.indices)
            assertThat(TestUtils.areDataRecordsEqual(retrievedRecords[i], expected[i])).isTrue
    }

    private fun loadTestDataToDb() {
        val dataRecords: Array<DataRecord> = TestData.getMockOutputData()
        dataRecords.forEach {r -> r.id = RandomStringUtils.randomAlphanumeric(10)}
        dataRecordRepository.deleteAll()
        dataRecordRepository.saveAll(dataRecords.toList())
        log.info("test DB contents")
        dataRecordRepository.findAll().forEach {r -> log.info("data record: {}", r)}
    }

    private fun getDataRecordsFromRestApi(url: String): Array<DataRecord> {
        val urlToConnect = BASE_URL + url
        return testRestTemplate.getForObject(urlToConnect, Array<DataRecord>::class.java)
    }
}
