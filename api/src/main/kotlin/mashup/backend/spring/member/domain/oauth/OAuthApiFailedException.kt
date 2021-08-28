package mashup.backend.spring.member.domain.oauth

open class OAuthApiFailedException(
        override val message: String?,
        override val cause: Throwable?
) : RuntimeException(message, cause)