package mashup.backend.spring.member.infrastructure.naver

import mashup.backend.spring.member.domain.IdProviderType
import mashup.backend.spring.member.domain.oauth.OAuthAccessToken
import mashup.backend.spring.member.domain.oauth.OAuthAccessTokenService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class NaverAccessTokenService(
        @Value("\${oauth.naver.client.id}")
        private val clientId: String,
        @Value("\${oauth.naver.client.secret}")
        private val clientSecret: String,
) : OAuthAccessTokenService {
    /**
     * state와 code로 token 조회
     */
    override fun getAccessToken(code: String, state: String?): OAuthAccessToken {
        val requestUrl = UriComponentsBuilder.fromHttpUrl("https://nid.naver.com/oauth2.0/token")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type", "authorization_code")
                .queryParam("state", state)
                .queryParam("code", code)
                .build()
                .toUriString()

        try {
            val responseDto = RestTemplate().getForObject(requestUrl, NaverAccessTokenResponse::class.java)
                    ?: throw RuntimeException("Failed to get accessToken")

            log.info("response: $responseDto")

            return NaverAccessToken(responseDto.accessToken, responseDto.refreshToken, responseDto.expiresIn.toLong())
        } catch (e: Exception) {
            log.error("naver login error >>> requestUrl : ${requestUrl}, error message : ${e.message}")
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