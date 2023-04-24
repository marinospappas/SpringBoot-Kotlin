package mpdev.springboot.eventforwarder.utils

import mpdev.springboot.eventforwarder.TestUtils
import mpdev.springboot.eventforwarder.model.DataRecord
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.BeanUtils
import uk.co.jemos.podam.api.PodamFactoryImpl

class TestUtilsTest {

    private val podamFactory = PodamFactoryImpl()
    private lateinit var data1: DataRecord
    private lateinit var data2: DataRecord

    @BeforeEach
    fun setup() {
        data1 = podamFactory.manufacturePojo(DataRecord::class.java)
        data2 = DataRecord()
        BeanUtils.copyProperties(data1, data2)
    }

    @Test
    fun `Data Records with all fields same are equal`() {
        assertThat(TestUtils.areDataRecordsEqual(data1, data2)).isTrue
    }

    @Test
    fun `Data Records with all fields same but different id are equal`() {
        data1.id = "ID1"
        data2.id = "ID2"
        assertThat(TestUtils.areDataRecordsEqual(data1, data2)).isTrue
    }

    @Test
    fun `Data Records with different fields other than id are not equal`() {
        data1.category = "CAT1"
        data2.category = "CAT2"
        assertThat(TestUtils.areDataRecordsEqual(data1, data2)).isFalse
    }
}
