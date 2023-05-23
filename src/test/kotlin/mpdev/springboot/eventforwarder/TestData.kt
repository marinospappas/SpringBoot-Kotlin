package mpdev.springboot.eventforwarder

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import mpdev.springboot.eventforwarder.model.DataRecord
import mpdev.springboot.eventforwarder.model.InputRecord

object TestData {

    private const val INPUT_TEST_DATA_JSON_UNIQUE = """
            [
                {"id": 1,"category":"application down","eventDetails":[
                    {"host":"chp009981","description": "statistics processor","criticality":"Warning","effectiveDate": "2023-05-01"},
                    {"host":"chu023815","description": "rule engine","criticality":"Critical","effectiveDate": "2023-05-30"}
                    ],
                    "region":"CH","department":"IB"},
                {"id": 3,"category":"disk space","eventDetails":[
                    {"host":"asp011328","description": "free space 15%","criticality":"Warning","effectiveDate": "2023-03-29"},
                    {"host":"ast110091","description": "free space 5%","criticality":"Critical","effectiveDate": "2023-04-02"}
                    ],
                    "region":"AS","department":"WM"},
                {"id": 2,"category":"memory usage","eventDetails":[
                    {"host":"uk1001342","description": "usage 70%","criticality":"Warning","effectiveDate": "2023-04-21"}
                    ],
                    "region":"UK","department":"IB"}
            ]
            """

    private const val OUTPUT_TEST_DATA_JSON = """
            [
                {"category":"application down","host":"chp009981","description":"statistics processor","effectiveDate":"2023-05-01","criticality":"Warning","region":"CH","department":"IB"},
                {"category":"application down","host":"chu023815","description":"rule engine","effectiveDate":"2023-05-30","criticality":"Critical","region":"CH","department":"IB"},
                {"category":"disk space","host":"asp011328","description":"free space 15%","effectiveDate":"2023-03-29","criticality":"Warning","region":"AS","department":"WM"},
                {"category":"disk space","host":"ast110091","description":"free space 5%","effectiveDate":"2023-04-02","criticality":"Critical","region":"AS","department":"WM"},
                {"category":"memory usage","host":"uk1001342","description":"usage 70%","effectiveDate":"2023-04-21","criticality":"Warning","region":"UK","department":"IB"}
            ]
            """

    private const val INPUT_TEST_DATA_JSON_DUPLICATE = """
            [
                {"id": 1,"department":"IB","category":"application down","region":"CH", "eventDetails":[
                    {"host":"chp009981","description": "statistics processor","criticality":"Warning","effectiveDate": "2023-05-01"},
                    {"host":"chp009981","description": "statistics processor","criticality":"Warning","effectiveDate": "2023-05-01"},
                    {"host":"chp009981","description": "statistics processor","criticality":"Warning","effectiveDate": "2023-05-01"}
                    ]},
                {"id": 1,"department":"IB","category":"application down","region":"AS", "eventDetails":[
                    {"host":"asp011328","description": "statistics processor","criticality":"Warning","effectiveDate": "2023-05-01"}
                    ]}
            ]
            """

    private const val INPUT_TEST_DATA_JSON_NO_DETAILS = """
            [
                {"id": 1,"department":"IB","category":"application down","region":"CH", "eventDetails":[]},
                {"id": 2,"department":"WM","category":"application down","region":"AS" }
            ]
            """

    fun getMockUniqueInputData() = getMockInputData(INPUT_TEST_DATA_JSON_UNIQUE)

    fun getMockDuplicateInputData() = getMockInputData(INPUT_TEST_DATA_JSON_DUPLICATE)

    fun getMockNoDetailsInputData() = getMockInputData(INPUT_TEST_DATA_JSON_NO_DETAILS)

    fun getMockOutputData(): Array<DataRecord> {
        val objectMapper = ObjectMapper()
        var dataRecords: Array<DataRecord>? = null
        try {
            dataRecords = objectMapper.readValue(OUTPUT_TEST_DATA_JSON, Array<DataRecord>::class.java)
        } catch(e: JsonProcessingException) {
            e.printStackTrace()
        }
        return dataRecords ?: arrayOf()
    }

    private fun getMockInputData(jsonString: String): Array<InputRecord> {
        val objectMapper = ObjectMapper()
        var inputRecords: Array<InputRecord>? = null
        try {
            inputRecords =  objectMapper.readValue(jsonString, Array<InputRecord>::class.java)
        } catch(e: JsonProcessingException) {
            e.printStackTrace()
        }
        return inputRecords ?: arrayOf()
    }
}
