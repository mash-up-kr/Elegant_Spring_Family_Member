package mashup.backend.spring.member.domain

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class IdProviderInfo(
        @Enumerated(EnumType.STRING)
        val idProviderType: IdProviderType,
        val idProviderUserId: String
)