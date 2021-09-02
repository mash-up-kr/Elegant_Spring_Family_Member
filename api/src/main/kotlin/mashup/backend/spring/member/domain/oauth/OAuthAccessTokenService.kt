package mashup.backend.spring.member.domain.oauth

import mashup.backend.spring.member.domain.IdProviderType

interface OAuthAccessTokenService {
    fun getAccessToken(code: String): OAuthAccessToken
    fun supports(idProviderType: IdProviderType): Boolean
}