package rocketlunch.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RocketLunchBackendApplication

fun main(args: Array<String>) {
	runApplication<RocketLunchBackendApplication>(*args)
}
