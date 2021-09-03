package mashup.backend.spring.member.infrastructure.naver

import mashup.backend.spring.member.domain.IdProviderType
import mashup.backend.spring.member.domain.oauth.OAuthUser
import mashup.backend.spring.member.domain.oauth.OAuthUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class NaverUserService : OAuthUserService {
    override fun getUser(accessToken: String): OAuthUser {
        try {
            val httpHeaders = HttpHeaders()
            httpHeaders["Authorization"] = "Bearer $accessToken"
            val httpEntity = HttpEntity<NaverUserResponse>(httpHeaders)
            val exchange = RestTemplate().exchange(
                    "https://openapi.naver.com/v1/nid/me",
                    HttpMethod.GET,
                    httpEntity,
                    NaverUserResponse::class.java
            )
            if (!exchange.statusCode.is2xxSuccessful) {
                throw RuntimeException("Failed to get naver user. statusCode: ${exchange.statusCode}")
            }
            val naverUserResponse = exchange.body!!
            println(exchange.body)
            println(naverUserResponse)

            return NaverUser(
                    id = naverUserResponse.response.id,
                    name = naverUserResponse.response.name,
                    email = naverUserResponse.response.email,
                    profileImageUrl = naverUserResponse.response.profileImage,
                    details = emptyMap()
            )
        } catch (e: Exception) {
            log.error("naver get user error", e)
            throw e
        }
    }

    override fun supports(idProviderType: IdProviderType): Boolean {
        return idProviderType == IdProviderType.NAVER
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}