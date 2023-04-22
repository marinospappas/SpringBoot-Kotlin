package mpdev.springboot.eventforwarder.model

import org.apache.commons.lang3.StringUtils.EMPTY

object InputRecordAllOk {

    const val ALL_OK = "ALL OK"

    fun getDetail(): InputRecordDetails = InputRecordDetails(EMPTY, ALL_OK, EMPTY, EMPTY)
}
