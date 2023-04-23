package mpdev.springboot.datasupplier

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.Properties

@SpringBootApplication
@EnableScheduling
@Slf4j
class DataSupplierApplication

fun main(args: Array<String>) {
    runApplication<DataSupplierApplication>(*args) {
        setDefaultProperties(Properties().also { p -> p["server.port"] = 9090 })
    }
}