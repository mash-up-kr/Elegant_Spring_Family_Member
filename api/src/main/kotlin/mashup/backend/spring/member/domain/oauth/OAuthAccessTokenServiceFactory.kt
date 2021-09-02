package mashup.backend.spring.member.domain.oauth

import mashup.backend.spring.member.domain.IdProviderType
import org.springframework.stereotype.Component

@Component
class OAuthAccessTokenServiceFactory(
        private val oAuthAccessTokenServices: List<OAuthAccessTokenService>
) {
    private val serviceMap: MutableMap<IdProviderType, OAuthAccessTokenService> = mutableMapOf()

    init {
        IdProviderType.values().forEach { idProviderType ->
            oAuthAccessTokenServices.firstOrNull() { it.supports(idProviderType) }
                    ?.run { serviceMap[idProviderType] = this }
        }
    }

    fun get(idProviderType: IdProviderType): OAuthAccessTokenService = serviceMap[idProviderType]
            ?: throw IllegalArgumentException("'idProviderType' is not supported. idProviderType: $idProviderType")
}