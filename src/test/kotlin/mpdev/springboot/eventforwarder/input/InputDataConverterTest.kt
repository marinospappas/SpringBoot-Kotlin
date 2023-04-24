package mpdev.springboot.eventforwarder.input

import mpdev.springboot.eventforwarder.TestData
import mpdev.springboot.eventforwarder.TestUtils
import mpdev.springboot.eventforwarder.model.DataRecord
import mpdev.springboot.eventforwarder.model.InputRecord
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InputDataConverterTest {

    private val inputRecordConverter = InputRecordConverter()

    @Test
    fun `Input records are converted to flat DataRecord rows`() {
        val testInput: Array<InputRecord> = TestData.getMockUniqueInputData()
        val expected: Array<DataRecord> = TestData.getMockOutputData()
        assertThat(TestUtils.areDataRecordsEqual(inputRecordConverter.convert(testInput[0],
            testInput[0].inputRecordDetails!![0]), expected[0])).isTrue
        assertThat(TestUtils.areDataRecordsEqual(inputRecordConverter.convert(testInput[0],
            testInput[0].inputRecordDetails!![1]), expected[1])).isTrue
        assertThat(TestUtils.areDataRecordsEqual(inputRecordConverter.convert(testInput[1],
            testInput[1].inputRecordDetails!![0]), expected[2])).isTrue
        assertThat(TestUtils.areDataRecordsEqual(inputRecordConverter.convert(testInput[1],
            testInput[1].inputRecordDetails!![1]), expected[3])).isTrue
        assertThat(TestUtils.areDataRecordsEqual(inputRecordConverter.convert(testInput[2],
            testInput[2].inputRecordDetails!![0]), expected[4])).isTrue
    }
}
