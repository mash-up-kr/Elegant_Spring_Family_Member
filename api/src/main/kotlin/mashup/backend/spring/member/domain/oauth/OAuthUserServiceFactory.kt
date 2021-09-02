package mashup.backend.spring.member.domain.oauth

import mashup.backend.spring.member.domain.IdProviderType
import org.springframework.stereotype.Component

@Component
class OAuthUserServiceFactory(
        private val oAuthUserServices: List<OAuthUserService>,
) {
    private val serviceMap: MutableMap<IdProviderType, OAuthUserService> = mutableMapOf()

    init {
        IdProviderType.values().forEach { idProviderType ->
            oAuthUserServices.firstOrNull() { it.supports(idProviderType) }
                    ?.run { serviceMap[idProviderType] = this }
        }
    }

    fun get(idProviderType: IdProviderType): OAuthUserService = serviceMap[idProviderType]
            ?: throw IllegalArgumentException("'idProviderType' is not supported. idProviderType: $idProviderType")
}
