package mpdev.springboot.eventforwarder

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class EventForwarderApplication {
    init {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
        log.info("Created EventForwarderApplication class")
    }
}

fun main(args: Array<String>) {
    runApplication<EventForwarderApplication>(*args)
}