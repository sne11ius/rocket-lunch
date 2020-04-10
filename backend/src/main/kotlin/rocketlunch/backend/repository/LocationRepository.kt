package rocketlunch.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import rocketlunch.backend.entity.Location
import java.util.*

interface LocationRepository: JpaRepository<Location, UUID> {

}
