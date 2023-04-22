package mpdev.springboot.datasupplier.controller

import mpdev.springboot.datasupplier.model.Events
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestApiController(@Autowired var eventsList: List<Events>) {

    @GetMapping("/events/", produces = ["application/json"])
    fun getAllEvents(): Iterable<Events> = eventsList

}