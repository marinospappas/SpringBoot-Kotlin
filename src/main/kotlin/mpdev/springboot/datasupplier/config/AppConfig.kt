package mpdev.springboot.datasupplier.config

import mpdev.springboot.datasupplier.model.Events
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun eventsList(): MutableList<Events> = mutableListOf()
}