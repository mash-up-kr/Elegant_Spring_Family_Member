package mashup.backend.spring.member.domain.oauth

import mashup.backend.spring.member.domain.IdProviderType

interface OAuthUserService {
    fun getUser(accessToken: String): OAuthUser
    fun supports(idProviderType: IdProviderType): Boolean
}