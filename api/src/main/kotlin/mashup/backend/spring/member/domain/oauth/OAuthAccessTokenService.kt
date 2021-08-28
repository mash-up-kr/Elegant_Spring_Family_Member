package mashup.backend.spring.member.domain.oauth

interface OAuthAccessTokenService {
    fun getAccessToken(code: String): OAuthAccessToken
}