package rocketlunch.backend.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.util.UUID.*
import javax.persistence.Access
import javax.persistence.AccessType.*
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
@Access(FIELD)
abstract class AbstractBaseEntity {
    @Id
    val id = randomUUID()

    @Version
    val version: Int = 0

    @CreatedDate
    val createdDate: OffsetDateTime = OffsetDateTime.now()

    @LastModifiedDate
    val lastModifiedDate: OffsetDateTime = OffsetDateTime.now()
}
