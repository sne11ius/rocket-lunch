package rocketlunch.backend.entity

import org.springframework.lang.NonNull
import javax.persistence.Column
import javax.persistence.Entity
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
class Location(
        @NotNull
        @NonNull
        @NotEmpty
        @Column
        var title: String = ""
): AbstractBaseEntity()
