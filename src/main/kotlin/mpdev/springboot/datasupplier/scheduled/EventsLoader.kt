package mpdev.springboot.datasupplier.scheduled

import com.fasterxml.jackson.databind.ObjectMapper
import mpdev.springboot.datasupplier.model.Events
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import java.nio.file.Files
import java.nio.file.Path
import java.util.Arrays

@Component
class EventsLoader(@Autowired var eventsList: MutableList<Events>) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Value("\${input.filename}")
    lateinit var inputFile: String

    @Scheduled(initialDelay = 3000, fixedRate = 10000)
    fun loadEventsFromFile() {
        log.info("loading input file {}", inputFile)
        val objectMapper = ObjectMapper()
        val events: Array<Events> =
            objectMapper.readValue(Files.readAllBytes(Path.of(inputFile)), Array<Events>::class.java)
        eventsList.clear()
        eventsList.addAll(Arrays.stream(events).toList())
    }

}



