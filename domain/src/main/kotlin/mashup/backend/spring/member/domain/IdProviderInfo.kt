package mashup.backend.spring.member.domain

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class IdProviderInfo(
        /**
         * 인증 제공자
         */
        @Enumerated(EnumType.STRING)
        val idProviderType: IdProviderType,
        /**
         * 인증 제공자의 회원 식별자
         */
        val idProviderUserId: String
) {
    companion object {
        fun github(githubUserId: String) = IdProviderInfo(IdProviderType.GITHUB, githubUserId)
    }
}