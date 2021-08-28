package mashup.backend.spring.member.domain.oauth

interface OAuthUserService {
    fun getUser(accessToken: String): OAuthUser
}