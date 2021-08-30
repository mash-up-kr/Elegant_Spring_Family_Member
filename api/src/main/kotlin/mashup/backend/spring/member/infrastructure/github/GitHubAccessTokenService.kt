package mashup.backend.spring.member.infrastructure.github

import mashup.backend.spring.member.domain.oauth.OAuthAccessTokenService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service("gitHubAccessTokenService")
class GitHubAccessTokenService(
        @Value("\${oauth.github.client.id}")
        private val clientId: String,
        @Value("\${oauth.github.client.secret}")
        private val clientSecret: String,
) : OAuthAccessTokenService {
    /**
     * code 로 AccessToken 조회
     */
    override fun getAccessToken(code: String): GitHubAccessToken {
        try {
            val responseDto = RestTemplate().postForObject(
                    "https://github.com/login/oauth/access_token",
                    GitHubAccessTokenRequest(
                            clientId = clientId,
                            clientSecret = clientSecret,
                            code = code
                    ),
                    GitHubAccessTokenResponse::class.java
            ) ?: throw RuntimeException("Failed to get accessToken")
            log.info("response: $responseDto")
            return GitHubAccessToken(responseDto.accessToken)
        } catch (e: Exception) {
            log.error("github login error", e)
            throw e
        }
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}