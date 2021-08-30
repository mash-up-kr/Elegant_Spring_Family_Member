package mashup.backend.spring.member.infrastructure.github

import mashup.backend.spring.member.domain.oauth.OAuthUser
import mashup.backend.spring.member.domain.oauth.OAuthUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service("gitHubUserService")
class GitHubUserService : OAuthUserService {
    /**
     * accessToken 으로 GitHub user 정보 조회
     */
    override fun getUser(accessToken: String): OAuthUser {
        try {
            val httpHeaders = HttpHeaders()
            httpHeaders["Authorization"] = "token $accessToken"
            val httpEntity = HttpEntity<GitHubUserResponse>(httpHeaders)
            val exchange = RestTemplate().exchange(
                    "https://api.github.com/user",
                    HttpMethod.GET,
                    httpEntity,
                    GitHubUserResponse::class.java
            )
            if (!exchange.statusCode.is2xxSuccessful) {
                throw RuntimeException("Failed to get github user. statusCode: ${exchange.statusCode}")
            }
            val githubUserResponse = exchange.body!!
            return GitHubUser(
                    id = githubUserResponse.id,
                    name = githubUserResponse.login,
                    email = githubUserResponse.email,
                    profileImageUrl = githubUserResponse.avatarUrl,
                    details = emptyMap()
            )
        } catch (e: Exception) {
            log.error("github get user error", e)
            throw e
        }
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}