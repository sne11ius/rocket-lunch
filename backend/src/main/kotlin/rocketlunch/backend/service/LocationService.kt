package rocketlunch.backend.service

import org.springframework.stereotype.Service
import rocketlunch.backend.entity.Location
import rocketlunch.backend.repository.LocationRepository

@Service
class LocationService(
        val locationRepository: LocationRepository
) {
    fun findAll(): List<Location> = locationRepository.findAll()
    fun create(location: Location) = locationRepository.save(location)
}
