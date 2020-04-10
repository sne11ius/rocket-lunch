package rocketlunch.backend.listener

import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ApplicationEventListener {

    private val log = KotlinLogging.logger {}

    @EventListener
    fun onApplicationReadyEvent(event: ApplicationReadyEvent) {
        log.info { "RocketLunch ready to serve requests..." }
    }

}
