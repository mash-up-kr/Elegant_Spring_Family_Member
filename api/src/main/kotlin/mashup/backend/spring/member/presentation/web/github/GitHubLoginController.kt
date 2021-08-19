package mashup.backend.spring.member.presentation.web.github

import mashup.backend.spring.member.application.LoginService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.concurrent.ThreadLocalRandom
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Controller
class GitHubLoginController(
        @Value("\${oauth.github.client.id}")
        private val clientId: String,
        @Value("\${oauth.github.client.secret}")
        private val clientSecret: String,
        @Value("\${oauth.github.url.callback}")
        private val callbackUrl: String,
        private val loginService: LoginService
) {
    /**
     * 깃헙 로그인 진입점
     */
    @GetMapping("/login/github")
    fun loginWithGitHub(
            httpSession: HttpSession
    ): String {
        val state = createState()
        val url = createGitHubOAuthUrl(state)
        httpSession.setAttribute("state", state)
        return "redirect:$url"
    }

    private fun createState(): String = "mashup${ThreadLocalRandom.current().nextInt(1000)}"

    private fun createGitHubOAuthUrl(state: String): String {
        return UriComponentsBuilder.fromHttpUrl("https://github.com/login/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", callbackUrl)
                .queryParam("state", state)
                .build()
                .toUriString()
    }

    /**
     * 깃헙 인증 후처리
     */
    @GetMapping("/login/github/callback")
    fun postProcess(
            @RequestParam code: String,
            @RequestParam state: String,
            httpServiceRequest: HttpServletRequest,
            httpSession: HttpSession
    ): String {
        if (state != httpSession.getAttribute("state")) {
            throw RuntimeException("state 값이 일치하지 않음. queryParam.state: $state, session.state: $state")
        }
        val accessToken = getAccessToken(code)
        val githubUser = getUser(accessToken)
        LoggerFactory.getLogger(this::class.java).info("githubUser: $githubUser")
        loginService.loginWithGitHub(githubUser.id)
        httpSession.setAttribute("nickname", githubUser.login)
        return "redirect:/"
    }

    /**
     * code 로 accessToken 조회
     */
    private fun getAccessToken(code: String): String {
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
            LoggerFactory.getLogger(this::class.java).info("response: $responseDto")
            return responseDto.accessToken
        } catch (e: Exception) {
            LoggerFactory.getLogger(this::class.java).error("github login error", e)
            throw e
        }
    }

    /**
     * accessToken 으로 user 정보 조회
     */
    private fun getUser(accessToken: String): GitHubUserResponse {
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
            return exchange.body!!
        } catch (e: Exception) {
            LoggerFactory.getLogger(this::class.java).error("github get user error", e)
            throw e
        }
    }
}

