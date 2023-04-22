package mpdev.springboot.eventforwarder

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@Slf4j
class EventForwarderApplication

fun main(args: Array<String>) {
    runApplication<EventForwarderApplication>(*args)
}