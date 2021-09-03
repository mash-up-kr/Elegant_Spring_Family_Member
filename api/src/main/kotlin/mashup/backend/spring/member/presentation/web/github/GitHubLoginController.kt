package mashup.backend.spring.member.presentation.web.github

import mashup.backend.spring.member.application.LoginService
import mashup.backend.spring.member.presentation.api.member.MemberResponse
import mashup.backend.spring.member.presentation.web.oauth.OAuthController
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.concurrent.ThreadLocalRandom
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("login/github")
class GitHubLoginController(
        @Value("\${oauth.github.client.id}")
        private val clientId: String,
        @Value("\${oauth.github.url.callback}")
        private val callbackUrl: String,
        private val loginService: LoginService
) : OAuthController()  {
    override fun createOAuthUrl(state: String): String {
        return UriComponentsBuilder.fromHttpUrl("https://github.com/login/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", callbackUrl)
                .queryParam("state", state)
                .build()
                .toUriString()
    }

    override fun loginCallbackWithOAuth(code: String, state: String): MemberResponse {
        return loginService.loginWithGitHub(code)
    }
}

