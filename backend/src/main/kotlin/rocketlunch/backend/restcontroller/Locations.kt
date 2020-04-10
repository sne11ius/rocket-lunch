package rocketlunch.backend.restcontroller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import rocketlunch.backend.service.LocationService
import rocketlunch.backend.entity.Location
import javax.validation.Valid

@RestController
@RequestMapping("/locations")
class Locations(
    val locationService: LocationService
) {

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Create e new location")
    fun createLocation(@Valid @RequestBody location: Location): Location = locationService.create(location)

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Get a list of all locations")
    fun getLocations(): List<Location> {
        return locationService.findAll()
    }

}
